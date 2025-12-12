package com.markdown.service;

import com.markdown.dto.GrammarCheckResponse;
import com.markdown.dto.GrammarError;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrammarService {

    private final JLanguageTool languageTool;

    public GrammarService() {
        this.languageTool = new JLanguageTool(new AmericanEnglish());
    }

    /**
     * Check grammar of the provided text
     */
    public GrammarCheckResponse checkGrammar(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new GrammarCheckResponse(0, new ArrayList<>(), "No text provided for grammar check.");
        }

        try {
            List<RuleMatch> matches = languageTool.check(text);
            List<GrammarError> errors = new ArrayList<>();

            for (RuleMatch match : matches) {
                GrammarError error = new GrammarError();
                error.setMessage(match.getMessage());
                error.setLine(match.getLine());
                error.setColumn(match.getColumn());
                error.setLength(match.getToPos() - match.getFromPos());

                // Get context around the error
                int contextStart = Math.max(0, match.getFromPos() - 20);
                int contextEnd = Math.min(text.length(), match.getToPos() + 20);
                error.setContext(text.substring(contextStart, contextEnd));

                // Get suggestions
                List<String> suggestionsList = match.getSuggestedReplacements();
                error.setSuggestions(suggestionsList.toArray(new String[0]));

                errors.add(error);
            }

            String summary = matches.isEmpty()
                ? "No grammar issues found!"
                : String.format("Found %d potential grammar issue(s).", matches.size());

            return new GrammarCheckResponse(matches.size(), errors, summary);

        } catch (IOException e) {
            throw new RuntimeException("Error checking grammar: " + e.getMessage(), e);
        }
    }
}

