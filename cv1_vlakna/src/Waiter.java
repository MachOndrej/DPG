public class Waiter extends Thread {
    Dish d = new Dish();

    public void run() {
        try {
            while (d.dish_number <= 2) {
                d.serveDish();
                sleep(2000);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
