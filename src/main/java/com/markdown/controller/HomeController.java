package com.markdown.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Tag(name = "API Info", description = "API information and documentation links")
public class HomeController {

    @Operation(
            summary = "Get API information",
            description = "Returns basic API information and available endpoints"
    )
    @ApiResponse(responseCode = "200", description = "API information retrieved successfully")
    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Markdown Note-taking API");
        response.put("version", "1.0.0");
        response.put("description", "RESTful API for managing markdown notes with grammar checking and HTML rendering");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("POST /api/notes", "Create a new note (send title and content)");
        endpoints.put("POST /api/notes/upload", "Upload a markdown file");
        endpoints.put("GET /api/notes", "List all saved notes");
        endpoints.put("GET /api/notes/{id}", "Get a specific note");
        endpoints.put("PUT /api/notes/{id}", "Update a note");
        endpoints.put("DELETE /api/notes/{id}", "Delete a note");
        endpoints.put("POST /api/notes/{id}/check-grammar", "Check grammar of a note");
        endpoints.put("POST /api/notes/check-grammar", "Check grammar of provided text");
        endpoints.put("GET /api/notes/{id}/render", "Get HTML rendered version of a note");
        endpoints.put("POST /api/notes/render", "Convert markdown text to HTML");

        response.put("endpoints", endpoints);

        return ResponseEntity.ok(response);
    }
}

