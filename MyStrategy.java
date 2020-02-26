package CardGame;

public class MyStrategy implements Strategy{
    @Override
    public boolean cheat(Bid b, Hand h) {
        return false;
    }

    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) throws InvalidIndex {
        return null;
    }

    @Override
    public boolean callCheat(Hand h, Bid b) {
        return false;
    }
}
