import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BlackJack {

    int difficulty;
    Deck deck;
    Player dealer = new Player("Dealer", "Dealer", "Dealer", 0);


    public void round(LinkedList<Player> players, Scanner scanner) throws InterruptedException {
        deck = new Deck();

        //give hands
        for (Player player : players) {
            player.hand = new Hand();
            System.out.println(player.FName + " ");
            bet(scanner, player);
            System.out.println("player's balance:");
            System.out.println(player.balance);
            giveHand(player);
        }

        //give the dealer a hand
        dealer.hand = new Hand();
        giveHand(dealer);

        //do turns
        for (Player player : players) {
            turn(player, scanner);
        }
        dealerTurn();

        //win and distribute bets
        determineWinners(players);

        System.out.print("Calculating results");
        TimeUnit.SECONDS.sleep(1);
        System.out.print(".");
        TimeUnit.SECONDS.sleep(1);
        System.out.print(".");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(".");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("");
        showDealerHandFull();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("");

        for (Player player : players) {
            if (player.hand.tie) {
                System.out.println(player.toString() + ", You have tied, and don't win or lose any money");
            } else if (player.hand.win) {
                System.out.println(player.toString() + ", You win the round and hence win ₪" + player.hand.bet);
                player.balance += player.hand.bet;
            } else {
                System.out.println(player.toString() + ", You lost this round, and lost ₪" + player.hand.bet);
                player.balance -= player.hand.bet;
            }
            player.hand.bet = 0;
            TimeUnit.SECONDS.sleep(1);
        }

    }

    public void determineWinners(LinkedList<Player> players) {
        for (Player player : players)
            determineWinner(player);
    }

    public void determineWinner(Player player) {
        if (player.hand.bust)
            player.hand.win = false;
        else {
            if (dealer.hand.bust)
                player.hand.win = true;
            else if (player.hand.total > dealer.hand.total)
                player.hand.win = true;
            else if (player.hand.total == dealer.hand.total) {
                if (player.hand.numberOfCards < dealer.hand.numberOfCards)
                    player.hand.win = true;
                else if (player.hand.numberOfCards == dealer.hand.numberOfCards)
                    player.hand.tie = true;
                else
                    player.hand.win = false;
            } else
                player.hand.win = false;
        }

    }

    public void dealerTurn() {
        int randomNum = (int) (Math.random() * difficulty);
        while (dealer.hand.total < 14 && randomNum > 30) {
            hit(dealer);
            randomNum = (int) (Math.random() * difficulty);
        }
        while (dealer.hand.total > 14 && 120 - randomNum < 75) {
            hit(dealer);
            randomNum = (int) (Math.random() * difficulty);
        }

    }

    public void turn(Player player, Scanner scanner) {
        System.out.println(player.FName + " the " + player.nationality + ", it is your turn. Press enter to continue");
        scanner.nextLine();
        showDealerHand();
        showHand(player);
        System.out.println("Hit or stand, " + player.FName + "? Type your choice.");
        String in = scanner.nextLine().toLowerCase();
        while (invalidInput(in, new String[]{"hit", "stand"}))
            in = scanner.nextLine().toLowerCase();
        while (in.equals("hit")) {
            hit(player);
            showHand(player);
            if (player.hand.total > 21) {
                System.out.println("You just got busted!");
                in = "stand";
                break;
            }
            System.out.println("hit or stand?");
            in = scanner.nextLine().toLowerCase();
            while (invalidInput(in, new String[]{"hit", "stand"}))
                in = scanner.nextLine().toLowerCase();
        }
        System.out.println("Your turn is over, " + player.FName + ".");
    }

    private void showDealerHand() {
        Card dealerCard = dealer.hand.hand.iterator().next();
        System.out.println("The Dealer's hand is:");
        System.out.print("HIDDEN CARD, ");
        System.out.println(dealerCard.toString());
    }

    private void showDealerHandFull() {
        StringBuilder res = new StringBuilder();
        res.append("The Dealer's hand was:\n");
        for (Card card : dealer.hand.hand) {
            res.append(card.toString());
            res.append(", ");
        }
        res.deleteCharAt(res.lastIndexOf(","));
        System.out.println(res);
    }


    public void showHand(Player player) {
        System.out.println(player.hand.toString());
    }

    public void giveHand(Player player) {
        hit(player);
        hit(player);
    }

    public void hit(Player player) {
        int random = (int) (Math.random() * deck.Deck.size() + 1);
        Card card = null;
        Iterator<Card> it = deck.Deck.iterator();
        for (int i = 0; i < random; i++) {
            card = it.next();
        }
        player.hand.add(card);
        deck.Deck.remove(card);
    }

    public void bet(Scanner scanner, Player player) {
        System.out.println("How much would you like to gamble in this round?");
        String inputBet = scanner.nextLine();
        boolean tooHigh = true;
        while (!Player.isNumeric(inputBet) && tooHigh) {
            inputBet = scanner.nextLine().toLowerCase();
            int bet = Integer.parseInt(inputBet);
            if (bet > player.balance) {
                System.out.println("You do not have the funds to make this bet. Your balance is " + player.balance + ".");
                tooHigh = true;
            } else
                tooHigh = false;
        }
        player.hand.bet = Integer.parseInt(inputBet);
    }


    static LinkedList<Player> players = new LinkedList<>();
    static BlackJack blackJack = new BlackJack();

    public static void start() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        startup(scanner);
        blackJack.round(players, scanner);
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
            blackJack.round(players, scanner);
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


class Hand {
    HashSet<Card> hand;
    int total;
    boolean bust = false;
    int numberOfCards;
    boolean win;
    boolean tie = false;
    int bet = 0;

    public Hand() {
        hand = new HashSet<>();
    }

    public void add(Card card) {
        hand.add(card);
        setTotal();
    }

    private void setTotal() {
        HashSet<Card> duplicate = (HashSet<Card>) hand.clone();
        total = 0;
        numberOfCards = 0;

        //make a new linked list with sorted cards in decreasing order.
        LinkedList<Card> sorted = new LinkedList<>();
        for (int i = 0; i < hand.size(); i++) {
            Card max = new Card(0, 1);
            for (Card card : duplicate) {
                if (card.value > max.value)
                    max = card;
            }
            duplicate.remove(max);
            sorted.add(max);
        }

        Iterator<Card> iterator = sorted.iterator();

        Card current;

        //add the values until reaching the aces
        while (iterator.hasNext()) {
            current = iterator.next();
            if (current.face)
                total += 10;
            else if (current.value == 1)
                break;
            else
                total += current.value;
            numberOfCards++;
        }
        if (hand.size() > numberOfCards) {
            int max = total + hand.size() - numberOfCards;
            if (max > 11)
                total += 1;
            else {
                total += 11;
                max = total + sorted.size() - numberOfCards;
            }
            numberOfCards++;
            //deal with the aces
            while (iterator.hasNext()) {
                current = iterator.next();
                if (max > 11)
                    total += 1;
                else {
                    total += 11;
                    max = total + sorted.size() - numberOfCards;
                }
                numberOfCards++;

            }
        }
        if (total > 21)
            bust = true;

    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Your hand:\n");
        for (Card card : hand) {
            res.append(card.toString());
            res.append(", ");
        }
        res.deleteCharAt(res.lastIndexOf(","));
        return res.toString();
    }
}