import java.util.Arrays;

public class App {
    public static void ThreeSum(int[] arr) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            int l = i + 1;
            int r = arr.length - 1;
            while (l < r) {
                if (arr[i] + arr[l] + arr[r] == 0) {
                    System.out.println(arr[i] + " " + arr[l] + " " + arr[r]);
                    l++;
                    r--;
                } else if (arr[i] + arr[l] + arr[r] < 0) {
                    l++;
                } else {
                    r--;  
                }
            }
        }
        
    }
    public static void main(String[] args) {
        int[] arr = {1, -1, 0, 2, -2};
        ThreeSum(arr);
    }
}
