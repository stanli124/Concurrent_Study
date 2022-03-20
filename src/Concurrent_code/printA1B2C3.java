package Concurrent_code;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
