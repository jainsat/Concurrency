import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MergeSortUsingForkJoin {

    public static void main(String args[]) {
        int arr[] = {2,4,3,1,5,3,4,9};
        new MergeSortUsingForkJoin().mergeSort(arr);
        for (int j : arr) {
            System.out.print(j + " ");
        }
    }
    void mergeSort(int arr[]) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        SortTask task = new SortTask(arr, 0, arr.length-1);
        pool.submit(task);
        task.join();
    }

    class SortTask extends RecursiveAction {

        int arr[];
        int low, high;

        SortTask(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            System.out.println(Thread.currentThread().getName() + " " +low + " " + high);
            if ((high - low + 1) <= 1) {
                return;
            }
            int mid = (low+high)/2;
            SortTask left = new SortTask(arr, low, mid);
            SortTask right = new SortTask(arr,mid+1, high);
            left.fork();
            right.compute();
            left.join();
            merge(arr, low, high);
//            System.out.println(low + " -> " + high);
//            for (int i = low; i <= high; i++) {
//                System.out.print(arr[i] + " ");
//            }
            //System.out.println();
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
            for ( i = low;i <=high; i++) {
                arr[i] = aux[i-low];
            }
        }
    }
}
