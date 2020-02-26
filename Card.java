package CardGame;
import java.util.Scanner;
import java.io.*;
import java.util.*;
public class Card implements Comparable<Card>, Serializable
{
    static final long serialVersionUID = 100246359;
    @Override
    public int compareTo(Card card) {
        if (this.getRank().ordinal() > card.getRank().ordinal() ){
            return -1;
        }
        else{
            return 1;
        }
    }
    enum Rank {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10),
        ACE(11);
        private final int value;
        Rank(int cardVal) { //setting the values for each rank of card
            this.value = cardVal;
        }
        public int getValue() { //retrieves the value for a card object it's called to
            return this.value;
        }
        public Rank getPrevious() {
            int rankIndex = this.ordinal();
            return rankArray[rankIndex - 1];
        }
    }
    private Rank rankVar;
    public Rank getRank() {//accessor method for rank enum
        return this.rankVar;
    }
    public int getRankOrdinal() {
        return this.rankVar.ordinal();
    }

    public static Rank[] rankArray =
            {Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN,
             Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING,
             Rank.ACE};
    enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES; // defining the possible enum values

        public static Suit getSuit() { /*nested static method for generating
            random suits */
            int rand = (int) (Math.random()*3); //between 0 and 3
            return Suit.values()[rand];
        }
    }
    private Suit suitVar;
    public Suit getSuit() {//accessor method for rank enum
        return this.suitVar;
    }
    public int getSuitOrdinal(){
        return this.suitVar.ordinal();
    }
    public static Suit[] suitArray =
            {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};
    public Card(Rank inpRank, Suit inpSuit) {
        this.rankVar = inpRank;
        this.suitVar = inpSuit;
    }
    public Card(){}
    public static int differenceValue(Card card1, Card card2) {
        int indexCard1 = card1.getRank().getValue();
        int indexCard2 = card2.getRank().getValue();
        return Math.abs(indexCard1-indexCard2);
    }
    public static Card[] cardInstantiations(Card[] c){
        int index = 0;
        for(int s =0;s<4;s++){
            for(int r=0;r<13;r++){
                c[index]=new Card(rankArray[r], suitArray[s]);
                //System.out.println("index: "+index+" loop val:" + "card: "+c[index].toString());
                index=index+1;
                //method to iterate over enums with advanced type of for loop was giving errors
            }
        }
        return c;
    }
    @Override
    public String toString()
    {
        return "This card is of Suit: "+getSuit()
                +" and is of Rank: " + getRank();
    }
    public static void selectTest(){
        Scanner scanObj = new Scanner(System.in);
        System.out.println("Please enter the Rank of the input card in all caps");
        String inpR = scanObj.nextLine();
        System.out.println("Please enter the Suit of the input card in all caps");
        String inpS = scanObj.nextLine();
        Card inpCard = new Card(null, null);
        try{
            inpCard = new Card(Rank.valueOf(inpR),Suit.valueOf(inpS));}
        catch(IllegalArgumentException e) {
            System.out.println("Enter in all caps! That input string wasn't right. "
                    +"I've run the method again for you:");
            selectTest();
        }
        Card card0 = new Card(Rank.KING, Suit.HEARTS);
        Card card1 = new Card(Rank.TEN, Suit.CLUBS);
        Card card2 = new Card(Rank.SIX, Suit.DIAMONDS);
        List<Card> initialisedCards = new ArrayList<>();
        initialisedCards.add(card0);
        initialisedCards.add(card1);
        initialisedCards.add(card2);
        Comparator<Card> compRlambda =
                (c0, c1) -> {
                    return Integer.compare(c0.getRankOrdinal(), c1.getRankOrdinal());
                };
        Comparator<Card> compSlambda =
                (c0, c1) -> {
                    return Integer.compare(c0.getSuitOrdinal(), c1.getSuitOrdinal());
                };
        for (int i=0;i<initialisedCards.size();i++) {
            System.out.println("");
            if (compRlambda.compare(inpCard, initialisedCards.get(i) ) > 0){
                System.out.println("input card is greater than Card " +i+" of "
                        + "the initialised cards.");
            } else if(compRlambda.compare(inpCard, initialisedCards.get(i) ) == 0){
                if (compSlambda.compare(inpCard, initialisedCards.get(i)) <0){
                    System.out.println("input card is greater than Card " +i+
                            " of the initialised cards.");
                } else{System.out.println("The "+i+"th initialised card is greater " +
                        "than the input card.");}
            }else{System.out.println("The "+i+"th initialised card is greater than " +
                    "the input card.");}
        }
        }
    public static void main(String[] args) {
        //selectTest();
        //serialising Cards
        Card testCard1 = new Card(Rank.QUEEN, Suit.DIAMONDS);
        Card testCard2 = new Card(Rank.ACE, Suit.CLUBS);
        String filename = "cards.ser";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            //objects are saved to an object output stream with the method writeObject
            out.writeObject(testCard1);
            out.writeObject(testCard2);
            out.close();
        } catch (Exception e) { //catch multiple types of exceptions
            e.printStackTrace();
        }
    }

static class CompareAscending implements Comparator<Card> {
    public CompareAscending(){};
    @Override
    public int compare(Card c0, Card c1) {
        if (c0.getRankOrdinal() < c1.getRankOrdinal()) {
            return -1;
        } else if (c0.getRankOrdinal() > c1.getRankOrdinal()) {
            return 1;
        } else return 0;
    }
}
static class CompareSuit implements Comparator<Card> {
    public CompareSuit(){};
    @Override
    public int compare(Card c0, Card c1) {
        if (c0.getSuitOrdinal() < c1.getSuitOrdinal()) {
            return -1;
        } else if (c0.getSuitOrdinal() > c1.getSuitOrdinal()) {
            return 1;
        } else return 0;
    }
}}