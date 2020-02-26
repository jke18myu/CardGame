package CardGame;
import java.io.Serializable;
import java.util.*;
public class Deck implements Serializable {
    static final long serialVersionUID = 100246349;
    private Card[] deckVar;
    public Card[] getDeck() {
        return this.deckVar;
    }
    private int deckTopPointer;
    public int size() {
        return deckTopPointer;
    };
    public Deck() { // deck constructor
        deckVar = new Card[53];
        Card.cardInstantiations(deckVar);
        int index = 0;
        //write this method in Card
        //pass in a 52 array
        deckTopPointer = 0;
    }
    public Card deal() {
        Card ret = deckVar[deckTopPointer]; // stores the Card object to be returned
        deckVar[deckTopPointer] = null; // removes the card object from the deck
        if (deckTopPointer < 52) {
            deckTopPointer = deckTopPointer + 1;
        } else {
            System.out.println("Deck empty");
            return null;
        }
        return ret; // returns the card object from the top of the deck
    }
    public void shuffle() {
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            try {
                deckTemp.add(deckVar[i]);
            } catch (NullPointerException ignored) {
            }
        }
        Collections.shuffle(deckTemp);
        for (int i = 0; i < 52; i++) {
            this.deckVar[i] = deckTemp.get(0);
        }
    }
    public final void newDeck() {
        new Deck();
    }
    public DeckIterator iterator() {
        return new DeckIterator(this.getDeck());
    }

    static class DeckIterator implements Iterator<Card> {
        int positionIndex=0;
        List<Card> deckData = new ArrayList<Card>();
        public DeckIterator(Card[] d){
            //deckData.addAll(Arrays.asList(d));
            for (int r=0;r<d.length;r++){
                deckData.add(d[r]);
                System.out.println("iteration: "+r+ "is "+deckData.get(r));
            }
            this.positionIndex = 0;
        }
        @Override
        public void remove() {
            deckData.remove(positionIndex);
        }
        @Override
        public boolean hasNext() {
            if (deckData.size() < positionIndex + 1) {
                //checks if the next index actually exists
                return false;
            } else {
                return true; }
        }
        @Override
        public Card next() {
            Card retVal= deckData.get(positionIndex);
            positionIndex=positionIndex+1;
            return retVal;
        }
    }
    static class OddEvenIterator implements Iterator<Card> {
        int positionIndex=0;
        List<Card> deckData = new ArrayList<Card>();
        public OddEvenIterator(Card[] d){
            //deckData.addAll(Arrays.asList(d));
            for (int r=0;r<d.length;r++){
                deckData.add(d[r]);
                //System.out.println("iteration: "+r+ "is "+deckData.get(r));
            }
            this.positionIndex = 0;
        }
        @Override
        public boolean hasNext() {
            if (deckData.size() < positionIndex + 1) {
                //checks if the next index actually exists
                return false;
            } else {
                return true; }
        }

        @Override
        public Card next() {
            return null;
        }
    }}
