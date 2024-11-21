package org.example.redirectUrlShorter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	private final S3Client s3Client = S3Client.builder().build();

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> stringObjectMap, Context context) {

		String pathParameters = stringObjectMap.get("rawPath").toString();
		String shortUrlCode = pathParameters.replace("/", "");

		if (Objects.isNull(shortUrlCode) || shortUrlCode.isEmpty()) {
			throw new IllegalArgumentException("Invalid input: 'shortUrlCode' is required.");
		}

		GetObjectRequest request = GetObjectRequest.builder()
				                           .bucket("url-shortener-aws-bucket")
				                           .key(shortUrlCode)
				                           .build();

		InputStream s3ObjectStream;

		try {
			s3ObjectStream = this.s3Client.getObject(request);
		} catch (Exception e) {
			throw new RuntimeException("Error fetching URL data from S3: " + e.getMessage());
		}

		OriginalUrlData originalUrlData;

		try {
			originalUrlData = this.objectMapper.readValue(s3ObjectStream, OriginalUrlData.class);
		} catch (Exception e) {
			throw new RuntimeException("Error deserializing URL data: " + e.getMessage());
		}

		long currentTimeInSeconds = System.currentTimeMillis() / 1000;

		Map<String, Object> response = new HashMap<>();

		if (currentTimeInSeconds > originalUrlData.getExpirationTime()) {
			response.put("statusCode", 410);
			response.put("body", "This URL has expired");
		} else {
			response.put("statusCode", 302);

			Map<String, String> headers = new HashMap<>();
			headers.put("Location", originalUrlData.getOriginalUrl());

			response.put("headers", headers);
		}

		return response;
	}
}