# Projeto de redirecionamento de URLs encurtadas com AWS
Este projeto é uma função Lambda desenvolvida em Java 17 que implementa um serviço de redirecionamento de URLs encurtadas.
Ele utiliza o Amazon S3 como armazenamento de dados para as URLs encurtadas, possibilitando o redirecionamento para a URL
original ou o retorno de mensagens de erro, dependendo da validade do código curto fornecido.

### Funcionalidades

- **Redirecionamento de URLs**:  
  Recebe um código curto (`shortUrlCode`) no caminho da requisição HTTP e redireciona o usuário para a URL original associada ao código.
  
- **Verificação de validade**:  
  Avalia se a URL ainda é válida com base no tempo de expiração armazenado no S3.
  
- **Respostas HTTP apropriadas**:  
  - `302 Found` para redirecionamento bem-sucedido.  
  - `410 Gone` caso a URL tenha expirado.  
  - `404 Not Found` se o código curto não for encontrado no S3.

### Tecnologias Utilizadas

- **Java 17**: Linguagem principal para o desenvolvimento da função Lambda.  
- **AWS Lambda**: Para execução sem servidor.  
- **Amazon S3**: Para armazenamento persistente dos dados de URLs encurtadas.  
- **AWS SDK for Java**: Para interação com os serviços da AWS.  
- **Jackson**: Para serialização e desserialização de objetos JSON.

## Requisitos
- Java 17
- Conta AWS com bucket S3 configurado
- Maven para gerenciamento de dependências

## Instalação

1. **Clone o repositório**:
   Clone o projeto para sua máquina local:
   ```bash
   git clone https://github.com/GabrielBarrosAS/redirect-url-shorter-aws-lambda.git
   cd redirect-url-shorter-aws-lambda

2. **Atualize o nome do bucket S3**:  
   No código da classe Main, altere o nome do bucket para o que você configurou na AWS:
   ```bash
   .bucket("seu-nome-do-bucket")

3. **Compile o projeto**:  
   Compile o projeto utilizando Maven:
   ```bash
   mvn clean install

## Implantação
Deploy no AWS Lambda
1. **Empacote o projeto em um arquivo .jar:**:
   ```bash
   mvn package
2. **Faça o upload do .jar na função Lambda no console da AWS ou via CLI.**
3. **Configure variáveis de ambiente, se necessário (por exemplo, nome do bucket).**

## Postman Collection

Uma coleção do Postman foi preparada para facilitar os testes da aplicação. Esta coleção contém exemplos de requisições configuradas para testar a função Lambda de encurtamento de URLs.

## Como Importar a Coleção

1. Faça o download do arquivo `url-shortener-aws-lambda.postman_collection.json` do repositório.
2. No Postman, clique em **Importar** no canto superior esquerdo.
3. Selecione o arquivo `url-shortener-aws-lambda.postman_collection.json` baixado.
4. Após a importação, a coleção aparecerá na sua lista de coleções.

## Estrutura da Coleção

A coleção inclui os seguintes endpoints:

2. **GET Shorten (AWS Lambda) e GET Shorten (API Gateway)**  
Envia um parametro na URL da requisição que deve ser o uuid gerado no endpoint de post, que irá consultar a URL cadastrada 
para o redirecionamento. Esse endpoint pode ser usado via navegador para ter uma melhor experiência. Uma requisição acessa a função lambda
diretamente e a segunda utiliza o gateway central da aplicação
