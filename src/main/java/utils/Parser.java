package utils;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<String> parseArgs(String input) {
        List<String> args = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        boolean inSingleQuote = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '\'') {
                inSingleQuote = !inSingleQuote;
                continue;
            }

            if (Character.isWhitespace(c) && !inSingleQuote) {
                if (current.length() > 0) {
                    args.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            args.add(current.toString());
        }

        return args;
    }
}