/* This program is intended to create eBay objects and allow for bids to be placed through the use of threads
* There are three classes, test1part2 and forSale, bidding*/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*test1part2 holds the main method*/
public class test1part2 {
    public static void main(String[] args){
        //creating the thread pool
        ExecutorService executor= Executors.newFixedThreadPool(6);
        //creating the forSale object
        forSale painting = new forSale("Dutch Masters Oil Painting", 5000.0, 60000,18490,17050,4827);

        //painting.MyBid(4827,5283,8000,18481);
        //executing threads
        executor.execute(new bidding(painting,4827,5283,4000,18481));
        executor.execute(new bidding(painting,4827,4681,15000, 18482));
        executor.execute(new bidding(painting,4827,5283,14500,18483));
        executor.execute(new bidding(painting,4827,5283,17500,18485));
        executor.execute(new bidding(painting,4827,4681,25000,18489));
        executor.execute(new bidding(painting,4827,5283,32000,18495));
        //shutting down the executor
        executor.shutdown();
    }//end of main

    /*class forSale holds the constructor for the eBay objects, and has function MyBid which
    * allows for bids to be placed on the eBay object*/
    public static class forSale{
        //creating variables
        private String ItemDes; //this is a character string to describe the item
        private double Buyitnow; // This is the price to buy the item now
        private double Currentbid;//this is the current bid on the object
        private double Minbid;// this is the minimum opening bid on the object
        private double Newbid;// this is the new bid submitted for the object under consideration by the eBay software
        private int  timedone; // this is the time the auction will close.
        // eBay measures  all bid closure times from 0 minutes beginning midnight Dec 31 for the entire year.
        private int ctime;// current time.
        private int itemno;//this is the unique Item number given to an item sold on eBay
        private int bidno;//this is a unique bidder number for the bidder holding the current high bid on an item
        private int sold;// item sold indicator 0=not sold, 1=sold

        //creating a lock
        private static Lock lock = new ReentrantLock();

        //creating the constructor
        public forSale(String itemdes1, double minbid1, int buyitnow1, int timedone1, int ctime1, int itemno1){
            //setting variables equal to their counterparts
            ItemDes = itemdes1;
            Minbid = minbid1;
            Buyitnow = buyitnow1;
            timedone = timedone1;
            ctime = ctime1;
            itemno = itemno1;

            Currentbid = 0; //item has not yet been bid on
            sold = 0;//item has not yet been sold
        }//end of constructor

        //creating function MyBid
        public void MyBid(int itemnoBid, int bidnoBid, double bidamtBid, int ctimeBid){
            lock.lock(); //locking so that only one user may place a bid at a time
            //checking to see if the item is available for purchase
            if(sold==1){//if item is not available for purchase, alert bidder
                System.out.println("Sorry user "+bidnoBid+", item "+itemnoBid+" is no longer avaiable for purchase.");
                System.out.flush();
            }else{//item is available for purchase
                //creating condition for characteristic #2
                if(bidamtBid>=Buyitnow){
                    System.out.println("Great job "+bidnoBid+", you just bought "+itemnoBid+", "+ItemDes+" for "+bidamtBid+" amount!");
                    System.out.flush();
                    sold = 1; //setting sold to 1 so that it is unavailable for purchase
                }
                //creating a condition for characteristic #3
                if((bidamtBid<=Currentbid)&&(bidamtBid<=Minbid)){
                    System.out.println("Sorry "+bidnoBid+", this is not enough for "+itemnoBid+", "+ItemDes);
                    System.out.flush();
                }
                //creating a condition for characteristic #4
                if(ctimeBid>timedone){
                    System.out.println("Sorry "+bidnoBid+", you have run out of time to purchase "+itemnoBid+", "+ItemDes);
                    System.out.flush();
                }
                //creating a condition for characteristic #5
                if((bidamtBid>Currentbid)&&(bidamtBid>=Minbid)){
                    Currentbid = bidamtBid;
                    bidno = bidnoBid;
                    ctime = ctimeBid;
                    System.out.println("Congratulations "+bidnoBid+", you are currently the highest bidder for "+itemnoBid+
                            ", "+ItemDes+", but keep watching, good stuff goes fast on eBay.");
                    System.out.flush();
                }
            }//end of else
            lock.unlock();//unlocking
        }//end of MyBid
    }//end of forSale

    /*class bidding allows implements runnable and runs using function MyBid*/
    public static class bidding implements Runnable{
        //this is the eBay item being bid upon
        forSale item;

        //creating variables for the bidding information
        int itemnoBidding;
        int bidnoBidding;
        double bidamtBidding;
        int ctimeBidding;

        //creating constructor
        public bidding(forSale x, int itemnoBid, int bidnoBid, double bidamtBid, int ctimeBid){
            item = x;
            itemnoBidding = itemnoBid;
            bidnoBidding = bidnoBid;
            bidamtBidding = bidamtBid;
            ctimeBidding = ctimeBid;
        }
        public void run(){
            item.MyBid(itemnoBidding,bidnoBidding,bidamtBidding,ctimeBidding);
        }
    }
}//end of test1part2
