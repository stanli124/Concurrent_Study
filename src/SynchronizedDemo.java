//public class SynchronizedDemo {
//    private static int count = 0;
//    public static void main(String[] args) {
//
//
//
//    }
//
//}


public class SynchronizedDemo implements Runnable {
    private static int count = 0;
    public static void main(String[] args) {
        int k = 11;
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new SynchronizedDemo()); //新建线程，每个线程都对count进程累加操作；
            t.start();
        }
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(count);
    }

    @Override
    public void run(){
        synchronized (SynchronizedDemo.class){  //这个锁是类锁，每个类只有这一个锁
            for (int i = 0; i < 1000000; i++) {
                count++;
            }
        }
    }

}
