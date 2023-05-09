public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println();
            System.out.println(Thread.currentThread().getState()+Thread.currentThread().getName());
            try {
                Thread.currentThread().join(1000); //主线程休眠后执行这段代码，等待1.5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "11");
        System.out.println(thread.getState() + thread.getName()); //刚刚创建线程，是new状态

        thread.run();
        System.out.println(thread.getState() + thread.getName()); //调用start后是可运行状态

        Thread.sleep(500); //主线程休眠
        System.out.println(thread.getState() + thread.getName()); //thread等待过程中，执行主线程，此时thread是timed waiting状态

        //Thread.currentThread().interrupt(); 这里演示主线程执行sleep后，若再执行中断方法，会抛出一个异常；

        Thread.sleep(1000); //主线程休眠1s等待thread执行完
        System.out.println(thread.getState() + thread.getName()); //此时thread执行完，进入终止状态
    }

}
