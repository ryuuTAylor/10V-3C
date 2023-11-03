#include <iostream>
#include <cassert>

using namespace std;

// pre-compute some factorials
int fact[11];

// choose y from x, requires y <= x <= 10
int choose(int x, int y)
{
  if (y > x)
    return 0;
  else if (y == x || y == 0)
    return 1;
  assert(x <= 10);
  int ans = fact[x] / (fact[y] * fact[x - y]);
  // Debug Code for Examining choose y form x

  // cout << "Choosing " << y << " from " << x
  //           << " is " << ans << endl;
  return ans;
}

int main()
{
  fact[0] = 1;
  for (int i = 1; i <= 10; ++i)
  {
    fact[i] = fact[i - 1] * i;
  }

  // Debug Code for Examining fact[n]

  // for (int i = 1; i <= 10; ++i) {
  //     cout << "The factorial of " << i << " is " << fact[i] << endl;
  // }

  long c[11];
  c[1] = 1L;
  cout << "The number of connected graphs with " << 1
       << " vertices is " << c[1] << endl;

  // Calculate c[n] using direct approach

  // for (int n = 2; n <= 10; ++n) {
  //     for (int i = 1; i <= n - 1; ++i) {
  //         c[n] += c[i] * c[n - i] * choose(n - 2, i - 1) * ((1 << i) - 1);
  //     }
  //     cout << "The number of connected graphs with " << n
  //               << " vertices is " << c[n] << endl;
  // }

  // Calculate c[n] = g[n] - d[n] using the Subtraction Rule
  for (int n = 2; n <= 10; ++n)
  {
    c[n] = 1L << (choose(n, 2)); // all possible graphs
    for (int i = 1; i <= n - 1; ++i)
    {
      c[n] -= choose(n - 1, i - 1) * c[i] * (1L << choose(n - i, 2));
    }
    cout << "The number of connected graphs with " << n
         << " vertices is " << c[n] << endl;
  }

  // total graphs with 10 vertices
  long result = 1L << 45;

  // subtract cases when the graph has two components, one of size i, the other of size 10-i
  for (int i = 1; i <= 4; ++i)
  {
    result -= choose(10, i) * c[i] * c[10 - i];
  }

  // subtract the case when the graph has two size 5 components, special treatment
  result -= choose(10, 5) * (c[5] * c[5]) / 2;

  // subtract the case when the graph has one component
  result -= c[10];

  cout << "There are " << result
       << " simple undirected graphs G = (V, E) where these vertices have at least 3 connected components." << endl;

  return 0;
}
