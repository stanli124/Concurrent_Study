

public class synchronized_keyword implements Runnable {

    private static int count = 0;

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread( new synchronized_keyword());  //创建10个线程 并 启动线程；每个线程都会对静态变量count执行1000000次加法；
//            thread.start();
//        }
//        try {
//            Thread.sleep(500);  //主线程休眠500ms
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//
//        System.out.println(count);

        synchronized (synchronized_keyword.class){
            System.out.println("hello word!");
        }

    }

    @Override
    public void run(){
        for (int i = 0; i < 1000000; i++) {
            count++;
        }
    }
}
