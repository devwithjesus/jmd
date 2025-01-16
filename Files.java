import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Files {
    final static Integer HEADING_LEVEL_ONE = 1;
    final static Integer HEADING_LEVEL_TWO = 2;
    final static Integer HEADING_LEVEL_THREE = 3;
    final static Integer HEADING_LEVEL_FOUR = 4;
    final static Integer HEADING_LEVEL_FIVE = 5;
    final static Integer HEADING_LEVEL_SIX = 6;

    final static String MARKDOWN_HEADING_PATTERN = "#";

    final static String MARKDOWN_LIST_PATTERN = "-";

    final static String MARKDOWN_LINK_PATTERN_START = "[";
    final static String MARKDOWN_LINK_PATTERN_END = "]";

    final static String MARKDOWN_ATTRIBUTE_PATTERN_START = "(";
    final static String MARKDOWN_ATTRIBUTE_PATTERN_END = ")";

    final static String MARKDOWN_BLOCKQUOTE_PATTERN =  ">";

    final static String MARKDOWN_ITALIC_PATTERN = "*";
    final static String MARKDOWN_BOLD_PATTERN = "**";
    final static String MARKDOWN_ITALIC_BOLD_PATTERN = MARKDOWN_ITALIC_PATTERN + MARKDOWN_BOLD_PATTERN;

    final static String MARKDOWN_HORIZONTAL_RULE_PATTERN = "---";

    public static List<String> ReadFile(String filePath) {
        List<String> htmlElements = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                if(currentLine.isEmpty()) continue;
                htmlElements.add(ParseMarkdown(currentLine));
            }

        } catch (FileNotFoundException Error) {
            System.out.println("File does not exist.");
        } catch (IOException Error) {
            System.out.println("Something went wrong when trying to read the file, please restart the program.");
            System.exit(0);
        }

        return htmlElements;
    }

    public static void WriteFile(String css, List<String> htmlElements) {
        File outputHTML = new File("output/index.html");

        String html = MessageFormat.format(HTMLElements.HTMLTemplate(), css, String.join("\n", htmlElements));

        try {
            File parentDir = outputHTML.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            if(!outputHTML.exists()) {
                outputHTML.createNewFile();
            }

        } catch (IOException e) {
            System.out.println("Something went wrong when trying to create the file, please restart the program.");
            System.exit(0);
        }

        try (FileWriter fileWriter = new FileWriter(outputHTML)) {
            fileWriter.write(html);
        } catch (IOException e) {
            System.out.println("Something went wrong when trying to write, please restart the program.");
            System.exit(0);
        }
    }

    private static String ParseMarkdown(String markdownLine) {
        if(markdownLine.contains(BuildHeadingRegex(HEADING_LEVEL_SIX))) {
            return HTMLElements.CreateHeadingHTMLElement(ParseHeadingMarkdown(markdownLine), HEADING_LEVEL_SIX);
        } else if (markdownLine.contains(BuildHeadingRegex(HEADING_LEVEL_FIVE))) {
            return HTMLElements.CreateHeadingHTMLElement(ParseHeadingMarkdown(markdownLine), HEADING_LEVEL_FIVE);
        } else if (markdownLine.contains(BuildHeadingRegex(HEADING_LEVEL_FOUR))) {
            return HTMLElements.CreateHeadingHTMLElement(ParseHeadingMarkdown(markdownLine), HEADING_LEVEL_FOUR);
        } else if (markdownLine.contains(BuildHeadingRegex(HEADING_LEVEL_THREE))) {
            return HTMLElements.CreateHeadingHTMLElement(ParseHeadingMarkdown(markdownLine), HEADING_LEVEL_THREE);
        } else if (markdownLine.contains(BuildHeadingRegex(HEADING_LEVEL_TWO))) {
            return HTMLElements.CreateHeadingHTMLElement(ParseHeadingMarkdown(markdownLine), HEADING_LEVEL_TWO);
        } else if (markdownLine.contains(BuildHeadingRegex(HEADING_LEVEL_ONE))) {
            return HTMLElements.CreateHeadingHTMLElement(ParseHeadingMarkdown(markdownLine), HEADING_LEVEL_ONE);
        } else if (markdownLine.matches(MARKDOWN_HORIZONTAL_RULE_PATTERN)) {
            return HTMLElements.CreateHorizontalRuleHTMLElement();
        } else if (markdownLine.startsWith(MARKDOWN_LIST_PATTERN)) {
            return HTMLElements.CreateListHTMLElement(ParseTextContent(markdownLine, MARKDOWN_LIST_PATTERN));
        } else if (markdownLine.startsWith(MARKDOWN_LINK_PATTERN_START)) {
            return HTMLElements.CreateAnchorHTMLElement(ParseTextContent(markdownLine, MARKDOWN_LINK_PATTERN_START, MARKDOWN_LINK_PATTERN_END), ParseTextContent(markdownLine, MARKDOWN_ATTRIBUTE_PATTERN_START, MARKDOWN_ATTRIBUTE_PATTERN_END));
        } else if (markdownLine.startsWith(MARKDOWN_BLOCKQUOTE_PATTERN)) {
            return HTMLElements.CreateBlockQuoteHTMLElement(ParseTextContent(markdownLine, MARKDOWN_BLOCKQUOTE_PATTERN));
        } else if (markdownLine.startsWith(MARKDOWN_ITALIC_BOLD_PATTERN)) {
            return HTMLElements.CreateStrongAndEmphasisHTMLElement(ParseTextContent(markdownLine, MARKDOWN_ITALIC_BOLD_PATTERN, MARKDOWN_ITALIC_BOLD_PATTERN));
        } else if (markdownLine.startsWith(MARKDOWN_BOLD_PATTERN)) {
            return HTMLElements.CreateStrongHTMLElement(ParseTextContent(markdownLine, MARKDOWN_BOLD_PATTERN, MARKDOWN_BOLD_PATTERN));
        } else if (markdownLine.startsWith(MARKDOWN_ITALIC_PATTERN)) {
            return HTMLElements.CreateEmphasisHTMLElement(ParseTextContent(markdownLine, MARKDOWN_ITALIC_PATTERN, MARKDOWN_ITALIC_PATTERN));
        } else {
            return HTMLElements.CreateParagraphHTMLElement(markdownLine);
        }
    }

    private static String ParseHeadingMarkdown(String markdownLine) {
        return ParseTextContent(markdownLine, MARKDOWN_HEADING_PATTERN);
    }

    private static String ParseTextContent(String markdownLine, String pattern) {
        int lastIndexOfMarkdownPattern = markdownLine.lastIndexOf(pattern) + 1;

        return markdownLine.substring(lastIndexOfMarkdownPattern).trim();
    }

    private static String ParseTextContent(String markdownLine, String patternStart, String patternEnd) {
        int firstIndexOfMarkdownPattern = markdownLine.indexOf(patternStart) + patternStart.length();

        int lastIndexOfMarkdownPattern = markdownLine.lastIndexOf(patternEnd);

        return markdownLine.substring(firstIndexOfMarkdownPattern, lastIndexOfMarkdownPattern).trim();
    }

    private static String BuildHeadingRegex(Integer headingLevel) {
        return "#".repeat(headingLevel);
    }
}