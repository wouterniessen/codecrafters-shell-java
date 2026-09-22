import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.io.PrintStream;
import utils.BuiltIn;
import utils.Command;
import utils.Parser;



public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        while (true) {
            PrintStream output = System.out;
            boolean closeOutput = false;

            System.out.print("$ ");
            String input = scanner.nextLine().trim();
            List<String> sinput = Parser.parseArgs(input);

            // check for output redirection
            String filename = null;
            if (sinput.contains(">")) {
                int index = sinput.indexOf(">") == -1 ? sinput.indexOf("1>") : sinput.indexOf(">");
                if (index == sinput.size() - 1) {
                    System.out.println("Error: no filename provided for output redirection");
                    continue;
                }
                filename = sinput.get(index + 1);
                sinput = sinput.subList(0, index);
                output = new PrintStream(filename);
            } 

            String command =  sinput.get(0);

            Command cmd = BuiltIn.get(command);
            if (cmd != null) {
                try { 
                    if (filename != null) {
                        output = new PrintStream(filename);
                        closeOutput = true;
                    }
                    cmd.execute(sinput, output);
                } finally {
                    if (closeOutput) {
                        output.close();
                        continue;
                    }
                }
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
