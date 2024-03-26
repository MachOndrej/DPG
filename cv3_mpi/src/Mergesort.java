import java.util.Random;
import mpi.*;

public class Mergesort {
    int world_rank;
    int world_size;
    final int N = 10000000;
    int[] original_array;

    Mergesort(String[] args) {
        original_array = new int[N];
        /* generovani vstupniho pole */
        Random rnd = new Random();
        for (int i = 0; i < N; i++) {
            original_array[i] = rnd.nextInt(N + 30);
        }
        /* init MPI */
        MPI.Init(args);
        world_rank = MPI.COMM_WORLD.Rank();
        world_size = MPI.COMM_WORLD.Size();
    }

    public static void main(String[] args) {
        Mergesort m = new Mergesort(args);
        long duration = m.sort();
        if (m.world_rank == 0) {
            System.out.println("Paralell ms: " + duration);
        }
        /* finalizace MPI */
        MPI.COMM_WORLD.Barrier();
        MPI.Finalize();
    }


    public long sort() {
//        System.out.println("CURR: " + Runtime.getRuntime().totalMemory()/1024/1024);
//        System.out.println("MAX: " + Runtime.getRuntime().maxMemory()/1024/1024);
        long duration = 0;
        long start  = System.currentTimeMillis();
        /* velikost poli pro procesy */
        int size = N / world_size;

        /* odeslat kazde sub pole kazdemu procesu */
        int[] sub_array = new int[size];
        MPI.COMM_WORLD.Scatter(original_array, 0, size, MPI.INT, sub_array, 0, size, MPI.INT, 0);

        /* spustit mergesort na kazdem procesu */
        int[] tmp_array = new int[size];

        Utils ms = new Utils();

        ms.mergeSort(sub_array, tmp_array, 0, size - 1);

        /* sber  a spojeni sub polí do jednoho */
        int[] sorted = new int[N];
        if (world_rank == 0) {
            sorted = new int[N];
        }
        MPI.COMM_WORLD.Gather(sub_array, 0, size, MPI.INT, sorted, 0, size, MPI.INT, 0);

        /* zaverecny mergesort na root node a vypis */
        if (world_rank == 0) {
            int[] other_array = new int[N];
            ms.mergeSort(sorted, other_array, 0, N - 1);
            long stop  = System.currentTimeMillis();
            duration = stop - start;
//            System.out.println(Arrays.toString(sorted));
        }

        return duration;
    }
}
