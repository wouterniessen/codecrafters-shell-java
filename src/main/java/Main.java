import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import utils.BuiltIn;
import utils.Command;



public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().trim();
            String[] sinput = input.split("\\s+");
            String command =  sinput[0];

            String[] arguments;
            if (sinput.length > 1) {
                arguments = new String[]{command, sinput[1]};
            } else {
                arguments = new String[]{command};
            }

            Command cmd = BuiltIn.get(command);
            if (cmd != null) {
                cmd.execute(arguments);
                continue;
            }


            Optional<Path> commandPath = BuiltIn.searchCommand(command);
            if (commandPath.isPresent()) {
                List<String> commandAndPath = new ArrayList<>();
                commandAndPath.add(commandPath.get().toString());
                commandAndPath.addAll(Arrays.asList(sinput).subList(1, sinput.length));
                try {
                    ProcessBuilder pb = new ProcessBuilder(commandAndPath);
                    pb.inheritIO();
                    pb.start().waitFor();
                }   catch (IOException e) {
                    e.printStackTrace();
                }
            } else { 
                System.out.println(String.format("%s: command not found", command));
            }
        }
    }
}
