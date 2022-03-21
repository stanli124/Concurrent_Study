package Concurrent_code;

import java.util.concurrent.locks.Condition;
//<<<<<<< HEAD
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class printA1B2C3 {
//    public static void main(String[] args) {
////        System.out.println(Thread.currentThread().getId());
//        Thread th1 = new Thread(new printThread());
//        Thread th2 = new Thread(new printThread());
//        Thread th3 = new Thread(new printThread());
//        th1.start();
//        th2.start();
//        th3.start();
//        try {
//            Thread.sleep(2000);
//        }catch (InterruptedException e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//}
//
//class printThread implements Runnable{
//    int count = 1;
//    @Override
//    public void run() {
//        synchronized (printA1B2C3.class){
//            while (count <= 50){
////                System.out.println(Thread.currentThread().getName());
//                if (Thread.currentThread().getName().equals("Thread-1")) System.out.println("A");
//                else if (Thread.currentThread().getName().equals("Thread-2")) System.out.println("BB");
//                else if (Thread.currentThread().getName().equals("Thread-0")) System.out.println("CCC");
//                count++;
//            }
//        }
//    }
//}

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Resource{

    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();  //该方法每次都会生成一个新的Condition对象
    private Condition c2 = lock.newCondition();
    private  Condition c3 = lock.newCondition();



}

//=======
//import java.util.concurrent.locks.ReentrantLock;

public class printA1B2C3 {



}


class concurrentPrint{
    private int number = 1;
    private int count1 = 1;
    private int count2 = 1;
    private int count3 = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print1(){
        lock.lock();
        try {
            while (number != 1){
                c1.await();
            }
            for (int i = 1; i < 2; i++) {
                System.out.println(Thread.currentThread().getName() + " " + "a " + count1);
            }
            count1++;
            number = 2;
            c2.signal();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    public void print2(){
        lock.lock();
        try {
            while (number!=2){
                c2.await();
            }
            for (int i = 1; i < 3; i++) {
                System.out.println(Thread.currentThread().getName()+ " " +"bb " + count2);
            }
            ++count2;
            number = 3;
            c3.signal();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    public void print3(){
        lock.lock();
        try {
            while (number != 3){
                c3.await(); //将当前线程放入c3的等待队列
            }
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + " " + "ccc " + count3);
            }
            count3++;
            number = 1;
            c1.signal();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        concurrentPrint p = new concurrentPrint();
        new Thread(() -> {
            for (int i = 1; i <=100 ; i++) {
                p.print1();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                p.print2();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                p.print3();
            }
        }, "c").start();

    }

}
//>>>>>>> origin/master
