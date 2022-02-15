//public class EarlyNotify {
//    private static String lockObject = "";
//
//    public static void main(String[] args) {
//        WaitThread waitThread = new WaitThread(lockObject); //两个对象使用的是同一个字符串对象，也就是同一个锁；
//        NotifyThread notifyThread = new NotifyThread(lockObject);
//        notifyThread.start(); //先启动了通知线程
//        try {
//            System.out.println("主线程休眠");
//            Thread.sleep(3000); //主线程休眠3秒，让其它线程有时间执行
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        waitThread.start();  //后启动等待线程
//    }
//
//    static class WaitThread extends Thread {
//        private String lock;
//
//        public WaitThread(String lock) {
//            this.lock = lock;
//        }
//
//        @Override
//        public void run() {
//            synchronized (lock) {
//                try {
//                    System.out.println("等待线程代码");
//                    System.out.println(Thread.currentThread().getName() + "  进去代码块");
//                    System.out.println(Thread.currentThread().getName() + "  开始wait");
//                    lock.wait();
//                    System.out.println(Thread.currentThread().getName() + "   结束wait");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    static class NotifyThread extends Thread {
//        private String lock;
//
//        public NotifyThread(String lock) {
//            this.lock = lock;
//        }
//
//        @Override
//        public void run() {
//            synchronized (lock) {
//                System.out.println("通知线程代码");
//                System.out.println(Thread.currentThread().getName() + "  进去代码块");
//                System.out.println(Thread.currentThread().getName() + "  开始notify");
//                lock.notify();
//                System.out.println(Thread.currentThread().getName() + "   结束开始notify");
//            }
//        }
//    }
//}

/**增加一个标志位
 * 用来表示通知线程是否已经通知过了；
 */
public class EarlyNotify {
    private static String lockObject = "";
    private static boolean isWait = true;

    public static void main(String[] args) {
        WaitThread waitThread = new WaitThread(lockObject);
        NotifyThread notifyThread = new NotifyThread(lockObject);
        notifyThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitThread.start();
    }

    static class WaitThread extends Thread {
        private String lock;

        public WaitThread(String lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    while (isWait) {
                        System.out.println(Thread.currentThread().getName() + "  进去代码块");
                        System.out.println(Thread.currentThread().getName() + "  开始wait");
                        lock.wait(); //调用wait()线程会释放锁
                        System.out.println(Thread.currentThread().getName() + "   结束wait");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class NotifyThread extends Thread {
        private String lock;

        public NotifyThread(String lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "  进去代码块");
                System.out.println(Thread.currentThread().getName() + "  开始notify");
                lock.notifyAll(); //这里会执行完整个同步代码块的代码后，才释放lock锁
                isWait = false;
                System.out.println(Thread.currentThread().getName() + "   结束开始notify");
            }
        }
    }
}