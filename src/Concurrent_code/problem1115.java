/** 给定foobar类
 *  两个不同的线程将会共用一个FooBar类实例
 *  线程A调用foo方法
 *  线程B调用bar方法
 *  请修改程序，确保“foobar”被输出n次。
 */

package Concurrent_code;

import jdk.management.resource.internal.inst.ThreadRMHooks;

public class problem1115 {


    public static void main(String[] args) {
        FooBar fb = new FooBar(10); //需要打印foobar十次
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fb.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fb.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("bar");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        th1.start();
        th2.start();

        try {
            Thread.sleep(100);
//            fb.lock.notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


class FooBar {
    private int n;
    String lock = " ";

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        synchronized (lock){
            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                lock.notifyAll();
                lock.wait();
            }
            lock.notifyAll();
        }

//        for (int i = 0; i < n; i++) {
//
//            // printFoo.run() outputs "foo". Do not change or remove this line.
//            printFoo.run();
//        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        synchronized (lock){
            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printBar.run();
                lock.notifyAll();
                lock.wait();
            }
            lock.notifyAll();
        }

//        for (int i = 0; i < n; i++) {
//
//            // printBar.run() outputs "bar". Do not change or remove this line.
//            printBar.run();
//        }
    }
}