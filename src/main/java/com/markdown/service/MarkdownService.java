package com.markdown.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    /**
     * Convert markdown text to HTML
     */
    public String convertToHtml(String markdown) {
        if (markdown == null || markdown.trim().isEmpty()) {
            return "";
        }

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    /**
     * Validate if text is valid markdown
     */
    public boolean isValidMarkdown(String markdown) {
        try {
            parser.parse(markdown);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

