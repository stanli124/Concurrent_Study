package Concurrent_code;

import java.util.Scanner;

public class byte02 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int[] points = new int[n];
        for (int i = 0; i < n; i++) {
            points[i] = in.nextInt();
        }


        int curPoint = 0;
        int steps = 0;
        int[] choices = new int[]{1,-1};
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int targetPoint = points[i];
            steps += countSteps(curPoint, targetPoint, choices, 0);
            curPoint = points[i];
            res[i] = steps;
        }
        for (int i = 0; i < n; i++) {
            System.out.println(res[i]);
        }
    }

    public static int countSteps(int curPoint, int targetPoint, int[] choices, int steps){
        if (curPoint == targetPoint) return steps;
        if (curPoint > targetPoint) return -1;
        int[] ch = new int[2];
        ch[0] = choices[0] + 1;
        ch[1] = -(choices[0] + 1);

        for (int i = 0; i < choices.length; i++) {
            int n1 = countSteps(curPoint+choices[i], targetPoint, ch, steps+1);
            if (n1!=-1) return n1;
        }
        return -1;
    }
}
