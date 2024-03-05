public class Guest extends Thread{
    boolean pleaseWait;
    private int mealCounter;

    public Guest(int mealCounter) {
        this.mealCounter = mealCounter;
    }

    public void run() {
        try {
            System.out.println("Guest eating: ");
            this.mealCounter += 1;
            if (this.mealCounter == 1) {
                System.out.println(" polevku");
            } else if (this.mealCounter == 2) {
                System.out.println(" hlavni jidlo");
            } else {
                System.out.println(" dezert");
            }
            pleaseWait = true;
            sleep(1000);
            pleaseWait = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
