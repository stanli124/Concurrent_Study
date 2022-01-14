/** final可以修饰变量，方法和类。表示所修饰的内容一旦赋值之后就不会再被改变。
 *  类变量（static修饰的变量）：必须要在静态初始化块中指定初始值或者声明该类变量时指定初始值，而且只能在这两个地方之一进行指定；
 *  实例变量：必要要在非静态初始化块，声明该实例变量或者在构造器中指定初始值，而且只能在这三个地方进行指定
 */

package finalKeyword;

public final class test {

    int a = 0;

    final static int b;   //静态变量(类变量)可以在静态初始化块中赋初值。
    int c;
//    final int d; //final未初始化的时候不会隐式初始化，因此会引发错误；
    final int e;

    { //初始化块
        e=3; //非静态变量可以在初始化块中赋值；
    }
    {
//        e = 5;  //e已经赋值后不能再次赋值；
    }

    public test() {
//        e = 4;  //e已经赋值后不能再次赋值；
    }

    static { //静态初始化块
        b = 2; //静态变量(类变量)可以在静态初始化块中赋初值。
//        c=3; //非静态变量无法在静态初始化块中赋初值。
    }


    public static void main(String[] args) {
        final test a = new test();
        a.a = 1;
//        a = new test(); //test对象a是final变量，所有在重新给a赋值的时候会报错
        System.out.println(a.a);
    }

}

class FinalParent{
    final public void test(){

    }
}

//当父类是final修饰的时候不能被继承；
class  Finale extends FinalParent{
//    @Override
//    public void test(){
//
//    }   //无法重写被final修饰的父类方法

    //重载父类方法是可以的
    public void test(int a){

    }

    public final void test(String  a){

    }

}


/** final修饰局部变量
 *  当final修饰基本数据类型变量时，不能对基本数据类型变量重新赋值，因此基本数据类型变量不能被改变。而对于引用类型变量而言，它仅仅保存的是一个引用，final只保证这个引用类型变量所引用的地址不会发生改变，即一直引用这个对象，但这个对象属性是可以改变的。
 */


/**  当父类的方法被final修饰的时候，子类不能重写父类的该方法，比如在Object中，getClass()方法就是final的，我们就不能重写该方法，但是hashCode()方法就不是被final所修饰的，我们就可以重写hashCode()方法。我们还是来写一个例子来加深一下理解：
 先定义一个父类，里面有final修饰的方法test();
 *
 *
 */
