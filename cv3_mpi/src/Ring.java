/*
 * Copyright (c) 2011      Cisco Systems, Inc.  All rights reserved.
 *
 * Simple ring test program
 */

import mpi.* ;

class Ring {
    static public void main(String[] args) throws MPIException {
        MPI.Init(args) ;

        int source;  // Rank of sender
        int dest;    // Rank of receiver
        int tag=50;  // Tag for messages
        int next;
        int prev;
        int[] message = new int [1];

        int myrank = MPI.COMM_WORLD.Rank() ;
        int size = MPI.COMM_WORLD.Size() ;

	/* vypocet ranků */

        next = (myrank + 1) % size;
        prev = (myrank + size - 1) % size;

	/* root node posila hodnotu dokola. */

        if (0 == myrank) {
            message[0] = 10;

            System.out.println("Process 0 sending " + message[0] + " to rank " + next + " (" + size + " processes in ring)");
            MPI.COMM_WORLD.Send(message, 0,1, MPI.INT, next, tag);
        }

    /*
     Odesila message dokola, na root node je dekrementovan.
     Jakmile proces dostane 0,preda ji dale a skonci
     */

        while (true) {
            MPI.COMM_WORLD.Recv(message,0, 1, MPI.INT, prev, tag);

            if (0 == myrank) {
                --message[0];
                System.out.println("Process 0 decremented value: " + message[0]);
            } else {
                System.out.println("Process " + myrank + " work with value: " + message[0] + " od " + prev);
            }

            MPI.COMM_WORLD.Send(message, 0,1, MPI.INT, next, tag);
            if (0 == message[0]) {
                System.out.println("Process " + myrank + " exiting");
                break;
            } else {
                System.out.println("Process " + myrank + " send value: " + message[0] + " pro " + next);
            }
        }

        if (0 == myrank) {
            MPI.COMM_WORLD.Recv(message,0, 1, MPI.INT, prev, tag);
        }

        MPI.Finalize();
    }
}