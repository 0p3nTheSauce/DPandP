#include <omp.h>
#include <stdio.h>

int main()
{
    const int N = 100000;
    //const int N = 10;
    int i, a[N];
    //#pragma omp parallel for //divides iterations between threads 
    #pragma omp for schedule(static) //defualt, each thread allocated section of for loop
    //#pragma omp for schedule (dynamic, 3)
    for (i = 0; i < N; i++)
        a[i] = 2 * i;
    for (i = 0; i < N; i++)
        printf("a[%d]: %d\n", i, a[i]);
    return 0;
} // main