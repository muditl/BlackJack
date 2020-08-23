import java.util.HashSet;

public class Deck {
    HashSet<Card> Deck = new HashSet<>();

    public Deck() {
        for (int i = 1; i <= 13; i++) {
            for (int j = 1; j <= 4; j++) {
                Deck.add(new Card(i, j));
            }
        }
    }

}
