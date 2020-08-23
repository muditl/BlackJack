
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player {
    String FName;
    String LName;
    String nationality;
    int balance;
    Hand hand = new Hand();

    Player(String FName, String LName, String nationality, int balance) {
        this.FName = FName;
        this.LName = LName;
        this.nationality = nationality;
        this.balance = balance;
    }

    public static Player playerMaker(Scanner scanner) {

        System.out.println("Enter First name: (cannot be empty)");
        String f = scanner.nextLine();
        if (f.equals(""))
            System.out.println("I said, it cannot be empty.");
        while (f.equals(""))
            f = scanner.nextLine();

        System.out.println("Enter last name: (cannot be empty)");
        String l = scanner.nextLine();
        if (l.equals(""))
            System.out.println("I said, it cannot be empty.");
        while (l.equals(""))
            l = scanner.nextLine();

        System.out.println("Enter Nationality: (cannot be empty)");
        String n = scanner.nextLine();
        if (n.equals(""))
            System.out.println("I said, it cannot be empty.");
        while (n.equals("")) {
            n = scanner.nextLine();
        }

        System.out.println("How much money would you like to gamble today? Enter a whole number");

        String bal = scanner.nextLine();
        while (!isNumeric(bal))
            bal = scanner.nextLine().toLowerCase();

        int b = Integer.parseInt(bal);
        System.out.println(b);
        return new Player(f, l, n, b);
    }

    public String toString() {
        return FName + " " + LName + " the " + nationality;
    }


    private static Pattern pattern = Pattern.compile("\\d+");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            System.out.println("Please enter a whole number.");
            return false;
        }
        if (pattern.matcher(strNum).matches())
            return true;
        else {
            System.out.println("Please enter a whole number.");
            return false;
        }
    }

}


