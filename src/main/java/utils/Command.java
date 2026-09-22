package utils;

import java.util.List;
import java.io.PrintStream;

public interface Command {
    void execute(List<String> args, PrintStream output);
}
