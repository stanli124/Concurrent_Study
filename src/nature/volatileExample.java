package nature;

public class volatileExample {

    private static volatile int counter = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++)
                        synchronized (volatileExample.class){
                            counter++;
                        }
//                    counter++;
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(15); //这里睡眠时间短导致主线程很快执行完，其它线程还没有执行完，还没有完成累加；
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);

    }

}
