class Customer{
    int bank = 10000;

    synchronized void withdraw(int amount){
        System.out.println("going to withdraw...");

        if(this.bank < amount){
            System.out.println("Less balance; waiting for deposit...");
            try{
                wait();
            }
            catch(Exception e){

            }
        }
        this.bank -= amount;
        System.out.println("withdraw completed...");
    }

    synchronized void deposit(int amount){
        System.out.println("going to deposit...");
        this.bank +=amount;
        System.out.println("deposit completed... ");
        notify();
    }
}    