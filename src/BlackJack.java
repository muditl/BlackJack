import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BlackJack {

    int difficulty;
    Deck deck;
    Player dealer = new Player("Dealer", "Dealer", "Dealer", Integer.MAX_VALUE);


    public void play(LinkedList<Player> players, Scanner scanner) throws InterruptedException {
        deck = new Deck();

        //give hands
        for (Player player : players) {
            player.hand = new Hand();
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


        showDealerHandFull();
        System.out.println("");

        for (Player player : players) {
            if (player.hand.tie) {
                System.out.println(player.toString() + ", You have tied, and don't win or lose any money");
            } else if (player.hand.win) {
                System.out.println(player.toString() + ", You win the round and hence win ₪" + player.hand.bet + "");
            } else {
                System.out.println(player.toString() + ", You lost this round, and lost ₪" + player.hand.bet);
            }
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

    }

    public void turn(Player player, Scanner scanner) {
        System.out.println(player.FName + " the " + player.nationality + ", it is your turn. Press enter to continue");
        scanner.nextLine();
        showDealerHand();
        showHand(player);
        System.out.println("Hit or stand, " + player.FName + "? Type your choice.");
        String in = scanner.nextLine().toLowerCase();
        while (PlayBlackJack.invalidInput(in, new String[]{"hit", "stand"}))
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
            while (PlayBlackJack.invalidInput(in, new String[]{"hit", "stand"}))
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