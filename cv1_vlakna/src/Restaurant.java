import java.util.concurrent.Semaphore;

class Restaurant {
    static Semaphore kitchen = new Semaphore(3);
    static Semaphore waiter = new Semaphore(5);
    static Semaphore order = new Semaphore(0);

    static class Host extends Thread {
        int id;
        Host(int id) {
            this.id = id;
        }
        public void run() {
            try {
                for (int i = 0; i < 3; i++) {
                    waiter.acquire(); // wait for waiter
                    String mealName = "";
                    if (i == 0) {
                        mealName = "polevku";
                    } else if (i == 1) {
                        mealName = "hlavni jidlo";
                    } else if (i == 2) {
                        mealName = "dezert";
                    }
                    //System.out.println("Host " + id + " is eating course " + (i+1));
                    System.out.println("Host " + id + " is eating course " + mealName);

                    Thread.sleep(1000); // eating time
                    order.release(); // notify waiter about completion
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Waiter extends Thread {
        int id;
        Waiter(int id) {
            this.id = id;
        }
        public void run() {
            try {
                for (int i = 0; i < 15; i++) {
                    kitchen.acquire(); // get access to kitchen
                    System.out.println("Waiter " + id + " serving course " + (i % 3 + 1));
                    Thread.sleep(100); // serving time
                    kitchen.release(); // release kitchen access
                    order.acquire(); // wait for host to finish eating
                    System.out.println("Host " + (i % 5 + 1) + " finished eating course " + (i % 3 + 1));
                    waiter.release(); // notify host about completion
                    System.out.println("Waiter " + id + " resting");
                    Thread.sleep(500); // resting time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            new Waiter(i + 1).start(); // start waiters
        }
        for (int i = 0; i < 5; i++) {
            new Host(i + 1).start(); // start hosts
        }
    }
}

