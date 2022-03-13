/** 多个线程按照顺序打印1到100
 *
 */

/** 第一种方式，设置一个全局变量，每个线程若想在这个线程上加1，就必须先取得锁。
 *  锁有多种方式：对象锁，java自带的lock类
 *
 *  //使用synchronized关键字，并把一个静态变量作为锁，在一个线程里调用该对象的wait会阻塞该锁
 *
 */
package Concurrent_code;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class print1To100 {

    static String s = new String("lpc");
    static int count = 1;

    public static void main(String[] args){
        Thread th1 = new Thread(new print_class());
        Thread th2 = new Thread(new print_class());

        th1.start();
        th2.start();

        try {
            Thread.sleep(3000); //等其他两个线程执行完了，再来执行主线程，唤醒队列中的等待线程；如果不让主线程休眠，那么主线程很快执行完，等待的线程就没有线程能去唤醒
            synchronized (s){
                s.notifyAll();
            }
        }catch (InterruptedException e){
            System.out.println(e.getStackTrace());
        }

    }

}

class print_class implements Runnable{
    @Override
    public void run() {
        synchronized (print1To100.s){
            while (print1To100.count <= 100){ //小于100就打印
                System.out.println(Thread.currentThread().getName() + " " + print1To100.count);
                print1To100.count++;
                try {
                    print1To100.s.notifyAll();
                    print1To100.s.wait();
                    System.out.println(Thread.currentThread().getName() + "从wait方法退出了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



//使用ReentrantLock
class LockTest{
    static ReentrantLock lock = new ReentrantLock();
    static Condition con = lock.newCondition();
    static int count = 1;

    static class PC implements Runnable{
        @Override
        public void run() {
            lock.lock(); //若有线程已经获取该对象的锁，那么后面的线程就会进入lock的同步队列；
            try {
                while (LockTest.count <= 100){ //小于100就打印
//                    if (LockTest.count % 2 ==0){
                        System.out.println(Thread.currentThread().getName() + " " + LockTest.count);
//                    }
                    LockTest.count++;
                    con.signalAll(); //调用后会唤醒等待队列中的线程移动到同步队列中去；
                    con.await(); //获得锁的线程调用await后会释放当前获得的锁，并进入到condition的等待队列；直到被其他线程唤醒到同步队列后，有机会去竞争锁，获得lock之后才会从await方法退出；
//                    lock.notifyAll();
//                    System.out.println("wake test");
//                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }


    public static void main(String[] args) {
        Thread th1 = new Thread(new PC());
        Thread th2 = new Thread(new PC());

        th1.start();
        th2.start();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockTest.lock.lock();
        LockTest.con.signalAll();
        LockTest.lock.unlock();

    }


}