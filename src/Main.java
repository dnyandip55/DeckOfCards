import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Enum for Card Suit
enum Suit {
    SPADE, CLUB, HEART, DIAMOND
}

// Enum for Card Rank
enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

// Card class representing a single card
class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

// Comparator to compare cards based on color, suit, and value
class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        // Compare by color
        int colorCompare = getColorValue(card1.getSuit()) - getColorValue(card2.getSuit());
        if (colorCompare != 0) {
            return colorCompare;
        }
        // If same color, compare by suit
        int suitCompare = card1.getSuit().ordinal() - card2.getSuit().ordinal();
        if (suitCompare != 0) {
            return suitCompare;
        }
        // If same suit, compare by rank
        return card1.getRank().ordinal() - card2.getRank().ordinal();
    }

    // Helper method to assign color values
    private int getColorValue(Suit suit) {
        return (suit == Suit.HEART || suit == Suit.DIAMOND) ? 0 : 1;
    }
}

// Deck class representing a deck of cards
class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        // Initialize deck with 52 cards
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    // Shuffle the deck using Fisher-Yates algorithm
    public void shuffle() {
        Random rnd = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Card temp = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, temp);
        }
    }

    // Draw a specific number of cards from the deck
    public List<Card> draw(int numCards) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < numCards && !cards.isEmpty(); i++) {
            drawnCards.add(cards.remove(0));
        }
        return drawnCards;
    }

    // Check the size of the deck
    public int size() {
        return cards.size();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get input from the user for the number of cards to draw
        System.out.print("Enter the number of cards to draw: ");
        int numCardsToDraw = scanner.nextInt();

        // Create a deck
        Deck deck = new Deck();

        // Shuffle the deck (optional)
        deck.shuffle();

        // Draw the specified number of cards
        List<Card> drawnCards = deck.draw(numCardsToDraw);

        // Sort the drawn cards
        Collections.sort(drawnCards, new CardComparator());

        // Print the sorted drawn cards
        for (Card card : drawnCards) {
            System.out.println(card);
        }

        // Close the scanner to prevent resource leak
        scanner.close();
    }
}
