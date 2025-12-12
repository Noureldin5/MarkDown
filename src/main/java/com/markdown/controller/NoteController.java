package com.markdown.controller;

import com.markdown.dto.GrammarCheckResponse;
import com.markdown.dto.NoteRequest;
import com.markdown.dto.NoteResponse;
import com.markdown.service.GrammarService;
import com.markdown.service.MarkdownService;
import com.markdown.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "API endpoints for managing markdown notes")
public class NoteController {

    private final NoteService noteService;
    private final MarkdownService markdownService;
    private final GrammarService grammarService;

    @Operation(
            summary = "Create a new note",
            description = "Creates a new markdown note with the provided title and content"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created successfully",
                    content = @Content(schema = @Schema(implementation = NoteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest request) {
        NoteResponse response = noteService.saveNote(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Upload a markdown file",
            description = "Uploads a .md file and saves it as a note. Max file size: 10MB"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File uploaded successfully",
                    content = @Content(schema = @Schema(implementation = NoteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file format or empty file"),
            @ApiResponse(responseCode = "413", description = "File size exceeds maximum allowed size (10MB)")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NoteResponse> uploadMarkdownFile(
            @Parameter(description = "Markdown file to upload (.md format only)")
            @RequestParam("file") MultipartFile file) throws IOException {
        NoteResponse response = noteService.uploadMarkdownFile(file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all notes",
            description = "Retrieves a list of all saved notes, sorted by creation date (newest first)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @Operation(
            summary = "Get a specific note",
            description = "Retrieves a note by its unique ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found",
                    content = @Content(schema = @Schema(implementation = NoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(
            @Parameter(description = "ID of the note to retrieve")
            @PathVariable Long id) {
        NoteResponse response = noteService.getNoteById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update a note",
            description = "Updates an existing note's title and content"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully",
                    content = @Content(schema = @Schema(implementation = NoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Note not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @Parameter(description = "ID of the note to update")
            @PathVariable Long id,
            @RequestBody NoteRequest request) {
        NoteResponse response = noteService.updateNote(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a note",
            description = "Permanently deletes a note from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteNote(
            @Parameter(description = "ID of the note to delete")
            @PathVariable Long id) {
        noteService.deleteNote(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Note deleted successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Check grammar of a note",
            description = "Checks the grammar of a saved note using LanguageTool and returns suggestions"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grammar check completed",
                    content = @Content(schema = @Schema(implementation = GrammarCheckResponse.class))),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PostMapping("/{id}/check-grammar")
    public ResponseEntity<GrammarCheckResponse> checkGrammar(
            @Parameter(description = "ID of the note to check")
            @PathVariable Long id) {
        String content = noteService.getNoteContent(id);
        GrammarCheckResponse response = grammarService.checkGrammar(content);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Check grammar of text",
            description = "Checks the grammar of any provided text without saving it as a note"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grammar check completed",
                    content = @Content(schema = @Schema(implementation = GrammarCheckResponse.class)))
    })
    @PostMapping("/check-grammar")
    public ResponseEntity<GrammarCheckResponse> checkGrammarText(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON object with 'text' field containing the text to check",
                    content = @Content(schema = @Schema(example = "{\"text\": \"This are a test sentence.\"}"))
            )
            @RequestBody Map<String, String> request) {
        String text = request.get("text");
        GrammarCheckResponse response = grammarService.checkGrammar(text);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Render note as HTML",
            description = "Converts a note's markdown content to styled HTML and returns a complete web page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTML rendered successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping(value = "/{id}/render", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> renderNoteAsHtml(
            @Parameter(description = "ID of the note to render")
            @PathVariable Long id) {
        String content = noteService.getNoteContent(id);
        String html = markdownService.convertToHtml(content);

        // Wrap in basic HTML structure
        String fullHtml = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Rendered Note</title>
                    <style>
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
                            line-height: 1.6;
                            max-width: 800px;
                            margin: 40px auto;
                            padding: 0 20px;
                            color: #333;
                        }
                        code {
                            background-color: #f4f4f4;
                            padding: 2px 6px;
                            border-radius: 3px;
                            font-family: 'Courier New', monospace;
                        }
                        pre {
                            background-color: #f4f4f4;
                            padding: 15px;
                            border-radius: 5px;
                            overflow-x: auto;
                        }
                        blockquote {
                            border-left: 4px solid #ddd;
                            margin: 0;
                            padding-left: 20px;
                            color: #666;
                        }
                        img {
                            max-width: 100%;
                            height: auto;
                        }
                    </style>
                </head>
                <body>
                    %s
                </body>
                </html>
                """.formatted(html);

        return ResponseEntity.ok(fullHtml);
    }

    @Operation(
            summary = "Convert markdown to HTML",
            description = "Converts any markdown text to HTML without saving it as a note"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Markdown converted successfully")
    })
    @PostMapping("/render")
    public ResponseEntity<Map<String, String>> renderMarkdownText(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON object with 'markdown' field containing the markdown text",
                    content = @Content(schema = @Schema(example = "{\"markdown\": \"# Hello\\n\\nThis is **bold** text.\"}"))
            )
            @RequestBody Map<String, String> request) {
        String markdown = request.get("markdown");
        String html = markdownService.convertToHtml(markdown);

        Map<String, String> response = new HashMap<>();
        response.put("html", html);

        return ResponseEntity.ok(response);
    }
}

