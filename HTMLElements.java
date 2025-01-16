import java.text.MessageFormat;

public class HTMLElements
{
    public static String CreateHeadingHTMLElement(String textContent, Integer headingLevel) {
        return MessageFormat.format("<h{0}>{1}</h{0}>", headingLevel, textContent);
    }

    public static String CreateParagraphHTMLElement(String textContent) {
        return MessageFormat.format("<p>{0}</p>", textContent);
    }

    public static String CreateListHTMLElement(String textContent) {
        return MessageFormat.format("<li>{0}</li>", textContent);
    }

    public static String CreateAnchorHTMLElement(String textContent, String hrefAttribute) {
        return MessageFormat.format("<a href=\"{0}\" target=\"_blank\" rel=\"noopener noreferrer\">{1}</a>", hrefAttribute, textContent);
    }

    public static String CreateBlockQuoteHTMLElement(String textContent) {
        return MessageFormat.format("<blockquote>{0}</blockquote>", textContent);
    }

    public static String CreateEmphasisHTMLElement(String textContent) {
        return MessageFormat.format("<em>{0}</em>", textContent);
    }

    public static String CreateStrongHTMLElement(String textContent) {
        return MessageFormat.format("<strong>{0}</strong>", textContent);
    }

    public static String CreateStrongAndEmphasisHTMLElement(String textContent) {
        return CreateStrongHTMLElement(CreateEmphasisHTMLElement(textContent));
    }

    public static String CreateHorizontalRuleHTMLElement() {
        return "<hr />";
    }

    public static String HTMLTemplate() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>JMD</title>
                  <link rel="preconnect" href="https://fonts.googleapis.com">
                  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                  <link href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&display=swap" rel="stylesheet">
                  {0}
                </head>
                <body>
                 <div>
                  {1}
                 </div>
                </body>
                </html>
                """;
    }

    public static String CSSTemplate() {
        return """
                  <style>
                    :root {
                        background-color: %s;
                    }
                    
                    body {
                     margin: 0;
                     padding: 0;
                     display: flex;
                     justify-content: center;
                    }
                    
                    body * {
                     color: %s;
                     font-family: "Lato", serif;
                     box-sizing: border-box;
                     text-wrap: wrap;
                    }
                    
                    body div {
                     display: flex;
                     flex-direction: column;
                     width: 80vw;
                    }
                    
                    strong {
                     font-weight: bold;
                    }
                    
                    em {
                     font-weight: italic;
                    }
                    
                    hr {
                     height: 4px;
                     background-color: %s;
                     width: 80vw;
                    }
                    
                    blockquote {
                     background-color: %s;
                     border-left: 2px solid %s;
                     padding: 16px;
                     border-radius: 4px;
                    }
                  </style>
                  """;
    }
}
