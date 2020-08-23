public class Card {
    int value;
    boolean red;
    boolean black;
    int suit;
    boolean face = false;

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
        if (value > 10)
            face = true;
        if (suit == 1 || suit == 2) {
            black = true;
            red = false;
        } else {
            black = false;
            red = true;
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isRed() {
        return red;
    }

    public boolean isBlack() {
        return black;
    }

    public int getSuit() {
        return suit;
    }

    public boolean isFace() {
        return face;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                red == card.red &&
                black == card.black &&
                suit == card.suit &&
                face == card.face;
    }

    @Override
    public String toString() {
        String v = "";
        if (value == 1) {
            v = "Ace";
        } else if (value == 2) {
            v = "Two";
        } else if (value == 3) {
            v = "Three";
        } else if (value == 4) {
            v = "Four";
        } else if (value == 5) {
            v = "Five";
        } else if (value == 6) {
            v = "Six";
        } else if (value == 7) {
            v = "Seven";
        } else if (value == 8) {
            v = "Eight";
        } else if (value == 9) {
            v = "Nine";
        } else if (value == 10) {
            v = "Ten";
        } else if (value == 11) {
            v = "Jack";
        } else if (value == 12) {
            v = "Queen";
        } else if (value == 13) {
            v = "King";
        }

        String s = "";
        if (suit == 1)
            s = "Spades";
        if (suit == 2)
            s = "Clubs";
        if (suit == 3)
            s = "Hearts";
        if (suit == 4)
            s = "Diamonds";

        return v + " of " + s;
    }
}


