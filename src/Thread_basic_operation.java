/**
 *
 */

public class Thread_basic_operation {



    public static void main(String[] args) {
        final Thread sleepThread = new Thread(){ //使用final修饰，那么sleepThread不能再指向另一个引用；
            @Override
            public void run(){
                try{
                    Thread.sleep(1000); //该线程实例睡眠1s
                }catch (InterruptedException e){
                    e.printStackTrace();
//                    System.out.println(e);
                }
                super.run();
            }
        };

        Thread busyThread = new Thread(){
            @Override
            public void run(){  //该线程实例执行死循环
                while (true);
            }
        };

        sleepThread.start(); //启动两个线程
        busyThread.start();
        sleepThread.interrupt(); //对两个线程执行中断操作
        busyThread.interrupt();
        while (sleepThread.isInterrupted()) ;
        System.out.println("sleepThread isInterrupted: " + sleepThread.isInterrupted());
        //false是因为该对象被调用了sleep方法，这时候调用interrupt会抛出InterruptedException异常，导致中断标志位被清除。
        System.out.println("busyThread isInterrupted: " + busyThread.isInterrupted());
    }



}
