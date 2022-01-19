

public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        System.out.print("EXCLUSIVE_MASK = (1<<SHARED_SHIFT) - 1: ");  //1111111111111111
        System.out.println((1<<16)-1);

        System.out.print("100 & EXCLUSIVE_MASK:");
        System.out.println(65537 & ((1<<16)-1)); ////保证数值不会超过65535，超过后从0开始算。

        System.out.println(Integer.toBinaryString(16));
        System.out.println("------------------------");

        System.out.println(700054>>>16);
        System.out.println(Integer.toBinaryString(700054));




    }

}

