#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

void childProcess() {
    printf("Hello from child process!\n");
}

void parentProcess(pid_t childPid) {
    printf("Hello from parent process!\n");

    //wait for child to finish
    int status;
    waitpid(childPid, &status, 0);

    printf("Child process exited with status %d\n", WEXITSTATUS(status));
}


int main() {
    pid_t pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        return 1; 
    } else if (pid == 0) {
        //this code is executed by the child process 
        childProcess();
        _exit(0);
    } else {
        //This code is executed by the parent process
        parentProcess(pid);
    }


    return 0;
    

}