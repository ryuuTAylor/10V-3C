#include <iostream>
#include <cassert>

using namespace std;

// Precompute some factorials
int fact[11];

// Function that chooses y from x, requires y <= x <= 10
int choose(int x, int y)
{
  assert(x <= 10);
  return fact[x] / (fact[y] * fact[x - y]);
}

int main()
{
  fact[0] = 1;
  for (int i = 1; i <= 10; i++)
  {
    fact[i] = fact[i - 1] * i;
  }

  long c[11] = {0};
  c[1] = 1L;

  // Calculate c[n] using direct approach

  // for (int n = 2; n <= 10; ++n)
  // {
  //   for (int i = 1; i <= n - 1; ++i)
  //   {
  //     c[n] += c[i] * c[n - i] * choose(n - 2, i - 1) * ((1 << i) - 1);
  //   }
  // }

  // Calculate c[n] = g[n] - d[n] using the Subtraction Rule

  for (int n = 2; n <= 10; n++)
  {
    c[n] = 1L << (choose(n, 2)); // all possible graphs
    for (int i = 1; i <= n - 1; i++)
    {
      c[n] -= choose(n - 1, i - 1) * c[i] * (1L << choose((n - i), 2));
    }
  }

  // Print out the number of connected graphs for n = 1 to 10
  for (int i = 1; i <= 10; ++i)
  {
    cout << "The number of connected graphs with " << i << " vertices is " << c[i] << endl;
  }

  // Total graphs with 10 vertices
  long result = 1L << 45;

  // Subtract cases when the graph has two components, one of size i, the other of size 10-i
  for (int i = 1; i <= 4; ++i)
  {
    result -= choose(10, i) * c[i] * c[10 - i];
  }

  // Subtract the case when the graph has two size 5 components, special treatment
  result -= choose(10, 5) * (c[5] * c[5]) / 2;

  // Subtract the case when the graph has one component
  result -= c[10];

  cout << "There are " << result
       << " simple undirected graphs G = (V, E) where the vertices have at least 3 connected components." << endl;

  return 0;
}
