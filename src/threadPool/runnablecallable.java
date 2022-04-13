package threadPool;

import java.util.concurrent.*;

public class runnablecallable {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> submit = executor.submit(() -> {
            System.out.println(Thread.currentThread());
            System.out.println("runnable");
        });


        try {
            Thread.sleep(100);
            System.out.println("runable的Future");
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println();

        Future<String> submit1 = executor.submit(() -> {
            System.out.println(Thread.currentThread());
            return "callable";
        });

        try {
            Thread.sleep(100);
            System.out.println("callable的Future");
            System.out.println(submit1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
