import java.lang.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test1part1 {
    public static void main(String[] args){

        //create the thread pool
        ExecutorService executor= Executors.newFixedThreadPool(2);
        Even even = new Even(7,30);
        Odd odd = new Odd(7,30);

        executor.execute(even);
        executor.execute(odd);
        executor.shutdown();

    }//end of main
}//end of test1part1

class Odd implements Runnable{
    private int beginning; //the start variable
    private int end; //the stop variable

    //creating the constructor
    public Odd(int start, int stop){
        beginning = start;
        end = stop;
    }//end of constructor

    //creating run
    public void run(){
        //implementing logic
        for(int i = beginning; i<=end; i++){
            if(i%2!=0){
                System.out.println(i);
            }
            //adding Thread.sleep in order to make Odds take longer to finish
            try{Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }//end of run
}//end of Odd

class Even implements Runnable{
    private int beginning; //the start variable
    private int end; //the stop variable

    //creating the constructor
    public Even(int start, int stop){
        beginning = start;
        end = stop;
    }//end of constructor

    //creating run
    public void run(){
        //implementing logic
        for(int i = beginning; i<=end; i++){
            if(i%2==0){System.out.println(i);}
            Thread.yield();
        }
    }//end of run
}//end of Even

class Consonants implements Runnable{
    private char beginning;
    private char end;

    //creating the constructor
    public Consonants(char start, char stop){
        beginning = start;
        end = stop;
    }//end of constructor

    //creating run
    public void run(){
        //for(){}
    }
}//end of Consonants