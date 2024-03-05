public class Main {
    public static void main(String[] args) {
        //boolean pleaseWait = false;
        int mealCounter = 0;
        Guest g = new Guest(mealCounter);
        Waiter w = new Waiter(g);

        w.start();

    }
}
