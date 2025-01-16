import java.util.*;

public class Main {
    final static String WHITESPACE_REGEX = "\\s";

    final static String[] COMMAND_LIST = {"help", "jmd"};

    final static String[] CSS_TEMPLATE_LIGHT = {"#f7f7fa", "#0e0f18", "#bfa5ca", "#bfa5ca", "#b280b4"};

    final static String[] CSS_TEMPLATE_DARK = {"#060609", "#e9e9f2", "#bfa5ca", "#4f355a", "#7f4b81"};

    final static Map<String, String> COMMAND_DESCRIPTIONS = new HashMap<>(Map.ofEntries(
            Map.entry("help", "Provides a list of commands and what they do."),
            Map.entry("jmd", "JMD (Java Markdown) is the starting command to convert Markdown into HTML."),
            Map.entry("--darkmode", "--darkmode is a JMD flag to output HTML with a darker style of CSS instead of the default light style."),
            Map.entry("Supported Markdown", "Headings, normal text, links, lists, quotes, italic text, bold text, italic & bold text.")
    ));

    public static void main(String[] args) {
        System.out.println("Welcome to JMD (Java Markdown).\nThis is a tool to convert MarkDown to a HTML file.");

        System.out.println("Use the 'help' command otherwise have fun converting.");

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();

        String[] userInputTokens = userInput.split(WHITESPACE_REGEX);

        String userCommand = userInputTokens[0].toLowerCase();

        while(!Arrays.asList(COMMAND_LIST).contains(userCommand)) {
            System.out.println("You did not enter a correct command.\nUse 'help' for a list of commands");

            userInput = scanner.nextLine();

            userInputTokens = userInput.split(WHITESPACE_REGEX);

            userCommand = userInputTokens[0].toLowerCase();
        }

        scanner.close();

        switch(userCommand) {
            case "help":
                COMMAND_DESCRIPTIONS.forEach((key, value) -> System.out.println(key + ": " + value));
                break;
            case "jmd":
                String filePath = userInputTokens[1];

                boolean darkmodeFlag = Arrays.asList(userInputTokens).contains("--darkmode");

                JMD(filePath, darkmodeFlag);
                break;
        }

    }


    private static void JMD(String filePath, boolean darkmodeFlag) {
        List<String> htmlElements = Files.ReadFile(filePath);

        String css = HTMLElements.CSSTemplate();

        if (!darkmodeFlag) {
            css = String.format(css, CSS_TEMPLATE_LIGHT[0], CSS_TEMPLATE_LIGHT[1], CSS_TEMPLATE_LIGHT[2], CSS_TEMPLATE_LIGHT[3], CSS_TEMPLATE_LIGHT[4]);
        } else {
            css = String.format(css, CSS_TEMPLATE_DARK[0], CSS_TEMPLATE_DARK[1], CSS_TEMPLATE_DARK[2], CSS_TEMPLATE_DARK[3], CSS_TEMPLATE_DARK[4]);
        }

        Files.WriteFile(css, htmlElements);
    }
}