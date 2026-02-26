public class Type {
    List<String> builtinCommands = Arrays.asList("echo", "exit", "type");

    protected static void getType(String command) {
        if (Arrays.stream(builtinCommands).anyMatch(command::equals)) {
            System.out.println(String.format("%s is a shell builtin", command))
        } else {
            System.out.println(String.format("%s: not found", command));
        }
    }
}