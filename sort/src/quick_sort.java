public class quick_sort {

    public static void main(String[] args) {
        int[] a = new int[]{3,7,4,5,1,2,10,8,34,4,9,8,4,56,41,4,1,5,8,0,0,1,1,2};
        quicksort(a, 0, a.length-1);
        for(int s : a){
            System.out.println(s);
        }
    }

    static void quicksort(int[] n, int left, int right){
        int dp;
        if (left < right){
            dp = partition(n, left, right);
            quicksort(n, left, dp-1);
            quicksort(n, dp+1, right);
        }
    }

    static int partition(int[] n, int left, int right){
        int pivot = n[left]; //基准数，比它大的都放在右边；比它小的都当做左边；
        while (left < right){
            while (left < right && n[right] >= pivot) right--; //找到比基准数小的数,碰到大于基准数的就继续往前找
            if (left < right){
                n[left++] = n[right]; //把小的值换到左边去
            }
            while (left < right && n[left] <= pivot) left++; //找到比基准数大的数，碰到小于基准数的继续往后找
            if (left < right)
                n[right--] = n[left];
            n[left] = pivot;
        }
        return left;
    }


}
