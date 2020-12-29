#include <stdio.h>
#include <string.h>

typedef unsigned int uint32_t;

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
  //389125467
  //cups[0] = 3;
  //cups[1] = 8;
  //cups[2] = 9;
  //cups[3] = 1;
  //cups[4] = 2;
  //cups[5] = 5;
  //cups[6] = 4;
  //cups[7] = 6;
  //cups[8] = 7;

  for (int x = 0; x < 10000000; x++) {
  //for (int x = 0; x < 100; x++) {
    if (x % 10000 == 0)
      printf("%d\n", x);
    uint32_t n = cups[0];
    uint32_t n1 = cups[1];
    uint32_t n2 = cups[2];
    uint32_t n3 = cups[3];
    //printf("%d: %d %d %d %d\n", x, n, n1, n2, n3);
    uint32_t t = n - 1;
    while (1) {
      if (t == 0)
        t = 1000000;
        //t = 9;
      if ((t != n1) && (t != n2) && (t != n3))
        break;
      t--;
    }
    for (i = 4; i < 1000000; i++)
      if (cups[i] == t)
        break;
    //printf("%d @ %d\n", t, i);
    memmove(&cups[0], &cups[4], i * 4);
    cups[i-3] = n1;
    cups[i-2] = n2;
    cups[i-1] = n3;
    memmove(&cups[i], &cups[i+1], (999999 - i) * 4);
    cups[999999] = n;
    //memmove(&cups[i], &cups[i+1], (8 - i) * 4);
    //cups[8] = n;
  }
  for (i = 0; i < 1000000; i++)
    if (cups[i] == 1)
      break;
  printf("%d: %d %d\n", i, cups[(i+1) % 1000000], cups[(i+2) % 1000000]);
  unsigned long res = cups[(i+1) % 1000000] * cups[(i+2) % 1000000];
  printf("%ld\n", res);
}

