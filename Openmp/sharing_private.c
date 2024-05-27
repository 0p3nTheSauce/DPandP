#include <omp.h>
#include <stdio.h>
#define MAX 100
//bad solution use a reduction
int main()
{
    int a = 0;
    #pragma omp for private(a)
    for (int k = 0; k < MAX; k++)
    {
        int c = k*k;
        printf("a: %d\n", a);
        a += c;
    }
    printf("a: %d\n", a);
}
