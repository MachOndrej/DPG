public class Dish{
    int dish_number = 0;

    public void chooseDish() {
        try {
            this.dish_number += 1;
            if (this.dish_number == 1) {
                System.out.println(" polevku");
            } else if (this.dish_number == 2) {
                System.out.println(" hlavni jidlo");
            } else {
                System.out.println(" dezert");
            }
        }
        catch (Exception ignored) {

        }
    }
}
