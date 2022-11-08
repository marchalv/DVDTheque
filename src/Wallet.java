public class Wallet implements MeansOfPayment{
    private double amount;

    public Wallet(double amount) { }
    @Override
    public void pay() {

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
