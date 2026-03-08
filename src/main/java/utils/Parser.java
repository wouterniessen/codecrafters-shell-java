package utils;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<String> parseArgs(String input) {

        List<String> args = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escaping = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            // handle escape
            if (escaping) {
                current.append(c);
                escaping = false;
                continue;
            }

            // escape start
            if (c == '\\' && !inSingleQuote) {
                escaping = true;
                continue;
            }

            // single quote toggle
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                continue;
            }

            // double quote toggle
            if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                continue;
            }

            // argument separator
            if ((c == ' ' || c == '\t') && !inSingleQuote && !inDoubleQuote) {
                if (current.length() > 0) {
                    args.add(current.toString());
                    current.setLength(0);
                }
                continue;
            }

            current.append(c);
        }

        if (current.length() > 0) {
            args.add(current.toString());
        }

        return args;
    }
}