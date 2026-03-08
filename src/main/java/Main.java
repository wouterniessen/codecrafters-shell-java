import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import utils.BuiltIn;
import utils.Command;
import utils.Parser;



public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().trim();
            List<String> sinput = Parser.parseArgs(input);

            // String[] sinput = input.split("\\s+");
            String command =  sinput.get(0);

            Command cmd = BuiltIn.get(command);
            if (cmd != null) {
                cmd.execute(sinput);
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
