package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;


public class BuiltIn {
    private static final Map<String, Command> commands = new HashMap<>();
    private static Optional<Path> currentDirectory = Optional.empty();

    static {
        commands.put("exit", new Exit());
        commands.put("echo", new Echo());
        commands.put("type", new Type());
        commands.put("pwd", new Pwd());
        commands.put("cd", new Cd());
    }

    public static Command get(String name) {
        return commands.get(name);
    }

    public boolean isCommand(String command) {
        return commands.containsKey(command);
    }

    public static Optional<Path> searchCommand(String name) throws IOException {
        String commandPaths = System.getenv("PATH");
        String[] paths = commandPaths.split(File.pathSeparator);

        for (String path : paths) {
            Path dir = Paths.get(path);
            if (!Files.exists(dir)) {
                continue;
            }
            try (Stream<Path> stream = Files.list(dir)){
                Optional<Path> result = stream
                    .filter(p -> p.getFileName().toString().equals(name) 
                            && Files.isExecutable(p))
                    .findFirst();
                if (result.isPresent()) {
                    return result;
                }
            }   
        }
        return Optional.empty();
    }

    public static Optional<Path> getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(Path newDirectory) {
        currentDirectory = Optional.of(newDirectory);
    }
}

class Exit implements Command {
    public void execute(String[] args) {
        if (args.length > 1)
            System.exit(Integer.parseInt(args[1]));
        else
            System.exit(0);
    }
}

class Echo implements Command {
    public void execute(String[] args) {
        if (args.length > 1)
            System.out.println(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
        else 
            System.out.println("echo: missing operand");
    }
}

class Type implements Command {
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("type: missing operand");
        }

        String name = args[1];

        Command cmd = BuiltIn.get(name);
        if (cmd != null) {
            System.out.println(String.format("%s is a shell builtin", name));
        } else {
            try {
                Optional<Path> result = BuiltIn.searchCommand(name);
                result.ifPresentOrElse(
                    p -> System.out.println(String.format("%s is %s", name, p)), 
                    () -> System.out.println(String.format("%s: not found", name))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Pwd implements Command {
    public void execute(String[] args) {
        Optional<Path> currentDirectory = BuiltIn.getCurrentDirectory();
        if (!currentDirectory.isPresent()) {
            String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
            System.out.println(userDirectory);
        } else {
            String userDirectory = currentDirectory.get().toString();
            System.out.println(userDirectory);
        }
    }
}

class Cd implements Command {
    public void execute(String[] args) {
        if (args.length > 2) {
            System.out.println(String.format("To many arguments for: %s", String.join(" ", args)));
            return;
        }

        String dir = args[1];
        Path dirPath = Paths.get(dir);
        if (dir.startsWith("/")) {
            if (Files.exists(dirPath)) {
                BuiltIn.setCurrentDirectory(dirPath);
                return;
            }
        }
        
        if (dir.startsWith("./")) {

        }
        if (dir.startsWith("../")) {

        } 
        if (dir.startsWith("~")) {

        } else {
            System.out.println(String.format("cd: %s: No such file or directory", dir));
        }
    }
}
