/** 针对被volatile修饰的变量，java虚拟机有特殊的约定，线程对volatile修饰的变量的修改会立即被其他线程所感知
 *  保证每个线程能够获取该变量的最新值，从而避免出现数据脏读的现象。
 *
 *  下面实例中，创建了两个线程，两个线程对两个static变量进行操作。
 *
 */

package volatileKeyword;

public class volatileTest  {

    private static int a = 0;
    private static volatile boolean flag = false;
    static volatile int i = 0;

    public void writer(){
//        System.out.println(Thread.currentThread().getName()+"------------------writer方法-----------------");
//        System.out.println("a = "+a);
        a = 1;
        flag = true;
    }

    public void reader(){
//        System.out.println(Thread.currentThread().getName()+"------------------reader方法-----------------");
        if (flag){
//            System.out.println(flag);
//            System.out.println("a = "+a);
            i = a;
//            System.out.println("i = "+i);
        }
    }

    public static int getA() {
        return a;
    }

    public static int getI() {
        return i;
    }

    public static boolean isFlag() {
        return flag;
    }
}

class testVolatile{
    public static void main(String[] args) {

        Thread exeWriter = new Thread(){
            volatileTest a = new volatileTest(); //该实例对象中含有变量a和flag;

            @Override
            public void run(){
                a.writer(); //先执行实例对象的writer方法，再执行实例对象的reader方法；
//                a.reader();
            }
        };

        Thread exeReader = new Thread(){
            volatileTest a = new volatileTest();

            @Override
            public void run(){
                a.reader(); //执行reader方法；
            }
        };

        exeWriter.start(); //线程1，执行writer方法，对a和flag进行赋值。
        exeReader.start(); //线程2，执行reader方法，对把a的值赋值给i。

        try {
            Thread.sleep(2000); //主线程休眠，给其他线程执行时间。
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("a = "+volatileTest.getA());
        System.out.println("flag = "+volatileTest.isFlag());
        System.out.println("i = "+volatileTest.getI()); //小概率可能会出现i=0的情况，可能因为执行了writer方法后，main线程竞争到资源执行代码，reader在main线程之后执行，所以i输出0；


    }
}

class volatileDemo{
    private static volatile boolean isOver = false;

    public static void main(String[] args) {
        Thread th = new Thread(){
          @Override
          public void run(){
              while (!isOver);
          }
        };

        th.start(); //th线程里面使用的是旧值

        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        isOver = true; //main线程里面修改了volatile变量，对值的修改能立刻被其他线程感知到；

    }

}