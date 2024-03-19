import mpi.* ;
class Main {
    static public void main(String[] args) throws MPIException {
        MPI.Init(args) ; // inicializace MPI
        int source; // ID idesilatele
        int dest; // ID prijemce
        int tag=50; // ID zpravy
        int myrank = MPI.COMM_WORLD.Rank() ; // ID aktualniho procesu v komunikatoru
        int p = MPI.COMM_WORLD.Size() ; // celk. pocet procesu v komunikatoru
        if(myrank != 0) { // jiny proces nez master node
            dest=0; // cilem je master node
            String myhost = MPI.Get_processor_name(); // jmeno aktualniho nodu
            char [] message = ("Zdravi vas proces " + myrank+" na "+myhost).toCharArray() ; //odesilana zprava
            MPI.COMM_WORLD.Send(message, 0, message.length, MPI.CHAR,dest, tag); //odeslani zpravy
        } else { // master node
            for (source = 1; source < p; source++ ) { // iterace pres ostatni nody
                char [] message = new char [60] ; // buffer pro prijem zpravy
                // prijem zpravy
                Status s = MPI.COMM_WORLD.Recv(message, 0, 60, MPI.CHAR, MPI.ANY_SOURCE, tag) ;
                int nrecv = s.Get_count(MPI.CHAR); // kolik znaku prislo ve zprave
                String s1 = new String(message); // prevod na String
                System.out.println("received: " + s1.substring(0,nrecv) + " : ") ; // vypis prijate zpravy
            }
        }
        MPI.Finalize(); // ukonceni MPI
    }
}
