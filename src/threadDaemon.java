public class threadDaemon {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        System.out.println("i am alive");
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        System.out.println("finallt code block");
                    }
                }
            }
        });

        daemonThread.setDaemon(true);
        daemonThread.start();
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
