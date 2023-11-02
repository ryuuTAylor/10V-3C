public class Main {

  // pre-compute some factorials
  static int[] fact = new int[11];

  // choose y from x, requires y <= x <= 10
  public static int choose(int x, int y) {
    // if (y >= x || y == 0 || x == 0)
    // return 1;
    assert x <= 10;
    int ans = fact[x] / (fact[y] * fact[x - y]);
    // System.out
    // .println("Choosing " + Integer.toString(y) + " from " + Integer.toString(x) +
    // " is " + Integer.toString(ans));
    return ans;
  }

  public static void main(String[] args) {
    fact[0] = 1;
    for (int i = 1; i <= 10; i++) {
      fact[i] = fact[i - 1] * i;
    }

    // for (int i = 1; i <= 10; i++) {
    // System.out.println(
    // "The factorial of " + Integer.toString(i) + " is " +
    // Integer.toString(fact[i]));
    // }

    long[] c = new long[11];
    c[1] = 1L;
    for (int n = 2; n <= 10; n++) {
      for (int i = 1; i <= n - 1; i++) {
        c[n] += c[i] * c[n - i] * choose(n - 2, i - 1) * ((1 << i) - 1);
      }
    }

    // Print out the number of connected graphs for n = 1 to 10
    for (int i = 1; i <= 10; i++) {
      System.out.println(
          "The number of connected graphs with " + Integer.toString(i) + " vertices is " + Long.toString(c[i]));
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
