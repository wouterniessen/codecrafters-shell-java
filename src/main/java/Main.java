import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String command = scanner.nextLine();
            if (command.startsWith("echo ")) {
                System.out.println(command.substring(5));
            }
            else if (command.equals("exit")) {
                break;
            } else {
                System.out.println(String.format("%s: command not found", command));
            }
        }
        scanner.close();
    }
}
