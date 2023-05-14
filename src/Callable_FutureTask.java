import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Callable_FutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> task = ()->{
            return 1;
        };
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);

        Thread thread = new Thread(integerFutureTask);
        thread.start();

        System.out.printf(""+integerFutureTask.get());

    }
}
