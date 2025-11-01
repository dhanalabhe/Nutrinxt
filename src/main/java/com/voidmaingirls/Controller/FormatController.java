package com.voidmaingirls.Controller;

import org.fxmisc.richtext.InlineCssTextArea;

public class FormatController {

    public void formatAndDisplayResponse(InlineCssTextArea area, String cleanResponse) {
        area.clear();
        String[] lines = cleanResponse.split("\\n");

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                area.appendText("\n");
                continue;
            }

            // Major Header (e.g., "Wednesday, July 23, 2025")
            if (trimmed.matches("^[A-Z][a-z]+,\\s+[A-Z][a-z]+\\s+\\d{1,2},\\s+\\d{4}:?$")) {
                int s = area.getLength();
                area.appendText(trimmed + "\n");
                area.setStyle(s, area.getLength(), "-fx-font-weight:bold; -fx-font-size:18px; -fx-fill:#2E8B57;");
                continue;
            }

            // Subheader (e.g., recipe name)
            if (Character.isUpperCase(trimmed.charAt(0)) && !trimmed.endsWith(":") && !trimmed.contains(" ")) {
                int s = area.getLength();
                area.appendText(trimmed + "\n");
                area.setStyle(s, area.getLength(), "-fx-font-weight:bold; -fx-font-size:16px; -fx-fill:#333333;");
                continue;
            }

            // Bullet/list item
            if (trimmed.startsWith("-")) {
                int s = area.getLength();
                area.appendText("• " + trimmed.substring(1).trim() + "\n");
                area.setStyle(s, area.getLength(), "-fx-font-size:20px; -fx-fill:#444444;");
                continue;
            }

            // Tips (Prep Tip, Dressing)
            if (trimmed.startsWith("Prep Tip:") || trimmed.startsWith("Dressing:") || trimmed.startsWith("Note:")) {
                int s = area.getLength();
                area.appendText(trimmed + "\n");
                area.setStyle(s, area.getLength(), "-fx-font-style:italic; -fx-font-size:13px; -fx-fill:#555555;");
                continue;
            }

            // Default paragraph
            int s = area.getLength();
            area.appendText(trimmed + "\n");
            area.setStyle(s, area.getLength(), "-fx-font-weight:bold;-fx-font-size:20px; -fx-fill:#2E8B57;");
        }
    }

    public void displayRawResponse(InlineCssTextArea area, String rawText) {
       area.clear();

    for (String line : rawText.split("\n")) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            area.appendText("\n");
            continue;
        }

        if (trimmed.toLowerCase().startsWith("additional ingredients")) {
            area.appendText("\n\n");
            area.appendText("=== " + trimmed.toUpperCase() + " ===\n");
        } else {
            area.appendText(trimmed + "\n");
        }
    }
    }
}
