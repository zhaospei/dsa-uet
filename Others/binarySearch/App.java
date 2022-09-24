import java.util.Arrays;
import java.util.Random;

public class App {
    public static int binarySearch(int[] a, int number) {
        int lo = 0, hi = a.length - 1, ans = -1;
        while (lo <= hi) {
            int mid = (lo + hi)/2;
            if (a[mid] <= number) {
                if (a[mid] == number) {
                    ans = mid;
                }
                lo = mid + 1;
            }
            else hi = mid - 1;
        }
        return ans;
    }
    public static void main(String[] args) {
        int n = (int)(Math.random() * 100);
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
            int curNumber = (int)(Math.random() * 20) - 10;
            a[i] = curNumber;
        }
        Arrays.sort(a);
        int number = (int)(Math.random() * 20) - 10;
        System.out.println(binarySearch(a, number));
    }
}