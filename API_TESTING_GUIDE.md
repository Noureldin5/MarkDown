# API Testing Guide

This guide shows you how to test all the endpoints of the Markdown Note-taking API.

## Base URL
```
http://localhost:8080
```

## Prerequisites
- Application must be running (`mvn spring-boot:run`)
- Use tools like Postman, cURL, or any REST client

---

## 1. Get API Information

**Request:**
```http
GET http://localhost:8080/
```

**Response:**
```json
{
  "application": "Markdown Note-taking API",
  "version": "1.0.0",
  "endpoints": { ... }
}
```

---

## 2. Create a Note

**Request:**
```http
POST http://localhost:8080/api/notes
Content-Type: application/json

{
  "title": "My First Note",
  "content": "# Introduction\n\nThis is my **first** markdown note!\n\n## Features\n- Easy to use\n- Supports markdown"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notes ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"My First Note\",\"content\":\"# Introduction\n\nThis is my **first** markdown note!\"}"
```

**Response:**
```json
{
  "id": 1,
  "title": "My First Note",
  "content": "# Introduction\n\nThis is my **first** markdown note!",
  "fileName": null,
  "createdAt": "2025-12-12T10:30:00",
  "updatedAt": "2025-12-12T10:30:00"
}
```

---

## 3. Upload Markdown File

**Request:**
```http
POST http://localhost:8080/api/notes/upload
Content-Type: multipart/form-data

file: [select sample-note.md]
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notes/upload ^
  -F "file=@sample-note.md"
```

**Response:**
```json
{
  "id": 2,
  "title": "sample-note",
  "content": "# Sample Markdown Note...",
  "fileName": "sample-note.md",
  "createdAt": "2025-12-12T10:31:00",
  "updatedAt": "2025-12-12T10:31:00"
}
```

---

## 4. List All Notes

**Request:**
```http
GET http://localhost:8080/api/notes
```

**cURL:**
```bash
curl http://localhost:8080/api/notes
```

**Response:**
```json
[
  {
    "id": 2,
    "title": "sample-note",
    "content": "# Sample Markdown Note...",
    "fileName": "sample-note.md",
    "createdAt": "2025-12-12T10:31:00",
    "updatedAt": "2025-12-12T10:31:00"
  },
  {
    "id": 1,
    "title": "My First Note",
    "content": "# Introduction...",
    "fileName": null,
    "createdAt": "2025-12-12T10:30:00",
    "updatedAt": "2025-12-12T10:30:00"
  }
]
```

---

## 5. Get a Specific Note

**Request:**
```http
GET http://localhost:8080/api/notes/1
```

**cURL:**
```bash
curl http://localhost:8080/api/notes/1
```

**Response:**
```json
{
  "id": 1,
  "title": "My First Note",
  "content": "# Introduction\n\nThis is my **first** markdown note!",
  "fileName": null,
  "createdAt": "2025-12-12T10:30:00",
  "updatedAt": "2025-12-12T10:30:00"
}
```

---

## 6. Update a Note

**Request:**
```http
PUT http://localhost:8080/api/notes/1
Content-Type: application/json

{
  "title": "Updated Title",
  "content": "# Updated Content\n\nThis note has been updated with new information."
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/notes/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Updated Title\",\"content\":\"# Updated Content\n\nThis note has been updated.\"}"
```

**Response:**
```json
{
  "id": 1,
  "title": "Updated Title",
  "content": "# Updated Content\n\nThis note has been updated.",
  "fileName": null,
  "createdAt": "2025-12-12T10:30:00",
  "updatedAt": "2025-12-12T10:35:00"
}
```

---

## 7. Check Grammar of a Note

**Request:**
```http
POST http://localhost:8080/api/notes/1/check-grammar
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notes/1/check-grammar
```

**Response:**
```json
{
  "totalErrors": 0,
  "errors": [],
  "summary": "No grammar issues found!"
}
```

**Example with errors:**
```json
{
  "totalErrors": 2,
  "errors": [
    {
      "message": "Use 'a' instead of 'an' if the following word doesn't start with a vowel sound",
      "line": 1,
      "column": 10,
      "length": 2,
      "context": "This are an test sentence",
      "suggestions": ["a"]
    }
  ],
  "summary": "Found 2 potential grammar issue(s)."
}
```

---

## 8. Check Grammar of Text (without saving)

**Request:**
```http
POST http://localhost:8080/api/notes/check-grammar
Content-Type: application/json

{
  "text": "This are a test sentence with grammar error."
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notes/check-grammar ^
  -H "Content-Type: application/json" ^
  -d "{\"text\":\"This are a test sentence with grammar error.\"}"
```

**Response:**
```json
{
  "totalErrors": 1,
  "errors": [
    {
      "message": "Did you mean 'is'?",
      "line": 0,
      "column": 5,
      "length": 3,
      "context": "This are a test sentence",
      "suggestions": ["is"]
    }
  ],
  "summary": "Found 1 potential grammar issue(s)."
}
```

---

## 9. Render Note as HTML

**Request:**
```http
GET http://localhost:8080/api/notes/1/render
```

**cURL:**
```bash
curl http://localhost:8080/api/notes/1/render
```

**Response:** (HTML page)
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rendered Note</title>
    ...
</head>
<body>
    <h1>Introduction</h1>
    <p>This is my <strong>first</strong> markdown note!</p>
</body>
</html>
```

Open in browser: `http://localhost:8080/api/notes/1/render`

---

## 10. Convert Markdown to HTML (without saving)

**Request:**
```http
POST http://localhost:8080/api/notes/render
Content-Type: application/json

{
  "markdown": "# Hello World\n\nThis is **bold** and this is *italic*.\n\n## Code Example\n\n```java\npublic class Test {}\n```"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notes/render ^
  -H "Content-Type: application/json" ^
  -d "{\"markdown\":\"# Hello World\n\nThis is **bold** text.\"}"
```

**Response:**
```json
{
  "html": "<h1>Hello World</h1>\n<p>This is <strong>bold</strong> and this is <em>italic</em>.</p>\n<h2>Code Example</h2>\n<pre><code class=\"language-java\">public class Test {}\n</code></pre>\n"
}
```

---

## 11. Delete a Note

**Request:**
```http
DELETE http://localhost:8080/api/notes/1
```

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/notes/1
```

**Response:**
```json
{
  "message": "Note deleted successfully"
}
```

---

## Error Responses

### 404 - Note Not Found
```json
{
  "timestamp": "2025-12-12T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Note not found with id: 999",
  "path": "/api/notes/999"
}
```

### 400 - Bad Request
```json
{
  "timestamp": "2025-12-12T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Only .md files are allowed",
  "path": "/api/notes/upload"
}
```

### 413 - File Too Large
```json
{
  "timestamp": "2025-12-12T10:30:00",
  "status": 413,
  "error": "Payload Too Large",
  "message": "File size exceeds maximum allowed size (10MB)",
  "path": "/api/notes/upload"
}
```

---

## Testing Workflow

### Complete Test Sequence:

1. **Start the application**
   ```bash
   mvn spring-boot:run
   ```

2. **Create a note**
   ```bash
   curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d "{\"title\":\"Test\",\"content\":\"# Test note\"}"
   ```

3. **List all notes**
   ```bash
   curl http://localhost:8080/api/notes
   ```

4. **Check grammar**
   ```bash
   curl -X POST http://localhost:8080/api/notes/1/check-grammar
   ```

5. **View rendered HTML in browser**
   ```
   http://localhost:8080/api/notes/1/render
   ```

6. **Upload a file**
   ```bash
   curl -X POST http://localhost:8080/api/notes/upload -F "file=@sample-note.md"
   ```

---

## Postman Collection

Import these settings into Postman:

**Base URL Variable:** `{{baseUrl}}` = `http://localhost:8080`

All endpoints use the base URL + path from the examples above.

---

## H2 Database Console

Access the database console at:
```
http://localhost:8080/h2-console
```

**Settings:**
- JDBC URL: `jdbc:h2:file:./data/notesdb`
- Username: `sa`
- Password: (leave empty)

Run SQL queries:
```sql
SELECT * FROM notes;
SELECT * FROM notes WHERE title LIKE '%test%';
DELETE FROM notes WHERE id = 1;
```

