package threadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class countdownTest {

    private static final int RUNNER_COUNTS = 10;

    public static void main(String[] args) {
        final CountDownLatch begin = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(RUNNER_COUNTS);
        final ExecutorService executors = Executors.newFixedThreadPool(10);

        for (int i = 0; i < RUNNER_COUNTS; i++) {
            int No = i + 1;
            Runnable run  = new Runnable() {
                @Override
                public void run() {
                    try {
                        begin.await(); //每个线程开始运行的时候先阻塞
                        Thread.sleep(1000); //模拟跑步时间
                        System.out.println("No." + No + "到达终点");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            executors.submit(run);
        }

        //到这里所有线程都被阻塞，等待发令开始跑步
        System.out.println("开始跑步");
        begin.countDown(); //减为0，所有线程不在阻塞

        try {
            end.await(); //阻塞主线程，等待所有到达终点
            System.out.println("所有人到达终点");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executors.shutdown();
        }


    }

}
