import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class testSort{
    
    public static int takeInput() {
        int num = -1;
        boolean haveNum = false;
        Scanner scanner = new Scanner(System.in);

        while (!haveNum) {
            System.out.println("Please enter a size of array: ");
            if (scanner.hasNextInt()) {
                //Read integer 
                num = scanner.nextInt();
                haveNum = true;
            } else {
                // If the input is not an integer, handle the error
                System.out.println("Invalid input. Please enter a valid integer.");
            }
                
            if (num < 0) {
                haveNum = false;
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
        scanner.close();
        return num;
    } //takeInput

    public static Integer[] fillArr(int size) { //fill array with random numbers
        Random random = new Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) 
            arr[i] = random.nextInt(size);
        return arr;
    } //fillArr

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = -1;
        Integer[] arr;


        //take input 
        if (args.length > 0) {
            try {
                //try convert string to int 
                size = Integer.parseInt(args[0]);
                if (size < 0) 
                    size = takeInput();
            } catch (NumberFormatException e) {
                System.out.println("Error: The input is not a valid integer.");
            }
        } else {
            size = takeInput();
        }

        arr = fillArr(size);
        // int start = 0;
        // int end = arr.length - 1;
        // int mid = (start + end) >>> 1;
        // Integer[] leftArr = Arrays.copyOfRange(arr,0,mid+1);
        // Integer[] rightArr = Arrays.copyOfRange(arr,mid+1,end+1);

        // System.out.println("Random array: ");
        // for (int element : arr) {
        //     System.out.println(element);
        // }

        // System.out.println("left array: ");
        // for (int element : leftArr) {
        //     System.out.println(element);
        // }
        
        // System.out.println("right array: ");
        // for (int element : rightArr) {
        //     System.out.println(element);
        // }


        long startTime = System.currentTimeMillis();
        Sort.quickSort(arr);
        long endTime = System.currentTimeMillis();

        // System.out.println("Sorted array: ");
        // for (int element : arr) {
        //     System.out.println(element);
        // }

        long sortTime = endTime - startTime;
        System.out.println("Sequentil Sorting time: " + sortTime + " milliseconds");




        arr = fillArr(size);

        // System.out.println("Random array: ");
        // for (int element : arr) {
        //     System.out.println(element);
        // }

        startTime = System.currentTimeMillis();
        ParallelSort.quickSort(arr);
        endTime = System.currentTimeMillis();

        // System.out.println("Sorted array: ");
        // for (int element : arr) {
        //     System.out.println(element);
        // }

        sortTime = endTime - startTime;
        System.out.println("Parallel Sorting time: " + sortTime + " milliseconds");

    }
}