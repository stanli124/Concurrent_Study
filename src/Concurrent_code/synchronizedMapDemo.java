/** 用synchronizedMap包装一个hashmap使之成为线程安全的，我要测试两个线程对同一个线程安全的map调用同一个方法的时候会不会出现同步错误
 *  因为synchronizedMap源码中的方法都是使用synchronized进行了加锁，并且是对同一个对象加锁，所以如果一个线程获得了锁，另外的线程想去执行某方法会被阻塞，直到获得锁的线程执行完代码释放锁，阻塞的线程才有机会去获得锁。
 */

package Concurrent_code;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class synchronizedMapDemo {

    static Map<Integer,String> map = new HashMap();
    static Map synmap = Collections.synchronizedMap(map);

    public static void main(String[] args) {
        new Thread(()->{
            synmap.put(1,"lpc");
            synmap.put(2,"tyj");
            System.out.println(synmap.size());
            System.out.println(synmap);
        }).start();

        new Thread(()->{
            synmap.put(3,"lpc");
            synmap.put(4,"tyj");
            System.out.println(synmap.size());
            System.out.println(synmap);
        }).start();

        new Thread(()->{
            synmap.put(5,"lpc");
            synmap.put(6,"tyj");
            System.out.println(synmap.size());
            System.out.println(synmap);
        }).start();

        new Thread(()->{
            synmap.put(7,"lpc");
            synmap.put(8,"tyj");
            System.out.println(synmap.size());
            System.out.println(synmap);
        }).start();


        try {
            Thread.sleep(200);
//            System.out.println(synmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
