import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;


public class BlackJackTest {
    Player player1;
    Player player2;
    Player player3;
    Deck deck;
    BlackJack blackJack;

    @Before
    public void setUp() {
        player1 = new Player("P1", "P1", "Nation1", 0);
        player2 = new Player("P2", "P2", "Nation2", 0);
        player3 = new Player("P3", "P3", "Nation3", 0);
        deck = new Deck();
        blackJack = new BlackJack();
    }

    @Test
    public void testTwoAces() {
        player1.hand.add(new Card(1, 1));
        player1.hand.add(new Card(1, 2));
        assert (player1.hand.total == 12);
        assert (player1.hand.numberOfCards == 2);
    }

    @Test
    public void testThreeAces() {
        player1.hand.add(new Card(1, 1));
        player1.hand.add(new Card(1, 2));
        player1.hand.add(new Card(1, 3));
        assert (player1.hand.total == 13);
        assert (player1.hand.numberOfCards == 3);
    }

    @Test
    public void testFourAces() {
        player1.hand.add(new Card(1, 1));
        player1.hand.add(new Card(1, 2));
        player1.hand.add(new Card(1, 3));
        player1.hand.add(new Card(1, 4));
        assert (player1.hand.total == 14);
        assert (player1.hand.numberOfCards == 4);
    }

    @Test
    public void testAceQueen() {
        player1.hand.add(new Card(12, 1));
        player1.hand.add(new Card(1, 2));
        assert (player1.hand.total == 21);
        assert (player1.hand.numberOfCards == 2);
    }

    @Test
    public void testAceQueenJack() {
        player1.hand.add(new Card(12, 1));
        player1.hand.add(new Card(11, 1));
        player1.hand.add(new Card(1, 2));
        assert (player1.hand.total == 21);
        assert (player1.hand.numberOfCards == 3);
    }

    @Test
    public void testWinner1() {
        player1.hand.add(new Card(12, 1));
        player1.hand.add(new Card(7, 2));
        blackJack.dealer.hand.hand.add(new Card(5, 1));
        blackJack.dealer.hand.hand.add(new Card(6, 2));
        blackJack.determineWinner(player1);
        assert (player1.hand.win && !player1.hand.tie);
    }

    @Test
    public void testLoser1() {
        player1.hand.add(new Card(10, 1));
        player1.hand.add(new Card(7, 2));
        blackJack.dealer.hand.add(new Card(9, 1));
        blackJack.dealer.hand.add(new Card(9, 2));
        blackJack.determineWinner(player1);
        assert (!player1.hand.win && !player1.hand.tie);

    }

    @Test
    public void testTie1() {
        player1.hand.add(new Card(1, 1));
        player1.hand.add(new Card(8, 2));
        blackJack.dealer.hand.add(new Card(10, 4));
        blackJack.dealer.hand.add(new Card(9, 3));
        blackJack.determineWinner(player1);
        assert (!player1.hand.win && player1.hand.tie);

    }

    @Test
    public void testBust1() {
        player1.hand.add(new Card(10, 1));
        player1.hand.add(new Card(8, 2));
        player1.hand.add(new Card(8, 4));
        blackJack.dealer.hand.add(new Card(10, 4));
        blackJack.dealer.hand.add(new Card(9, 3));
        blackJack.determineWinner(player1);
        assert (!player1.hand.win && !player1.hand.tie);

    }


}
