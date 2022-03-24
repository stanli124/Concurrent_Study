/** 给你一个类
 *  三个不同的线程A、B、C将会共用一个Foo实例
 *  线程A调用first方法
 *  线程B调用second方法
 *  线程C调用third方法
 *  确保second方法在first方法之后被执行，third方法在second方法之后被执行。
 */

package Concurrent_code;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class leecode1114 {
    int[] nums = {1,2,3};

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

        Thread A = new Thread(()->{
            try {
                foo.first(new print("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Thread B = new Thread(()->{
            try {
                foo.second(new print("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Thread C = new Thread(()->{
            try {
                foo.third(new print("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        A.start();
        B.start();
        C.start();

        Thread.sleep(100);

//        a.run();
    }
}

class print implements Runnable{
    String type;
    public print(String s){
        type = s;
    }
    @Override
    public void run() {
        System.out.printf(type);
    }
}


//能过，但是超时了
//class Foo {
//    static int status = 1;
//    static String s = "lock";
//
//    public Foo() {
//
//    }
//
//    public void first(Runnable printFirst) throws InterruptedException {
//        //传入实现了Runnable的接口
//        // printFirst.run() outputs "first". Do not change or remove this line.
//        synchronized (s){
////            if (status == 1){
////                printFirst.run(); //输出first
////                status += 1;
////                status.notify();
////            }
//
//            while (status!=1){
//                s.wait();
//            }
//            printFirst.run();
//            status +=1;
//            s.notifyAll();
//        }
//
////        printFirst.run(); //输出first
//    }
//
//    public void second(Runnable printSecond) throws InterruptedException {
//        //传入实现了Runnable接口的类实例
//        // printSecond.run() outputs "second". Do not change or remove this line.
//        synchronized (s){
//
//            while (status!=2){
//                s.wait();
//            }
//            printSecond.run();
//            status +=1;
//            s.notifyAll();
//        }
//
////        printSecond.run(); //输出second
//    }
//
//    public void third(Runnable printThird) throws InterruptedException {
//
//        synchronized (s){
//            while (status!=3){
//                s.wait();
//            }
//            printThird.run();
//            status +=1;
//            s.notifyAll();
//        }
//
//        // printThird.run() outputs "third". Do not change or remove this line.
////        printThird.run(); //输出third
//    }
//}


//这种写法也超时
class Foo {
    static int status = 1;

    ReentrantLock lock = new ReentrantLock();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        //传入实现了Runnable的接口
        // printFirst.run() outputs "first". Do not change or remove this line.

        lock.lock();
        try {
            if (status==1)
                printFirst.run();
            status++;
            c2.signal();
        }finally {
            lock.unlock();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {
        //传入实现了Runnable接口的类实例
        // printSecond.run() outputs "second". Do not change or remove this line.

        lock.lock();
        try {
            while (status!=2)  //不等于2则进入c2进行等待，知道线程A唤醒
                c2.await();
            printSecond.run();
            status++;
            c3.signal();
        }finally {
            lock.unlock();
        }

    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        try {
            while (status!=3) //不等于3则进入等待，直到线程B唤醒
                c3.await();
            printThird.run();
        }finally {
            lock.unlock();
        }

    }
}