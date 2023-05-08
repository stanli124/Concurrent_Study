public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println();
            System.out.println(Thread.currentThread());
        }, "test");
        System.out.println(thread.getState() + thread.getName());
        thread.start();
        System.out.println(thread.getState() + thread.getName());
        thread.join(1000);
        System.out.println(thread.getState() + thread.getName());
    }

}
