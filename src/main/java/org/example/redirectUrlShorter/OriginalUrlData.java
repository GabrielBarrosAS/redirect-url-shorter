package org.example.redirectUrlShorter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalUrlData {
	private String originalUrl;
	private Long expirationTime;
}
