package CardGame;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
class InvalidIndex extends Exception {
    public InvalidIndex(String errorMessage) {
        super(errorMessage);
    }
}
public class Hand implements Serializable {
    static final long serialVersionUID = 100246369;
    private ArrayList<Card> collection = new ArrayList<>();
    private int[] cardCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public void keepCount(Card card, int i){
        cardCount[card.getRankOrdinal()] += i;
    }
    public int[] getCardCount(){
        return this.cardCount;
    }
    public ArrayList<Card> getHand(){
        return this.collection;
    }
    public Hand(){ // default constructor
        this.collection = new ArrayList<>();
    }
    public Hand(Card[] cardList){
// constructor that takes an array of cards and adds them to the hand
        this.collection.addAll(Arrays.asList(cardList));
        for (int i =0;i<collection.size();i++){
            this.keepCount(cardList[i], 1);}
    }
    public Hand(Hand hand){
// constructor that takes a different hand and adds all the cards to this hand.
        this.collection.addAll(hand.getHand());
        for (int i =0;i<hand.getHand().size();i++){
            this.keepCount(hand.getHand().get(i), 1);}
    }
    public static Hand sortHand(Hand h){
        ArrayList<Card> list = new ArrayList<Card>();
        list = h.getHand();
        Collections.sort(list);
        Card[] ret = new Card[list.size()];
        for(int i=0;i<list.size();i++){
            ret[i] = list.get(i);
        }
        return new Hand(ret);
    }
    public void addCard(Card card){
// Code identical to constructors although this time collection already stores cards
        this.collection.add(card);
        this.keepCount(card, 1);
    }
    public void addCollection(ArrayList<Card> inpList){
        this.collection.addAll(inpList);
        for (int i =0;i<collection.size();i++){
            this.keepCount(inpList.get(i), 1);
        }
    }
    public void addHand(Hand hand){
        this.collection.addAll(hand.getHand());
        for (int i =0;i<hand.getHand().size();i++){
            this.keepCount(hand.getHand().get(i), 1);
        }
    }
    public boolean removeCard(Card card){
        if(!this.collection.contains(card)){return false;}
        else{
            collection.remove(card);
            return true;}
    }
    public boolean removeFromHand(Hand hand){
//removes all of the cards in the current hand that exist in the passed in hand.
        int o =0;
        for(int i =1;i<hand.getHand().size();i++){
            if(this.collection.contains(hand.getHand().get(i))){
                collection.remove(hand.getHand().get(i));
                o+=1;} /*if the card from the passed in hand was found in the
                current hand a variable is incremented*/
        }
        return o == hand.getHand().size(); /* if the increment variable is equal
        to the length of the list of cards in the hand then every card from the
        passed in hand was found and removed. */
    }
    public Card removeFromIndex(int index) throws InvalidIndex {
/* removes a card from a specific location in the hand and returns it, or throws
exception if position passed is invalid*/
        if(index>(this.collection.size())){
            throw new InvalidIndex("Index "+index+" is an invalid position");
        }else{
            Card retVal = this.collection.get(index);
            this.collection.remove(index);
            return retVal;
        }
    }
}