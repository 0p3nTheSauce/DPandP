#include <omp.h>
#include <stdio.h>
#define MAX 100

int main()
{
    int a = 0;
    #pragma omp parallel for reduction(+:a)
    for (int k = 0; k < MAX; k++)
    {
        int c = k*k;
        a += c;
    }
    printf("a: %d\n", a);
}
