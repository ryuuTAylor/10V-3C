public class Main {

  // pre-compute some factorials
  static int[] fact = new int[11];

  // choose y from x, requires y <= x <= 10
  public static int choose(int x, int y) {
    if (y > x)
      return 0;
    else if (y == x || y == 0)
      return 1;
    assert x <= 10;
    int ans = fact[x] / (fact[y] * fact[x - y]);
    // Debug Code for Examining choose y form x

    // System.out
    //     .println("Choosing " + Integer.toString(y) + " from " + Integer.toString(x) +
    //         " is " + Integer.toString(ans));
    return ans;
  }

  public static void main(String[] args) {
    fact[0] = 1;
    for (int i = 1; i <= 10; i++) {
      fact[i] = fact[i - 1] * i;
    }

    // Debug Code for Examining fact[n]

    // for (int i = 1; i <= 10; i++) {
    //   System.out.println(
    //       "The factorial of " + Integer.toString(i) + " is " +
    //           Integer.toString(fact[i]));
    // }

    long[] c = new long[11];
    c[1] = 1L;
    System.out.println(
        "The number of connected graphs with " + Integer.toString(1) + " vertices is " + Long.toString(c[1]));

    // Calculate c[n] using direct approach

    // for (int n = 2; n <= 10; n++) {
    //   for (int i = 1; i <= n - 1; i++) {
    //     c[n] += c[i] * c[n - i] * choose(n - 2, i - 1) * ((1 << i) - 1);
    //   }
    //   System.out.println(
    //       "The number of connected graphs with " + Integer.toString(n) + " vertices is " + Long.toString(c[n]));
    // }

    // Calculate c[n] = g[n] - d[n] using the Subtraction Rule
    
    for (int n = 2; n <= 10; n++) {
      c[n] = 1L << (choose(n, 2)); // all possible graphs
      for (int i = 1; i <= n - 1; i++) {
        c[n] -= choose(n - 1, i - 1) * c[i] * (1L << choose((n - i), 2));
      }
      System.out.println(
          "The number of connected graphs with " + Integer.toString(n) + " vertices is " + Long.toString(c[n]));
    }

    // total graphs with 10 vertices
    long result = 1L << 45;

    // subtract cases when the graph has two components, one of size i, the other
    // of size n-i
    for (int i = 1; i <= 4; i++) {
      result -= choose(10, i) * c[i] * c[10 - i];
    }

    // subtract the case when the graph has two size 5 components, special treatment
    result -= choose(10, 5) * (c[5] * c[5]) / 2;

    // subtract the case when the graph has one component
    result -= c[10];

    System.out.println("The are " + Long.toString(result)
        + " simple undirected graphs G = (V, E) with these vertices have at least 3 connected components.");
  }
}
