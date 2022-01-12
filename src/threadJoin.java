

public class threadJoin {
    public static void main(String[] args) {

        Thread preThread = Thread.currentThread();  //前一个线程
        for (int i = 1; i <= 10; i++) {
            Thread curThread = new JoinThread(preThread, i); //当前线程
            curThread.start();          //会去执行当前线程的run方法；run方法会执行前一个线程的join方法；
            preThread = curThread;      //前一个线程赋值为当前线程；
//            System.out.println(i);
        }

    }

    static class JoinThread extends Thread {
        private Thread thread; //当前线程会保存一个线程，这里是保存前一个线程的引用；
        int val = -1;

        public JoinThread(Thread thread, int val) {
            this.thread = thread;
            this.val = val;
        }

        @Override
        public void run() {
            try {
//                Thread.sleep(500);
                thread.join(); //一个线程实例A执行了threadB.join(),其含义是：当前线程A会等待threadB线程终止后threadA才会继续执行;
                System.out.println("pre thread is "+thread.getName() + ", current thread index is " + this.val + " terminated.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

}
