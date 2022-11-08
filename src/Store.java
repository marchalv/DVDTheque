import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Store {
    private ArrayList<Client> listClient = new ArrayList<Client>();
    private ArrayList<Film> listFilm = new ArrayList<Film>();
    private ArrayList<SupportBurned> listSupportBurned = new ArrayList<SupportBurned>();
    private ArrayList<Location> listLocation = new ArrayList<Location>();
    private Client currentClient;

    public Store(){

    }

    //-----------------------------LIST INIT------------------------------------------------
    public void loadData(){
        initListClient();
        initListFilm();
        initListSupportBurned();
        initListLocation();
    }

    public void initListClient(){
        listClient.add(new Client("harun", "rashid", 999, new Wallet(50)));
        listClient.add(new Client("val", "marchal", 998, new Wallet(0)));
        listClient.add(new Client("ichem", "mous", 997, new Wallet(6)));
    }

    public void initListFilm() {
        listFilm.add(new Film("Avatar",2013,180,"SF",5));
        listFilm.add(new Film("Insidious",2014,170,"Horreur",4));
        listFilm.add(new Film("Gladiator",2000,125,"Action",5));
        listFilm.add(new Film("Nemo",2004,170,"Enfance",4));
    }

    public void initListSupportBurned() {
        listSupportBurned.add(new SupportBurned(listFilm.get(0), "Blueray", true));
        listSupportBurned.add(new SupportBurned(listFilm.get(0), "Blueray", false));
        listSupportBurned.add(new SupportBurned(listFilm.get(0), "DVD", true));
        listSupportBurned.add(new SupportBurned(listFilm.get(1), "DVD", true));
        listSupportBurned.add(new SupportBurned(listFilm.get(3), "K7", true));
    }

    public void initListLocation() {
        //DateLocation = 24 OCT 22
        listLocation.add(new Location(listSupportBurned.get(1), listClient.get(0),1,1666605600000l , 2000, false));
    }

    // ----------------------DISPLAY LIST------------------------------------
    protected void displayFilms() {
        System.out.println(listFilm.toString());
    }

    protected void displayClientList() { System.out.println(listClient.toString()); }
    protected void displayListSupportGrave() {
        for (int i = 0; i < listSupportBurned.size(); i++) {
            if (listSupportBurned.get(i).isAvailable()) {
                System.out.println(listSupportBurned.get(i).toStringSupportGrave());
            }
        }
    }

    protected void displayLocations() {
        for (int i = 0; i < listLocation.size(); i++) {
            System.out.println(listLocation.get(i).toStringLocation());
        }
    }

    //-----------------------FILM CHOICE--------------------------------------
    public SupportBurned askFilm(){
        String supportChose = "";
        System.out.println("Please choose your film");
        Scanner scanner = new Scanner(System.in);
        String filmChose = scanner.nextLine();
        int cpt = -1;
        //verify if nClientCurrent is valid
        for (int i = 0; i < listFilm.size(); i++) {
            if(filmChose.equalsIgnoreCase(listFilm.get(i).getNom())){
                cpt = i;
            }
        }
        if(cpt!=-1){
            //ask support
            System.out.println("Please choose your support (Blueray / DVD / K7)");
            Scanner support = new Scanner(System.in);

            //mieux utiliser line ou int?
            //tapez (1) pour blueray etc etc...

            supportChose = support.nextLine();
            if (supportChose.equals("Blueray") || supportChose.equals("DVD") || supportChose.equals("K7")) {
                System.out.println("You chose " + listFilm.get(cpt) + " on " + supportChose);
            } else {
                System.out.println("This support doesn't exist");
                askFilm();

            }
        }else{
            System.out.println("Error, film doesn't exist");
            askFilm();
        }
        return new SupportBurned(listFilm.get(cpt), supportChose, true);
    }

    public boolean isSupportGraveAvailable(SupportBurned supportBurned) {
        int cpt = 0;
        for (int i = 0; i < listSupportBurned.size(); i++) {
            if (listSupportBurned.get(i).getFilm().equals(supportBurned.getFilm()) && listSupportBurned.get(i).getSupport().equals(supportBurned.getSupport()) && listSupportBurned.get(i).isAvailable()) {
                cpt = 1;
            }
        }
        if (cpt == 1) {
            return true;
        } else {
            return false;
        }
    }

    protected void rent() {
        double tarif = 0;
        int cpt = -1;
        int year = Calendar.getInstance().get(Calendar.YEAR);

        SupportBurned wantedSupportBurned = askFilm();
        if (isSupportGraveAvailable(wantedSupportBurned)) {
            System.out.println("Film available");
            for (int i = 0; i < listSupportBurned.size(); i++) {
                if (listSupportBurned.get(i).getFilm().equals(wantedSupportBurned.getFilm()) && listSupportBurned.get(i).getSupport().equals(wantedSupportBurned.getSupport()) && listSupportBurned.get(i).isAvailable()) {
                    cpt = i;
                }
            }
            //tarification
            if (listSupportBurned.get(cpt).getSupport().equals("Blueray")) {
                tarif++;
            }
            if (listSupportBurned.get(cpt).getFilm().getAnneeDeSortie() == year) {
                tarif+=2;
            } else if (listSupportBurned.get(cpt).getFilm().getAnneeDeSortie() == year-1) {
                tarif+=1;
            }
            System.out.println("Choose location duration in days (max. 3)");
            Scanner duration = new Scanner(System.in);
            int locationTime = duration.nextInt();
            tarif = tarif + locationTime * 2;
            System.out.println("You have to pay " + tarif + "$");
            chooseMeanOfPayment(tarif);
            Location location = new Location();
            location.setIdLocation(location.getIdLocation()+1);
            listLocation.add(new Location(listSupportBurned.get(cpt), currentClient, locationTime, System.currentTimeMillis(), location.getIdLocation(), false));
            listSupportBurned.get(cpt).setAvailable(false);
        } else {
            System.out.println("Film or support not available");
            rent();
        }
    }

    protected void chooseMeanOfPayment(double prix) {
        System.out.println("\n****************" +
                "\nChoose your mean of payment " +
                "\n 1) CB " +
                "\n 2) Check " +
                "\n 3) Cash " +
                "\n 4) Wallet");
        Scanner payment = new Scanner(System.in);
        int mpayment = payment.nextInt();
        switch (mpayment) {
            case 1:
                BankCard bankCard = new BankCard();
                bankCard.pay();
                break;
            case 2:
                BankCheck bankCheck = new BankCheck();
                bankCheck.pay();
                break;
            case 3:
                Cash cash = new Cash();
                cash.pay();
                break;
            case 4:
                int line = getClientLine();
                listClient.get(line).getWallet().setAmount(listClient.get(line).getWallet().getAmount()-prix) ;
                break;
        }
    }

    //------------------------------------CLIENT------------------------------------

    public void askIfClient(){
        //System.out.println(listClient);
        System.out.println("Do you have an account ? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        //System.out.println(answer);
        if (answer.equalsIgnoreCase("n")) {
            listClient.add(new Client().createAccount());
            asknClient();
        }
        else if(answer.equalsIgnoreCase("y")) {
            asknClient();

        }else{
            System.out.println("please type Y/N");
            askIfClient();
        }
    }

    private void asknClient() {
        System.out.println("Please enter your client no.");
        Scanner scanner = new Scanner(System.in);
        int nClientCurrent = scanner.nextInt();
        int cpt = -1;
        //verify if nClientCurrent is valid
        for (int i = 0; i < listClient.size(); i++) {
            if(nClientCurrent==listClient.get(i).getnClient()){
                cpt = i;
            }
        }
        if(cpt!=-1){
            System.out.println("Bonjour " + listClient.get(cpt).getFirstName() + " " + listClient.get(cpt).getLastName());
            currentClient = listClient.get(cpt);
        }else{
            System.out.println("error, client doesn't exist");
            askIfClient();
        }
    }

    private int getClientLine() {
        int line = -1;
        for (int i = 0; i < listClient.size(); i++) {
            if (currentClient.getnClient()==listClient.get(i).getnClient()) {
                line = i;
            }
        }
        return line;
    }


    //------------------------------------GETTER SETTER------------------------------------------------


    public void returnFilm(){
        System.out.println("Please enter your Location ID");
        Scanner scanner = new Scanner(System.in);
        int nLoc = scanner.nextInt();
        int cpt = -1;
        for (int i = 0; i < listLocation.size(); i++) {
            if(listLocation.get(i).getIdLocation() == nLoc){
                cpt = i;
            }
        }
        if(cpt == -1){
            returnFilm();
            return;
        }
        long date1 = listLocation.get(cpt).getDateLocation();
        long date2 = System.currentTimeMillis();
        long dateLimit = listLocation.get(cpt).getDateLocation() + listLocation.get(cpt).getDureeLocation()*3600000*24;
        int deltaLoc = (int)(date2 - dateLimit)/3600000;
        if(deltaLoc > 0){
            double penalty = deltaLoc*0.1;
            System.out.println("You have surpassed the limit date. You have to pay "+ penalty +"$");
            chooseMeanOfPayment(penalty);
        }
        listLocation.get(cpt).getSupportBurned().setAvailable(true);
        listLocation.get(cpt).setLocationFinished(true);
        System.out.println("Return OK");
    }
}
