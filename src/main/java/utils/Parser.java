package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static List<String> parseArgs(String input) {
        List<String> arguments = new ArrayList<>();

        Pattern pattern = Pattern.compile("'([^']*)'|\\S+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                arguments.add(matcher.group(1));
            } else {
                arguments.add(matcher.group());
            }
        }
        return arguments;
    }
}
