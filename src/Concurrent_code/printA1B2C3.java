package Concurrent_code;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class printA1B2C3 {
    public static void main(String[] args) {
//        System.out.println(Thread.currentThread().getId());
        Thread th1 = new Thread(new printThread());
        Thread th2 = new Thread(new printThread());
        Thread th3 = new Thread(new printThread());
        th1.start();
        th2.start();
        th3.start();
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

}

class printThread implements Runnable{
    int count = 1;
    @Override
    public void run() {
        synchronized (printA1B2C3.class){
            while (count <= 50){
//                System.out.println(Thread.currentThread().getName());
                if (Thread.currentThread().getName().equals("Thread-1")) System.out.println("A");
                else if (Thread.currentThread().getName().equals("Thread-2")) System.out.println("BB");
                else if (Thread.currentThread().getName().equals("Thread-0")) System.out.println("CCC");
                count++;
            }
        }
    }
}


class Resource{

    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();  //该方法每次都会生成一个新的Condition对象
    private Condition c2 = lock.newCondition();
    private  Condition c3 = lock.newCondition();



}

