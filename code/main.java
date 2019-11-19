package src.test01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) throws InterruptedException{
        thread th = new thread(0);
        int num = th.getAllName().size();
        try{
            ExecutorService executor = Executors.newFixedThreadPool(num);
            thread[] thread = new thread[num];
            System.out.println("| No. |      login id      |   Number of respositories    | Number of Follower | Number of Following | Number of Star |");
            for(int i =0;i<thread.length;i++){
                thread[i] = new thread(i);
                executor.execute(thread[i]);
                Thread.sleep(10000);
            }
            executor.shutdown();
            while(!executor.isTerminated()){

            }
        }catch(Exception e){
            e.wait();

        }
    }
}
