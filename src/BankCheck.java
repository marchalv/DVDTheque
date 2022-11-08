public class BankCheck implements MeansOfPayment{
    @Override
    public void pay() {
        System.out.println("Payment with check OK");
    }
}
