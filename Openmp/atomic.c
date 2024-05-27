#include <omp.h>
#include <stdio.h>
#define MAX 100
//bad solution use a reduction
int main()
{
    int a = 0;
    for (int k = 0; k < MAX; k++)
    {
        int c = k*k;
        #pragma omp atomic
        a += c;
        printf("a: %d\n", a);
    }
    printf("a: %d\n", a);
}
