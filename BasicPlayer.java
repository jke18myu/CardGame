package CardGame;
public class BasicPlayer implements Player {
    private Hand myHand;
    private Strategy playerStrategy;
    private CardGame cardGame;
    public BasicPlayer(Strategy strat, CardGame gameType){
        this.myHand = new Hand();
        this.cardGame = gameType;
        this.playerStrategy=strat;
    }
    @Override
    public boolean callCheat(Bid bid) {
        return playerStrategy.callCheat(this.myHand, bid);
    }
    @Override
    public void addCard(Card card) {
        this.myHand.addCard(card);
    }
    @Override
    public void addHand(Hand hand) {
        this.myHand.addHand(hand);
    }
    @Override
    public int cardsLeft() {
        return this.myHand.getHand().size();
    }
    @Override
    public Bid playHand(Bid lastBid) throws InvalidIndex {
        boolean cheat = playerStrategy.cheat(lastBid, this. myHand);
        return playerStrategy.chooseBid(lastBid, this.myHand, cheat);
    }

    @Override
    public void setStrategy(Strategy strat) {
        this.playerStrategy=strat;
    }

    @Override
    public void setGame(CardGame gameType) {
        this.cardGame=gameType;
    }
}
