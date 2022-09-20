import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {
    public static int pairs(int k, int[] mainArr) {
        int arrLength = mainArr.length;
        int ans = 0;
        //int[] mainArr = new int[100005];
       // for (int cur: arr) {
       //     mainArr[arrLength] = cur;
       //     arrLength++;
       // }
        Arrays.sort(mainArr);
        for (int i = 0; i < arrLength; i++) {
            int curFirstPos = -1, lo = 0, hi = arrLength - 1, curLastPos = -1;
            while (lo <= hi){
                int mid = (lo + hi)/2;
              //  System.out.println(lo + " " + mid + " " + hi);
                if (mainArr[mid] <= mainArr[i] + k) {
                    if (mainArr[mid] == mainArr[i] + k) {
                        curFirstPos = mid;
                    }
                    lo = mid + 1;
                }
                else hi = mid - 1;
            }
            lo = 0; hi = arrLength - 1;
            while (lo <= hi){
                int mid = (lo + hi)/2;
                if (mainArr[mid] >= mainArr[i] + k) {
                    if (mainArr[mid] == mainArr[i] + k) {
                        curLastPos = mid;
                    }
                    hi = mid - 1;
                }
                else lo = mid + 1;
            }
          //  System.out.println(curFirstPos + " - " + curLastPos);
            if (curFirstPos == -1 || curLastPos == -1) continue;
            ans += curLastPos - curFirstPos + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
       int[] arr = {1, 5, 3, 4, 2};
        System.out.println(pairs(2, arr));
    }

}
