package thread.capter05;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j
public class ExerciseSell {

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<500;i++){
            sell();
        }
    }

    public static void sell() throws InterruptedException {
        //模拟2000个窗口，卖出3000张票
        TicketWindow ticketWindow = new TicketWindow(5000);
        List<Thread> threads = new ArrayList<>(2000);
        //记录实际卖出的票数
        List<Integer> sellCount = new Vector<>();
        for(int i=0;i<2000;i++){
            int amount = randomAmount();
            Thread t = new Thread(()->{
                int realAmount = ticketWindow.sell(amount);
                sellCount.add(realAmount);
            });
            threads.add(t);
            t.start();
        }
        //等待买票结束
        for(Thread t:threads){
            t.join();
        }
        log.error("还剩的票数：{}",ticketWindow.getCount());
        log.error("理论上卖出的票数：{}，实际上卖出的票数：{}",(2000-ticketWindow.getCount()),sellCount.stream().mapToInt(m->m).sum());
    }

    /**
     * Random是线程安全的
     */
    static Random random = new Random();

    /**
     * 获取随机数
     * @return
     */
    private static int randomAmount(){
        return random.nextInt(5)+1;
    }
}
class TicketWindow {

    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public synchronized int getCount(){
        return count;
    }

    public synchronized int sell(int amount){
        if(count >= amount){
            this.count = count-amount;
            return amount;
        }else{
            return 0;
        }
    }
}
