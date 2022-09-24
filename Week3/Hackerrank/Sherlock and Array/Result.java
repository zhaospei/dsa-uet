import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'balancedSums' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static String balancedSums(List<Integer> arr) {
    // Write your code here
        int arrLength = 0, ans = 0, sum = 0, curSum = 0;
        for (int cur: arr) {
            arrLength++;
        }
        int[] mainArr = new int[arrLength];
        arrLength = 0;
        for (int cur: arr) {
            mainArr[arrLength] = cur;
            arrLength++;
            sum += cur;
        }
        for (int i = 0; i < arrLength; i++) {
            if (curSum == sum - curSum - mainArr[i]) return "YES";
            curSum += mainArr[i];
        }
        return "NO";
    }
}