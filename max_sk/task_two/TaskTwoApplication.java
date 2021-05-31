package max_sk.task_two;

import java.util.concurrent.locks.ReentrantLock;

public class TaskTwoApplication {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Count count = new Count(0);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new CountThread(count, lock));

            t.setName("Thread " + i);
            System.out.println(t.getName());
            t.start();
        }
    }
}

class Count {
    private static int count;

    public Count(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void countInc() {
        count++;
    }
}

class CountThread implements Runnable {
    private Count count;
    private ReentrantLock locker;
    private boolean stop = true;

    public CountThread(Count count, ReentrantLock lock) {
        this.count = count;
        locker = lock;

    }

    @Override
    public void run() {

        while (stop) {
            locker.lock();
            if (count.getCount() >= 20000){
                locker.unlock();
                break;
            }
            count.countInc();
            System.out.println(count.getCount() + " " + Thread.currentThread());
            locker.unlock();


        }
    }
}
