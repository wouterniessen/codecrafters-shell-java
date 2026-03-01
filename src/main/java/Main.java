import java.io.IOException;
import java.nio.file.Path;
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

            String[] arguments = sinput;

            Command cmd = BuiltIn.get(command);
            if (cmd != null) {
                cmd.execute(arguments);
                continue;
            }


            Optional<Path> commandPath = BuiltIn.searchCommand(command);
            if (commandPath.isPresent()) {
                try {
                    ProcessBuilder pb = new ProcessBuilder(sinput);
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
