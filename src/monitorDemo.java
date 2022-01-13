public class monitorDemo {

    private int a = 0;

    public synchronized void writer(){
        a++;
    }

    public  synchronized void reader(){
        int i = a;
    }

    public static void main(String[] args) {

    }

}
