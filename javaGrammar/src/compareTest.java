import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 实现 compare
 * 要么实现Comparable接口中的CompareTo方法
 * 要么实现Comparator中的compare方法
 */

//实现Comparable接口
public class compareTest implements Comparable<compareTest>{
    private Integer id;

    public compareTest(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(compareTest o) {
        return this.id - o.id;
    }

    public static void main(String[] args) {
        compareTest c1 = new compareTest(5);
        compareTest c2 = new compareTest(10);

        System.out.println(c1.compareTo(c2));
    }

}



class idCompare{
    private Integer id;

    public idCompare(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static void main(String[] args) {
        List<idCompare> a = new LinkedList<>();


        for (int i = 4; i >=0 ; i--) {
            a.add(new idCompare(i));
        }
        for (int i = 0; i < 5; i++) {
            System.out.printf(a.get(i).getId()+" ");
        }
        Collections.sort(a, new idComparator());
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.printf(a.get(i).getId()+" ");
        }

    }


}

//实现Comparator的子类
class idComparator implements Comparator<idCompare>{
    @Override
    public int compare(idCompare o1, idCompare o2) {
        return o1.getId() - o2.getId();
    }
}

