import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'minimumBribes' function below.
     *
     * The function accepts INTEGER_ARRAY q as parameter.
     */

     public static void minimumBribes(List<Integer> q) {
        int numberOfChanges = 0;
        boolean ok = true;
        for (int i = q.size() - 1; i >= 0; i--) {
            if (q.get(i) != i + 1){
                if (i >= 1 && q.get(i - 1) == i + 1) {
                    Collections.swap(q, i, i - 1);
                    numberOfChanges++;
                } else if (i >= 2 && q.get(i - 2) == i + 1) {
                    Collections.swap(q, i - 1, i - 2);
                    Collections.swap(q, i - 1, i);
                    numberOfChanges +=2;
                } else {
                    ok = false;
                    break;
                }
            }
        }
        if (!ok) {
            System.out.println("Too chaotic");
        } else {
            System.out.println(numberOfChanges);
        }
    }
}
