public class ThreadState {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println();
            System.out.println(Thread.currentThread());
        }, "test");
        System.out.println(thread.getState() + thread.getName());
        thread.start();
        System.out.println(thread.getState() + thread.getName());
    }

}
