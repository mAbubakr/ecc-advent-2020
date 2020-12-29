#include <stdio.h>
#include <string.h>

typedef unsigned int uint32_t;
typedef unsigned long uint64_t;

uint32_t cups[1000000];

int main() {
  uint32_t i;
  for (i = 0; i < 1000000; i++)
    cups[i] = i + 1;
  cups[0] = 3;
  cups[1] = 2;
  cups[2] = 6;
  cups[3] = 5;
  cups[4] = 1;
  cups[5] = 9;
  cups[6] = 4;
  cups[7] = 7;
  cups[8] = 8;

  for (int x = 0; x < 10000000; x++) {
    if (x % 10000 == 0)
      printf("%d\n", x);
    uint32_t n = cups[0];
    uint32_t n1 = cups[1];
    uint32_t n2 = cups[2];
    uint32_t n3 = cups[3];
    uint32_t t = n - 1;
    while (1) {
      if (t == 0)
        t = 1000000;
      if ((t != n1) && (t != n2) && (t != n3))
        break;
      t--;
    }
    for (i = 4; i < 1000000; i++)
      if (cups[i] == t)
        break;
    memmove(&cups[0], &cups[4], i * 4);
    cups[i-3] = n1;
    cups[i-2] = n2;
    cups[i-1] = n3;
    memmove(&cups[i], &cups[i+1], (999999 - i) * 4);
    cups[999999] = n;
  }
  for (i = 0; i < 1000000; i++)
    if (cups[i] == 1)
      break;
  printf("%d: %d %d\n", i, cups[(i+1) % 1000000], cups[(i+2) % 1000000]);
  uint64_t res = (uint64_t)(cups[(i+1) % 1000000]) * (uint64_t)(cups[(i+2) % 1000000]);
  printf("%ld\n", res);
}

