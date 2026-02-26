import java.util.HashMap;
import java.util.Map;

interface Command {
    void execute(String[] args);
}

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
            System.out.println("echo: missing operand")
    }
}

class Type implements Command {
    public void execute()
}
