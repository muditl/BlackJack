/*import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PlayBlackJack {
    static LinkedList<Player> players = new LinkedList<>();
    static BlackJack blackJack = new BlackJack();

    public static void playBlackJack() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        startup(scanner);
        blackJack.play(players, scanner);
        anotherGame(scanner);
    }

    public static void startup(Scanner scanner) throws InterruptedException {
        System.out.println("Hello! Welcome to BlackJack by Mudit Lodha.");
        TimeUnit.SECONDS.sleep(2
        );
        System.out.println("Let's start with creating some players.");
        TimeUnit.SECONDS.sleep(2);
        players.add(Player.playerMaker(scanner));
        addPlayers(scanner);
        setDifficulty(scanner);
        System.out.println("Great! Now, lets play some BlackJack!");
    }

    public static void setDifficulty(Scanner scanner) {
        System.out.println("What difficulty would you like the Dealer to be? Easy, medium or hard?");
        String d = scanner.nextLine().toLowerCase();
        while (invalidInput(d, new String[]{"easy", "medium", "hard"}))
            d = scanner.nextLine().toLowerCase();
        if (d.equals("easy"))
            blackJack.difficulty = 50;
        if (d.equals("medium"))
            blackJack.difficulty = 75;
        if (d.equals("hard"))
            blackJack.difficulty = 100;
    }

    public static void anotherGame(Scanner scanner) throws InterruptedException {
        System.out.println("Do you wanna play another game? Enter yes or no.");
        String another = scanner.nextLine().toLowerCase();
        while (invalidInput(another, new String[]{"yes", "no"}))
            another = scanner.nextLine().toLowerCase();
        if (another.equals("no")) {
            endCard();
        } else if (another.equals(("yes"))) {
            System.out.println("LET'S GOoOOoooOOoOO!!!1!!111!!");
            TimeUnit.SECONDS.sleep(1);
            addPlayers(scanner);
            removePlayers(scanner);
            setDifficulty(scanner);
            System.out.println("Let's start the next round then!");
            blackJack.play(players, scanner);
            anotherGame(scanner);
        }

    }

    public static void endCard() {
        System.out.println("Thank you for playing BlackJack by Mudit Lodha.");
    }

    public static void addPlayers(Scanner scanner) {
        System.out.println("Would you like another player? Enter yes or no.");
        String in = scanner.nextLine().toLowerCase();
        while (invalidInput(in, new String[]{"yes", "no"}))
            in = scanner.nextLine().toLowerCase();
        while (in.equals("yes")) {
            players.add(Player.playerMaker(scanner));
            System.out.println("Would you like another player? Enter yes or no.");
            in = scanner.nextLine().toLowerCase();
            while (invalidInput(in, new String[]{"yes", "no"}))
                in = scanner.nextLine().toLowerCase();
        }
    }

    public static void removePlayers(Scanner scanner) {
        System.out.println("Would you like to remove a player?");
        String remove = scanner.nextLine().toLowerCase();
        while (invalidInput(remove, new String[]{"yes", "no"}))
            remove = scanner.nextLine().toLowerCase();
        while (remove.equals("yes")) {
            boolean removed = false;
            System.out.println("What is the first name of the player?");
            String Fname = scanner.nextLine();
            System.out.println("What is the last name of the player?");
            String Lname = scanner.nextLine();
            System.out.println("What is the nationality of the player?");
            String nat = scanner.nextLine();
            for (Player p : players) {
                if (p.FName.equals(Fname))
                    if (p.LName.equals(Lname))
                        if (p.nationality.equals(nat))
                            removed = players.remove(p);

            }
            if (removed)
                System.out.println("Successfully removed!");
            else
                System.out.println("Could not find player :/");
            System.out.println("Would you like to remove a player?");
            remove = scanner.nextLine().toLowerCase();
            while (invalidInput(remove, new String[]{"yes", "no"}))
                remove = scanner.nextLine().toLowerCase();
        }
    }

    public static boolean invalidInput(String input, String[] range) {
        for (String string : range) {
            if (string.equals(input))
                return false;
        }
        System.out.println("The input you entered is invalid. Please try again, dumbass.");
        return true;
    }
}

 */