import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean state = true;
        while (state) {
            System.out.print("$ ");
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                state = false;
            } else {
                System.out.println(String.format("%s: command not found", command));
            }
        }
        scanner.close();
    }
}
