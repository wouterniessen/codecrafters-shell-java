import java.util.Arrays;
import java.util.List;

public class Type {
    private static List<String> builtinCommands = Arrays.asList("echo", "exit", "type");

    protected static void getType(String command) {
        if (builtinCommands.stream().anyMatch(command::equals)) {
            System.out.println(String.format("%s is a shell builtin", command));
        } else {
            System.out.println(String.format("%s: not found", command));
        }
    }
}