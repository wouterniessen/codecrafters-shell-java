import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] sinput = input.split(" ", 2);
            String command =  sinput[0];

            String[] arguments;
            if (sinput.length > 1) {
                arguments = new String[]{command, sinput[1]};
            } else {
                arguments = new String[]{command};
            }

            Command cmd = Commands.get(commmand);
            if (cmd != null) {
                cmd.execute(arguments);
            } else {
                System.out.println(String.format("%s: command not found", command));
            }
        }
        scanner.close();
    }
}
