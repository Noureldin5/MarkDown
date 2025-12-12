# Markdown Note-taking App

A RESTful API application for managing markdown notes with grammar checking and HTML rendering capabilities.

## Features

- ✅ **Save Notes**: Create and save notes with markdown formatting
- ✅ **Upload Markdown Files**: Upload `.md` files directly
- ✅ **List Notes**: Retrieve all saved notes
- ✅ **Grammar Checking**: Check grammar using LanguageTool
- ✅ **HTML Rendering**: Convert markdown to HTML
- ✅ **CRUD Operations**: Full Create, Read, Update, Delete support

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (embedded)
- **CommonMark** (Markdown parsing)
- **LanguageTool** (Grammar checking)
- **Maven** (Build tool)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use the Maven wrapper included)

### Installation

1. Clone or navigate to the project directory:
```bash
cd C:\Users\Noor\IdeaProjects\MarkDown
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Create a Note
```http
POST /api/notes
Content-Type: application/json

{
  "title": "My First Note",
  "content": "# Hello World\n\nThis is a **markdown** note."
}
```

### 2. Upload Markdown File
```http
POST /api/notes/upload
Content-Type: multipart/form-data

file: [select .md file]
```

### 3. List All Notes
```http
GET /api/notes
```

### 4. Get a Specific Note
```http
GET /api/notes/{id}
```

### 5. Update a Note
```http
PUT /api/notes/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "content": "Updated markdown content"
}
```

### 6. Delete a Note
```http
DELETE /api/notes/{id}
```

### 7. Check Grammar of a Note
```http
POST /api/notes/{id}/check-grammar
```

### 8. Check Grammar of Text
```http
POST /api/notes/check-grammar
Content-Type: application/json

{
  "text": "This are a test sentence with grammar error."
}
```

### 9. Render Note as HTML
```http
GET /api/notes/{id}/render
```

### 10. Convert Markdown to HTML
```http
POST /api/notes/render
Content-Type: application/json

{
  "markdown": "# Hello\n\nThis is **bold** text."
}
```

## Example Usage with cURL

### Create a note:
```bash
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Sample Note\",\"content\":\"# Markdown Example\n\nThis is a **test** note.\"}"
```

### Check grammar:
```bash
curl -X POST http://localhost:8080/api/notes/check-grammar \
  -H "Content-Type: application/json" \
  -d "{\"text\":\"This are a test.\"}"
```

### Upload a file:
```bash
curl -X POST http://localhost:8080/api/notes/upload \
  -F "file=@path/to/your/file.md"
```

## Testing with Postman

1. Import the endpoints into Postman
2. Set the base URL to `http://localhost:8080`
3. Use the examples above for request bodies

## Database

The application uses an embedded H2 database. You can access the H2 console at:
```
http://localhost:8080/h2-console
```

**Connection details:**
- JDBC URL: `jdbc:h2:file:./data/notesdb`
- Username: `sa`
- Password: (leave empty)

## Project Structure

```
src/main/java/com/markdown/
├── MarkdownNotesApplication.java   # Main application class
├── controller/
│   ├── HomeController.java         # Root endpoint
│   └── NoteController.java         # Notes API endpoints
├── service/
│   ├── NoteService.java           # Note business logic
│   ├── MarkdownService.java       # Markdown to HTML conversion
│   └── GrammarService.java        # Grammar checking
├── repository/
│   └── NoteRepository.java        # Database access
├── entity/
│   └── Note.java                  # Note entity
├── dto/
│   ├── NoteRequest.java           # Request DTOs
│   ├── NoteResponse.java          # Response DTOs
│   ├── GrammarCheckResponse.java
│   └── GrammarError.java
└── exception/
    ├── ResourceNotFoundException.java
    ├── ErrorResponse.java
    └── GlobalExceptionHandler.java
```

## Configuration

Application settings can be modified in `src/main/resources/application.properties`:

```properties
# Server port
server.port=8080

# Database location
spring.datasource.url=jdbc:h2:file:./data/notesdb

# File upload limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## Error Handling

The API returns structured error responses:

```json
{
  "timestamp": "2025-12-12T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Note not found with id: 1",
  "path": "/api/notes/1"
}
```

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to submit issues and enhancement requests!

