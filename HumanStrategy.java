package CardGame;
import java.util.Scanner;
public class HumanStrategy implements Strategy {
    public void displayHand(Hand h){
        h=Hand.sortHand(h);
        //I first sort the hand so that the user can view a clean hand
        System.out.println("Your hand is comprised of: ");
        if(h.getHand().size() <10){
            for (int i =0;i<h.getHand().size();i++) {
                System.out.print("\n");
                System.out.print("Card no."+i +": "+h.getHand().get(i)+"   ");
            }
        } else{
        for (int i =0;i<10;i++) {
            System.out.print("\n");
            System.out.print("Card no."+i +": "+h.getHand().get(i)+"   ");
        }
        if (h.getHand().size()>10 && h.getHand().size()<20){
            for (int i=10; i<h.getHand().size();i++)
            {
                System.out.print("\n");
                System.out.print("Card no."+i +": "+h.getHand().get(i)+"   ");
            }} // printing across a couple of lines for large hands to try to keep it neat
        if (h.getHand().size()>10) {
            int iterationLim;
            if(h.getHand().size()<20) {
                iterationLim = h.getHand().size();
            } else{iterationLim=20;}
            for (int i = 10; i < iterationLim; i++) {
                System.out.print("\n");
                System.out.print("Card no." + i + ": " + h.getHand().get(i) + "   ");
            }
        }
        if(h.getHand().size()>20){
            int iterationLim;
            if(h.getHand().size()<30) {
                iterationLim = h.getHand().size();
            } else{iterationLim=30;}
            for (int i=20; i<iterationLim;i++)
            {
                System.out.print("\n");
                System.out.print("Card no."+i +": "+h.getHand().get(i)+"   ");
            }}}
        /*This system breaks if the player has a hand larger than 30 cards, but for my
        sake and theirs lets just hope that doesn't occur*/
    }
    @Override
    public boolean cheat(Bid b, Hand h) {
        String usrInp = " ";
        Scanner inp = new Scanner(System.in);
        System.out.println("the land bid played: "+ b.toString());
        displayHand(h);
        System.out.println("type 'YES' or 'NO' (in caps) to inform me if you wish to cheat or not. ");
        usrInp = inp.nextLine();
        if(!usrInp.equals("YES") || !usrInp.equals("NO")){
            System.out.println("make sure you've entered a valid response");
            cheat(b, h);
        }
        if(usrInp=="YES"){ return true; }
        else{return false;}
    }
    @Override
    public Bid chooseBid(Bid b, Hand h, boolean cheat) throws InvalidIndex {
        displayHand(h);
        System.out.println("and the previous bid was: " + b.toString());
        Hand handToPlay=new Hand();
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter the index of the cards you would like to bid one by one." +
                "when all of your indexes are input, please input a non " +
                "integer character to show that you are finished");
        System.out.println("You can't input more than 4 cards");
        if(cheat)
        {   for(int i=0;i<4;i++){
                try{
                    handToPlay.addCard(h.getHand().get(Integer.parseInt(inp.nextLine())));
                } catch(NumberFormatException e){ // the exception will be a wrong  exception
                    //exit for loop
                    i = 5; }
            }
            Card.Rank bidRank = Card.Rank.TEN;
            //initialising the variable to store which rank the player wants to bid at.
            try {
                System.out.println("what rank do you wish to bid these at");
                bidRank = Card.Rank.valueOf(inp.nextLine());
            } catch(IllegalArgumentException e){
                System.out.println("you failed to input a rank in the correct format, you'll have to begin again");
                chooseBid(b, h, true);
            }
            return new Bid(handToPlay, bidRank);
        } else{
            for(int i=0;i<4;i++){
                try{
                    handToPlay.addCard(h.getHand().get(Integer.parseInt(inp.nextLine())));
                }catch(NumberFormatException e){ // the exception will be a wrong  exception
                    i = 5; //exit for loop
                }
            }
            return new Bid(handToPlay, handToPlay.getHand().get(0).getRank());
        }
    }
    @Override
    public boolean callCheat(Hand h, Bid b) {
        Scanner inp = new Scanner(System.in);
        System.out.println("Do you wish to call cheat?" +
                "\nPlease enter just 'YES' or 'NO' in all caps");
        if(inp.nextLine()!="YES" || inp.nextLine()!="NO"){
            System.out.println("That input was invalid try again");
            callCheat(h, b);
        }
        if(inp.nextLine()=="YES"){
            return true;
        }
        else{
            return false;}
    }
}
