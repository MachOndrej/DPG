import java.util.LinkedList;
import java.util.concurrent.Semaphore;

class Restaurace {
    static int guestNumber = 5;
    static int waitersNumber = 7;
    static int mealsNumber = 3;
    // Semaphores definition
    static Semaphore waiterSem = new Semaphore(waitersNumber);
    static Semaphore soupSem = new Semaphore(guestNumber);
    static Semaphore mainMealSem = new Semaphore(guestNumber);
    static Semaphore dessertSem = new Semaphore(guestNumber);
    // Queues
    static WorkQueue workQueue = new WorkQueue();
    static WorkQueue foodQueue = new WorkQueue();

    static class Waiter extends Thread {
        int id;

        Waiter(int id) {
            this.id = id;
        }

        public void run() {
            try {
                if (soupSem.availablePermits() != 0) {
                    soupSem.acquire(1);     // Reduce soup permit
                    Object guestId = workQueue.getWork();
                    String foodType = "SOUP";
                    foodQueue.addWork(foodType);
                    System.out.println("Waiter " + id + " getting " + foodType);
                    sleep(100);
                    System.out.println("Waiter " + id + " serving " + foodType + " to guest " + guestId);
                    sleep(100);
                    sleep(500);     // waiter resting
                    waiterSem.release(1);

                } else if (mainMealSem.availablePermits() != 0) {
                    mainMealSem.acquire(1);     // Reduce main meal permit
                    Object guestId = workQueue.getWork();
                    String foodType = "MAIN MEAL";
                    foodQueue.addWork(foodType);
                    System.out.println("Waiter " + id + " getting " + foodType);
                    sleep(100);
                    System.out.println("Waiter " + id + " serving " + foodType + " to guest " + guestId);
                    sleep(100);
                    sleep(500);     // waiter resting
                    waiterSem.release(1);

                } else {
                    dessertSem.acquire(1);     // Reduce dessert permit
                    Object guestId = workQueue.getWork();
                    String foodType = "DESSERT";
                    foodQueue.addWork(foodType);
                    System.out.println("Waiter " + id + " getting " + foodType);
                    sleep(100);
                    System.out.println("Waiter " + id + " serving " + foodType + " to guest " + guestId);
                    sleep(100);
                    sleep(500);     // waiter resting
                    waiterSem.release(1);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Guest extends Thread {
        int id;

        Guest(int id) {
            this.id = id;
        }

        public void run() {
            try {
                for (int i = 0; i < mealsNumber; i++) {
                    waiterSem.acquire();
                    workQueue.addWork(id);
                    Object currentFood = foodQueue.getWork();
                    System.out.println("Guest " + id + " is consuming " + currentFood);
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class WorkQueue {
        LinkedList queue = new LinkedList();

        public synchronized void addWork(Object o) {
            queue.addLast(o);
            notify();
        }

        public synchronized Object getWork() throws
                InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            return queue.removeFirst();
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < waitersNumber; i++) {
            new Restaurace.Waiter(i + 1).start(); // start waiters
        }
        for (int i = 0; i < guestNumber; i++) {
            new Restaurace.Guest(i + 1).start(); // start hosts
        }
    }
}
