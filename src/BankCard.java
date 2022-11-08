public class BankCard implements MeansOfPayment{

    @Override
    public void pay() {
        System.out.println("Payement with card OK");
    }
}
