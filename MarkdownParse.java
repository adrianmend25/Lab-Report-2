//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int openBracket = markdown.indexOf("[", currentIndex);
            if (openBracket == -1){
                break;
            }
            if (markdown.indexOf("!", currentIndex) != -1) {
                int exMark = markdown.indexOf("!", currentIndex);
                if ((exMark + 1) == openBracket) {
                    currentIndex = markdown.indexOf(")", currentIndex) + 1;
                    continue;
                }
            }
            int exMark = markdown.indexOf("!", currentIndex);
            if ((exMark + 1) == openBracket){
                currentIndex = markdown.indexOf(")", currentIndex) + 1;
                continue;
            }
            int closeBracket = markdown.indexOf("]", openBracket);
            if (closeBracket == -1) {
                break;
            }
            int openParen = markdown.indexOf("(", closeBracket);
            if (openParen == -1) {
                break;
            }
            int closeParen = markdown.indexOf(")", openParen);
            if (closeParen == -1) {
                break;
            }
            currentIndex = closeParen + 1;
            String url = markdown.substring(openParen + 1, closeParen);
            if (url.indexOf(" ") != -1) {
                continue;
            }
            toReturn.add(url);
        }
        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}