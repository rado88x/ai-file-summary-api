# File summary service using OpenAI ChatGPT to read, extract and summarize content.
Java Spring Boot REST API that scans a folder of documents, stores metadata in a relational database (PostgreSQL), and generates concise summaries using OpenAI. 
It supports PDF, DOCX, and TXT files, exposes a clean HTTP interface (Swagger/OpenAPI), and can process files in parallel/async for throughput.

## Important about API_KEY : 
OpenAI does not work with subsciption/paid accounts. It request a deposit before to be able to use API_KEY.

## What inside :
Multi-format ingestion: .pdf, .docx, .txt from a configured folder. 
Metadata persistence: name, path, format, size, checksum/status, etc., in a relational DB.
AI Service extracted to interface and have multiple implementations (Google, Anthropic).
AI-powered summaries: Leverages OpenAI/ChatGPT to produce document summaries. 
RESTful API: Endpoints defined per shared specification, documented with Swagger/OpenAPI.
Parallel/async processing (bonus): Optional asynchronous pipeline for faster throughput.
Production-minded: Layered architecture, clean code, performance considerations

## Flow
-> Controller accept request 

-> Service scans folder, validates types, computes checksum 

-> Calls OpenAI, handles retries/backoff and update db record

-> DB persistence, idempotency by checksum

-> Async processing with ThreadPoolTaskExecutor

-> Error Handling and Custom exceptions.


## Build & Run with Maven 
mvn clean install

mvn spring-boot:run


<img width="1919" height="1044" alt="shot2" src="https://github.com/user-attachments/assets/6f455120-0175-482a-b43f-ab8f42ca025c" />

<img width="1897" height="541" alt="image" src="https://github.com/user-attachments/assets/26bda59c-d2b0-4e18-bbe5-431947fd1ee4" />
