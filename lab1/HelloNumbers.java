public class HelloNumbers {
    public static void cummulative(int n, int sum, int add) {
      if (n <= 0) {
        System.out.println();
      } else {
        System.out.print(sum + " ");
        cummulative(n-1, sum + add, add + 1);
      }
    }
    public static void main(String[] args) {
        cummulative(10, 0, 1);
    }
}