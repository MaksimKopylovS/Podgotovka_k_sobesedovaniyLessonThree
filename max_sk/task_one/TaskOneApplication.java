package max_sk.task_one;

public class TaskOneApplication {
    public static void main(String[] args) throws InterruptedException {
        PingPong pingPong = new PingPong();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pingPong.printPing();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pingPong.printPong();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}


class PingPong {

    private final String PING ="ping";
    private final String PONG ="pong";
    private String str = PING;
    private Object stop = new Object();

    public void printPing() throws InterruptedException {
        synchronized (stop) {
            while (true) {
                while (!str.equals(PING)) {
                    stop.wait();
                }
                Thread.sleep(1000);
                System.out.println(str);
                str = PONG;
                stop.notify();
            }
        }
    }

    public void printPong() throws InterruptedException {
       synchronized (stop) {
           while (true) {
               while (!str.equals(PONG)) {
                   stop.wait();
               }
               Thread.sleep(1000);
               System.out.println(str);
               str = PING;
               stop.notify();
           }
       }
    }
}
