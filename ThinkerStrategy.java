package CardGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class ThinkerStrategy implements Strategy {
    Random random = new Random();
    Hand discardStack = new Hand();
    @Override
    public boolean cheat(Bid b, Hand h) {
        boolean callBS = false;
        for(Card card : h.getHand()){
            if(card.getRank()!=b.getRank() || Card.rankArray[card.getRankOrdinal()] != b.getRank())
            {
                callBS = true;// has to cheat
            }
        }
        if(!callBS){
            if(random.nextInt(6)==1){
                callBS=true;
                //sometimes cheat will be called even if it is not needed to
            }
        }
        return callBS;
    }
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) throws InvalidIndex {
        Hand handToPlay = new Hand();
        h = Hand.sortHand(h); // sort into ascending order
        if (cheat) {
            for (int k = 0; k < random.nextInt(4 - 1) + 1; k++) {
                //goes through a random amount of times (between 1 and 4)
                int ran = random.nextInt(4);
                if (ran == 1) { // 1in4 chance of picking the lower card route
                    Card randomCard = h.removeFromIndex(random.nextInt(h.getHand().size() / 2));
                    //bound for random integer selection is at the half way point so lower integers are produced
                    handToPlay.addCard(randomCard);
                    discardStack.addCard(randomCard);
                } else { //3in4 chance of following the higher card route
                    Card randomCard = h.removeFromIndex(random.nextInt(
                            h.getHand().size() - h.getHand().size() / 3) + h.getHand().size() / 3);
                /*i skew the distribution of the function with
                "rand.nextInt(max - min ) + min;" to get results in a certain range*/
                    handToPlay.addCard(randomCard);
                    discardStack.addCard(randomCard);
                }
            }
            return new Bid(handToPlay, b.getRank());
        }
        else {//if we're not cheating a random number is played 5 times
            int ran = random.nextInt(5);
            if (ran == 1) {
                int randomRank = random.nextInt(13);
                Card randomCard = null;
                do {
                    for (int k = 0; k < h.getHand().size(); k++) {
                        if (h.getHand().get(k).getRankOrdinal() == randomRank) {
                            handToPlay.addCard(h.getHand().get(k));
                            randomCard = h.getHand().get(k);
                        }
                    }
                    randomRank += 1; //if nothing is found at this rank, go higher
                } while (randomCard == null);
                for (int g = 0; g < h.getHand().size(); g++) {
                    if (h.getHand().get(g).getRank() == randomCard.getRank()) {
                        //search thru the hand to look for any cards of equal rank
                        handToPlay.addCard(h.getHand().get(g));
                        discardStack.addCard(h.getHand().get(g));
                    }
                }
            } else {
                /* If not cheating, always play the maximum number of cards possible of the lowest
                rank possible. This may be possible at the rank at which the prev card was bid,
                or the rank above. */
                Card.Rank sameRank = b.getRank();
                Card.Rank aboveRank = Card.rankArray[b.getRank().ordinal()];
                Hand hand0 = new Hand();
                Hand hand1 = new Hand();
                for (int a = 0; a < h.getHand().size(); a++) {
                    System.out.println(a);
                    if (h.getHand().get(a).getRank() == sameRank) {
                        hand0.addCard(h.getHand().get(a));
                    } else if (h.getHand().get(a).getRank() == aboveRank) {
                        hand1.addCard(h.getHand().get(a));
                    }
                }
                /* if the hand of rank the same as the previous bid is larger than the hand
                 * of rank above the prev bid is larger, it will be played. If vice versa,
                 * the hand of rank above will be played*/
                if (hand0.getHand().size() >= hand1.getHand().size()) {
                    handToPlay = hand0;
                } else {
                    handToPlay = hand1;
                }
            }
            return new Bid(handToPlay, handToPlay.getHand().get(0).getRank());
        }
    }

    @Override
    public boolean callCheat(Hand h, Bid b) {
    int countOfRankInHand =0;
    int countOfBidRank=0;
        for(int i=0;i<h.getHand().size();i++) {
            if (h.getHand().get(i).getRank() == b.getRank()) {
                countOfRankInHand += 1;
            }
        }
        int d=discardStack.getHand().size();
        List<Card> lastBids = new ArrayList<Card>();
        for (int i =d-10;i<d;i++){
            lastBids.add(discardStack.getHand().get(i));
        }//it's reasonable for a player to remember the last 10 cards that were bid. that isn't discard stack tho
        for(int i=0;i<lastBids.size();i++)
            if(lastBids.get(i).getRank()== b.getRank()){countOfBidRank+=1;}
        if((countOfBidRank+countOfRankInHand)>4){
        return true;
    }else{return false;}
    }
}
