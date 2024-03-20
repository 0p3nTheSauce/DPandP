#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string.h>

#define MAX_MSG_SIZE 1024

//Define a structure for the message
struct msgbuf {
    long mtype; //message type (must be > 0)
    char mtext[MAX_MSG_SIZE];
};

void childProcess(int msgid) {
    //printf("Hello from child process!\n");
    struct msgbuf message;

    // Prepare the message 
    message.mtype = 1; // Message type (must be > 0)
    strcpy(message.mtext, "Hello, Message Queue!");


    //send the message to the queue
    if (msgsnd(msgid, &message, sizeof(message.mtext), 1) == -1) {
        perror("msgsnd");
        exit(1);
    }

    printf("Child sent message to the queue.\n");
}

void parentProcess(pid_t childPid, int msgid) {
    //printf("Hello from parent process!\n");
    struct msgbuf message;

    //Recieve a message from the queue
    if (msgrcv(msgid, &message, sizeof(message.mtext), 1, 0) == -1) {
        perror("msgrcv");
        exit(1);
    }

    printf("parent recieved message from the queue: %s\n", message.mtext);

}


int main() {
    
    key_t key;
    int msgid;

    //Generate a unique key for the message queue
    if ((key = ftok(".", 'A')) == -1) {
        perror("ftok");
        exit(1);
    }

    //creat or open the message queue
    if ((msgid = msgget(key, IPC_CREAT | 0660)) == -1) {
        perror("msgget");
        exit(1);
    }

    printf("message queue created or opened with id %d.\n", msgid);

    pid_t pid = fork();

    if (pid < 0) {
        perror("fork failed");
        exit(1);
    } else if (pid == 0) {
        //this code is executed by the child process 
        childProcess(msgid);
        exit(0);
    } else {
        //This code is executed by the parent process
        parentProcess(pid, msgid);
        wait(NULL);

        //remove the message queue
        if (msgctl(msgid, IPC_RMID, NULL) == -1) {
            perror("msgctl");
            exit(1);
        }

        printf("Message queue removed.\n");
    }
    
    return 0;
}