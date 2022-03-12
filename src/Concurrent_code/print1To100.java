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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class LockTest{
    final static ReentrantLock lock = new ReentrantLock();

    static class PC implements Runnable{
        @Override
        public void run() {
            lock.lock();
            try {
                while (print1To100.count <= 100){ //小于100就打印
                    System.out.println(Thread.currentThread().getName() + " " + print1To100.count);
                    print1To100.count++;


//                    lock.notifyAll();
//                    lock.wait();
                }
            }finally {
                lock.unlock();
            }

        }
    }


    public static void main(String[] args) {
        Thread th1 = new Thread(new PC());
        Thread th2 = new Thread(new PC());

        th1.start();
        th2.start();

    }


}