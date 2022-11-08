public class Cash implements MeansOfPayment{
    @Override
    public void pay() {
        System.out.println("Payment with cash OK");
    }
}
