package com.markdown.service;

import com.markdown.dto.NoteRequest;
import com.markdown.dto.NoteResponse;
import com.markdown.entity.Note;
import com.markdown.exception.ResourceNotFoundException;
import com.markdown.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * Save a new note
     */
    @Transactional
    public NoteResponse saveNote(NoteRequest request) {
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note savedNote = noteRepository.save(note);
        return convertToResponse(savedNote);
    }

    /**
     * Upload a markdown file and save as note
     */
    @Transactional
    public NoteResponse uploadMarkdownFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.endsWith(".md")) {
            throw new IllegalArgumentException("Only .md files are allowed");
        }

        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        String title = fileName.replace(".md", "");

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setFileName(fileName);

        Note savedNote = noteRepository.save(note);
        return convertToResponse(savedNote);
    }

    /**
     * Get all notes
     */
    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get note by ID
     */
    public NoteResponse getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return convertToResponse(note);
    }

    /**
     * Update a note
     */
    @Transactional
    public NoteResponse updateNote(Long id, NoteRequest request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        Note updatedNote = noteRepository.save(note);
        return convertToResponse(updatedNote);
    }

    /**
     * Delete a note
     */
    @Transactional
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    /**
     * Get note content for processing
     */
    public String getNoteContent(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return note.getContent();
    }

    /**
     * Convert Note entity to NoteResponse DTO
     */
    private NoteResponse convertToResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getFileName(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}

