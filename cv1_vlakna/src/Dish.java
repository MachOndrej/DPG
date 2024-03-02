public class Dish extends Thread{
    int dish_number = 0;

    synchronized void serveDish() {
        try {
            this.dish_number += 1;
            if (this.dish_number == 1) {
                System.out.println("polevka");
            } else if (this.dish_number == 2) {
                System.out.println("hlavni jidlo");
            } else {
                System.out.println("dezert");
            }
        }
        catch (Exception ignored) {

        }
    }
}
