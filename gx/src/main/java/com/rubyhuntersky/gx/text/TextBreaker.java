package com.rubyhuntersky.gx.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wehjin
 * @since 1/30/15.
 */

public class TextBreaker {
    public List<TextLine> breakText(String text, int maxLineWidth, int maxLines, TextRuler ruler) {
        List<TextLine> textLines = new ArrayList<>();
        if (maxLines < 1) {
            return textLines;
        }

        TextLine currentTextLine;
        List<String> paragraphs = getParagraphs(text, maxLines + 1);
        for (String paragraph : paragraphs) {
            if (textLines.size() > maxLines) {
                break;
            }
            currentTextLine = new TextLine("", 0);
            textLines.add(currentTextLine);
            for (String word : getWords(paragraph.trim())) {
                if (textLines.size() > maxLines) {
                    break;
                }
                if (word.length() == 0) {
                    continue;
                }
                String lineWithWord = currentTextLine.lineWidth == 0 ? word : (currentTextLine.text + " " + word);
                float lineWithWordWidth = ruler.getTextWidth(lineWithWord);

                // If the word fits on the current line, add it to the line.
                if (lineWithWordWidth <= maxLineWidth) {
                    currentTextLine.text = lineWithWord;
                    currentTextLine.lineWidth = lineWithWordWidth;
                    continue;
                }

                // If the word makes the current line too wide and it is the first word in the line, add the word to the line and start a new line.
                if (currentTextLine.lineWidth == 0) {
                    currentTextLine.text = lineWithWord;
                    currentTextLine.lineWidth = lineWithWordWidth;
                    currentTextLine = new TextLine("", 0);
                    textLines.add(currentTextLine);
                    continue;
                }

                // If the word makes the line too wide and it isn't the first word in the line, start a new line with the word.
                currentTextLine = new TextLine(word, ruler.getTextWidth(word));
                textLines.add(currentTextLine);
            }
        }
        if (textLines.size() > maxLines) {
            TextLine textLine = textLines.get(maxLines - 1);
            while (textLine.text.length() > 0) {
                String lineWithEllipsis = textLine.text + "\u2026";
                float lineWithEllipsisWidth = ruler.getTextWidth(lineWithEllipsis);
                if (lineWithEllipsisWidth <= maxLineWidth) {
                    textLine.text = lineWithEllipsis;
                    textLine.lineWidth = lineWithEllipsisWidth;
                    break;
                }
                textLine.text = textLine.text.substring(0, textLine.text.length() - 1);
                textLine.lineWidth = ruler.getTextWidth(textLine.text);
            }
            if (textLine.text.length() == 0) {
                textLine.text = "\u2026";
                textLine.lineWidth = ruler.getTextWidth(textLine.text);
            }
        }
        return textLines.subList(0, Math.min(textLines.size(), maxLines));
    }

    List<String> getParagraphs(String text, int maxParagraphs) {
        return Arrays.asList(text.split("\n", maxParagraphs));
    }

    List<String> getWords(String text) {
        return Arrays.asList(text.split("\\s+"));
    }
}
