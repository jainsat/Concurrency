public class MultiThreadedMergeSort {

    public static void main(String args[]) {
        int arr[] = {2,4,3,1,5,3,4,9};

        new MultiThreadedMergeSort().mergeSort(arr);
        for (int j : arr) {
            System.out.print(j + " ");
        }
    }
    void mergeSort(int arr[]) {
        mergeSort(arr, 0, arr.length-1);

    }

    void mergeSort(int arr[], int low, int high)  {
        if (low == high) {
            return;
        }
        int mid = (low+high)/2;
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                mergeSort(arr, low, mid);
            }
        });
        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                mergeSort(arr, mid+1, high);
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch(InterruptedException e) {

        }

        merge(arr, low, high);
    }

    void foo(int arr[], int low, int high) {
        if (low == high) {
            return;
        }
        int mid = (low+high)/2;
        foo(arr, low, mid);
        foo(arr, mid+1, high);
        merge(arr, low, high);
    }

     void merge(int arr[], int low, int high) {
        int aux[] = new int[high-low+1];
        int mid = (low+high)/2;
        int l = low, h = mid+1, i = 0;
        while(l <= mid && h <= high) {
            if (arr[l] < arr[h] ) {
                aux[i++] = arr[l++];
            } else {
                aux[i++] = arr[h++];
            }
        }
        while(l <= mid) {
            aux[i++] = arr[l++];
        }
        while(h <= high) {
            aux[i++] = arr[h++];
        }
       // System.out.println("Merging " + low + " " + high);
        for ( i =low;i <=high; i++) {
            arr[i] = aux[i-low];
            //System.out.print(arr[i]  + " ");

        }
        //System.out.println();
    }
}
