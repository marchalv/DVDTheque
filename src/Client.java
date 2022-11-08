import java.util.Scanner;

public class Client {
    private String firstName;
    private String lastName;
    private int nClient = 1000;
    private Wallet wallet;

    public Client(String firstName, String lastName, Integer nClient, Wallet wallet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nClient = nClient;
        this.wallet = wallet;
    }

    public Client() {

    }

    public Client createAccount() {
        //ask first name
        System.out.println("Enter your first name :");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        //ask last name
        System.out.println("Enter your last name :");
        Scanner scanner2 = new Scanner(System.in);
        String lastName = scanner2.nextLine();
        nClient++;
        Client newClient = new Client(firstName, lastName, nClient, new Wallet(10));
        System.out.println("Your client number is " + newClient.getnClient() + ". Please remember this number.");
        return newClient;
    }




    @Override public String toString() {
        return firstName + " " + lastName + " #" + nClient;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getnClient() {
        return nClient;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
