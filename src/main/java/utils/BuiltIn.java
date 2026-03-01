package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;


public class BuiltIn {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("exit", new Exit());
        commands.put("echo", new Echo());
        commands.put("type", new Type());
    }

    public static Command get(String name) {
        return commands.get(name);
    }

    public boolean isCommand(String command) {
        return commands.containsKey(command);
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
            System.out.println(args[1]);
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
                searchCommand(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchCommand(String name) throws IOException {
        String commandPaths = System.getenv("PATH");
        String[] paths = commandPaths.split(File.pathSeparator);

        boolean found = false;
        for (String path : paths) {
            Path dir = Paths.get(path);
            if (!Files.exists(dir)) {
                continue;
            }
            try {
                Optional<Path> result = Files.walk(dir)
                    .filter(p -> p.getFileName().toString().equals(name) && Files.isExecutable(p))
                    .findFirst();
                if (result.isPresent()) {
                    System.out.println(String.format("%s is %s", name, result.get()));
                    found = true;
                    break;
                };

            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }          
        }
        if (!found) {
            System.out.println(String.format("%s: not found", name));
        }
    } 
}
