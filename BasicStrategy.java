package CardGame;
import java.util.Random;
public class BasicStrategy implements Strategy {
/* All the strategies should implement the Strategy interface or extend the
BasicStrategy */
    public BasicStrategy(){}
    @Override
    public boolean cheat(Bid b, Hand h) {
        boolean callBS = false;
        // Decision to call cheat by default is false.
        Card.Rank rank = b.getRank();
        for(Card card : h.getHand()){
            if(card.getRank()!=b.getRank() || Card.rankArray[card.getRankOrdinal()] != b.getRank())
            {callBS = true;}
        }
        return callBS;
    }
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) throws InvalidIndex {
        Hand handToPlay = new Hand();
        if(cheat)
/* If you need to cheat, play a single card selected randomly from your hand*/
        {   Random rand = new Random();
            Card randFromHand = h.removeFromIndex(rand.nextInt(h.getHand().size()));
            handToPlay.addCard(randFromHand);
            return new Bid(handToPlay, randFromHand.getRank());
        } else{
/* If not cheating, always play the maximum number of cards possible of the lowest
rank possible. This may be possible at the rank at which the prev card was bid,
or the rank above. */
            Card.Rank sameRank = b.getRank();
            Card.Rank aboveRank = Card.rankArray[b.getRank().ordinal()];
            Hand hand0 = new Hand();
            Hand hand1 = new Hand();
            for (int a=0;a<h.getHand().size();a++)
            {
                System.out.println(a);
                if(h.getHand().get(a).getRank()==sameRank){
                    hand0.addCard(h.getHand().get(a));
                }
                else if(h.getHand().get(a).getRank()==aboveRank) {
                    hand1.addCard(h.getHand().get(a));
                }
            }
/* if the hand of rank the same as the previous bid is larger than the hand
 * of rank above the prev bid is larger, it will be played. If vice versa,
 * the hand of rank above will be played*/
            if(hand0.getHand().size()>=hand1.getHand().size()) {
                handToPlay = hand0;
            }
            else{
                handToPlay=hand1;
            }
            return new Bid(handToPlay, handToPlay.getHand().get(0).getRank());
        }
    }
    @Override
    public boolean callCheat(Hand h, Bid b) {
/* Call another player a cheat only when certain they are cheating (based on
your own hand). */
        int[] inMyHand = h.getCardCount();
        int bidNum = b.getCount();
        if(h.getCardCount()[b.getRank().ordinal()] + bidNum >4 ) {
            return true;
        } else {
            return false;
        }
    }
}
