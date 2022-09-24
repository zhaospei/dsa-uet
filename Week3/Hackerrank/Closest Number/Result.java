import java.io.*;
import java.util.*;

public class Result {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);
        int min = arr[1] - arr[0];
        for(int i = 0; i < n - 1; i++){
            min = Math.min(min, arr[i + 1] - arr[i]);
        }
        String ans = "";
        for(int i = 0; i < n - 1; i++){
            if(arr[i + 1] - arr[i] == min){
                ans += arr[i] + " " + arr[i+1] + " ";
            }
        }
        System.out.println(ans);
        scanner.close();
     }
}
