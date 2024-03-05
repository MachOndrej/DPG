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

        // Method to return to main code
        public boolean returnToMainCode() {
            // You can add any necessary operations here to return to the main code
            // For demonstration purposes, let's print a message indicating the waiter is returning to the main code
            System.out.println("Waiter " + id + " returning to main code...");
            boolean nextMeal = true;
            return nextMeal;
        }

        public void run() {
            try {
                for (int i = 0; i < mealsNumber; i++) {
                    if (soupSem.availablePermits() != 0) {
                        String foodType = "SOUP";
                        soupSem.acquire();     // Reduce soup permit
                        Object guestId = workQueue.getWork();
                        synchronized (guestId) {
                            foodQueue.addWork(foodType);
                            System.out.println("Waiter " + id + " getting " + foodType);
                            sleep(100);
                            System.out.println("Waiter " + id + " serving " + foodType + " to guest " + guestId);
                            sleep(100);
                            guestId.notify();
                        }
                        sleep(500);     // waiter resting
                        waiterSem.release();


                    } else if (mainMealSem.availablePermits() != 0) {
                        String foodType = "MAIN MEAL";
                        mainMealSem.acquire();     // Reduce main meal permit
                        Object guestId = workQueue.getWork();
                        synchronized (guestId) {
                            foodQueue.addWork(foodType);
                            System.out.println("Waiter " + id + " getting " + foodType);
                            sleep(100);
                            System.out.println("Waiter " + id + " serving " + foodType + " to guest " + guestId);
                            sleep(100);
                            guestId.notify();

                        }
                        sleep(500);     // waiter resting
                        waiterSem.release();

                    } else {
                        String foodType = "DESSERT";
                        dessertSem.acquire(1);     // Reduce dessert permit
                        Object guestId = workQueue.getWork();
                        synchronized (guestId) {
                            foodQueue.addWork(foodType);
                            System.out.println("Waiter " + id + " getting " + foodType);
                            sleep(100);
                            System.out.println("Waiter " + id + " serving " + foodType + " to guest " + guestId);
                            sleep(100);
                            guestId.notify();
                        }
                        sleep(500);     // waiter resting
                        waiterSem.release(1);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Guest extends Thread {
        final Integer id;

        Guest(int id) {
            this.id = id;
        }

        public void run() {
            try {
                for (int i = 0; i < mealsNumber; i++) {
                    waiterSem.acquire();
                    synchronized (id) {
                        workQueue.addWork(id);
                        id.wait();
                    }
                    Object currentFood = foodQueue.getWork();
                    System.out.println("Guest " + id + " is consuming " + currentFood);
                    Thread.sleep(1000);
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
