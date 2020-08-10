package thread.capter04;

public class Test5 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(()->{
            for(int i=0;i<5000;i++){
                room.increment();
            }
        },"t1");
        Thread t2 = new Thread(()->{
            for(int i=0;i<5000;i++){
                room.decrement();
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(room.getCount());
    }
    private static class Room{
        private int count;

        /**
         * 自增操作
         */
        public void increment(){
            synchronized (this){
                count++;
            }
        }

        /**
         * 自减操作
         */
        public void decrement(){
            synchronized (this){
                count--;
            }
        }

        public int getCount(){
            synchronized (this){
                return count;
            }
        }

    }
}

