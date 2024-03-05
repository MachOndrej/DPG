public class Waiter extends Thread {
    private Guest guest;
    public Waiter(Guest guest) {
        this.guest = guest;
    }

    public void run() {
        // Code to be executed by the thread
        try {
                System.out.println("Waiter: Getting food");
                sleep(100);
                System.out.println("Waiter: Serving food");
                sleep(100);
                guest.start();
                System.out.println("Waiter: Resting");
                sleep(500);
                guest.join(); // Thread 1 waits for Thread 2 to finish
            for (int i = 1; i <= 2; i++) {
                run();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
