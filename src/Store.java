import java.text.ParseException;
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
    private ArrayList<Reservation> listReservation = new ArrayList<Reservation>();
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
        listSupportBurned.add(new SupportBurned(listFilm.get(0), new Blueray(), true));
        listSupportBurned.add(new SupportBurned(listFilm.get(0), new Blueray(), false));
        listSupportBurned.add(new SupportBurned(listFilm.get(0), new DVD(), true));
        listSupportBurned.add(new SupportBurned(listFilm.get(1), new DVD(), true));
        listSupportBurned.add(new SupportBurned(listFilm.get(3), new K7(), true));
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
        boolean again = true;
        String stringSupportChose = "";
        Support supportChose = null;
        int cpt = -1;
        while (again != false) {
            again = false;
            System.out.println("Please choose your film");
            Scanner scanner = new Scanner(System.in);
            String filmChose = scanner.nextLine();

            //verify if nClientCurrent is valid
            for (int i = 0; i < listFilm.size(); i++) {
                if(filmChose.equalsIgnoreCase(listFilm.get(i).getNom())){
                    cpt = i;
                }
            }
            if (cpt != -1) {
                //ask support
                System.out.println("Please choose your support (Blueray / DVD / K7)");
                Scanner support = new Scanner(System.in);

                //mieux utiliser line ou int?
                //tapez (1) pour blueray etc etc...

                stringSupportChose = support.nextLine();
                if (stringSupportChose.equalsIgnoreCase("Blueray")) {
                    supportChose = new Blueray();
                } else if (stringSupportChose.equalsIgnoreCase("DVD")) {
                    supportChose = new DVD();
                } else if (stringSupportChose.equalsIgnoreCase("K7")) {
                    supportChose = new K7();
                } else {
                    System.out.println("This support doesn't exist");
                    again = true;
                }

            } else {
                System.out.println("Error, film doesn't exist");
                again = true;
            }
        }
        return new SupportBurned(listFilm.get(cpt), supportChose, true);
    }

    public boolean isSupportGraveAvailable(SupportBurned supportBurned) {
        int cpt = 0;
        for (int i = 0; i < listSupportBurned.size(); i++) {
            if (listSupportBurned.get(i).getFilm().equals(supportBurned.getFilm()) && listSupportBurned.get(i).getSupport().getClass().equals(supportBurned.getSupport().getClass()) && listSupportBurned.get(i).isAvailable()) {
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
        boolean again = true;
        double tarif = 0;
        int cpt = -1;
        int year = Calendar.getInstance().get(Calendar.YEAR);

        SupportBurned wantedSupportBurned = askFilm();
        while (again != false) {
            again = false;
            if (isSupportGraveAvailable(wantedSupportBurned)) {
                System.out.println("Film available");
                for (int i = 0; i < listSupportBurned.size(); i++) {
                    if (listSupportBurned.get(i).getFilm().equals(wantedSupportBurned.getFilm()) && listSupportBurned.get(i).getSupport().getClass().equals(wantedSupportBurned.getSupport().getClass()) && listSupportBurned.get(i).isAvailable()) {
                        cpt = i;
                    }
                }
                //System.out.println(cpt);
                //tarification
                if (listSupportBurned.get(cpt).getSupport().equals("Blueray")) {
                    tarif++;
                }
                if (listSupportBurned.get(cpt).getFilm().getAnneeDeSortie() == year) {
                    tarif += 2;
                } else if (listSupportBurned.get(cpt).getFilm().getAnneeDeSortie() == year - 1) {
                    tarif += 1;
                }
                System.out.println("Choose location duration in days (max. 3)");
                Scanner duration = new Scanner(System.in);
                int locationTime = duration.nextInt();
                tarif = tarif + locationTime * 2;
                System.out.println("You have to pay " + tarif + "$");
                chooseMeanOfPayment(tarif);
                Location location = new Location();
                location.setIdLocation(location.getIdLocation() + 1);
                listLocation.add(new Location(listSupportBurned.get(cpt), currentClient, locationTime, System.currentTimeMillis(), location.getIdLocation(), false));
                listSupportBurned.get(cpt).setAvailable(false);
                System.out.println("Location ID : " + location.getIdLocation());
            } else {
                System.out.println("Film or support not available");
                again = true;
            }
        }
    }

    protected void chooseMeanOfPayment(double prix) {
        boolean again = true;
        while (again != false) {
            again = false;
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
                    listClient.get(line).getWallet().setAmount(listClient.get(line).getWallet().getAmount() - prix);
                    System.out.println("You have now $" + listClient.get(line).getWallet().getAmount() + " on your wallet");
                    break;
                default:
                    again = true;
            }
        }
    }

    //------------------------------------CLIENT------------------------------------

    public void askIfClient(){
        boolean again = true;
        while (again != false) {
            again = false;
            //System.out.println(listClient);
            System.out.println("Do you have an account ? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            //System.out.println(answer);
            if (answer.equalsIgnoreCase("n")) {
                listClient.add(new Client().createAccount());
                asknClient();
            } else if (answer.equalsIgnoreCase("y")) {
                asknClient();
            } else {
                System.out.println("please type Y/N");
                again = true;
            }
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
            if (currentClient.getnClient().equals(listClient.get(i).getnClient())) {
                line = i;
            }
        }
        return line;
    }

    // -------------------------------STATS-------------------------

    protected void clientStats() {
        asknClient();
        int nbDVD = 0;
        int nbBR = 0;
        int nbK7 = 0;
        int nbSF = 0;
        int nbHorreur = 0;
        int nbAction = 0;
        int nbEnfance = 0;
        for (int i = 0; i < listLocation.size(); i++) {
            if (listLocation.get(i).getClient().equals(currentClient)) {
                // denombrement par support
                if (listLocation.get(i).getSupportBurned().getSupport().getClass().equals(DVD.class)) {
                    nbDVD++;
                } else if (listLocation.get(i).getSupportBurned().getSupport().getClass().equals(Blueray.class)) {
                    nbBR++;
                } else if (listLocation.get(i).getSupportBurned().getSupport().getClass().equals(K7.class)) {
                    nbK7++;
                }
                // denombrement par genre
                if (listLocation.get(i).getSupportBurned().getFilm().getGenre().equalsIgnoreCase("sf")) {
                    nbSF++;
                } else if (listLocation.get(i).getSupportBurned().getFilm().getGenre().equalsIgnoreCase("horreur")) {
                    nbHorreur++;
                } else if (listLocation.get(i).getSupportBurned().getFilm().getGenre().equalsIgnoreCase("action")) {
                    nbAction++;
                } else if (listLocation.get(i).getSupportBurned().getFilm().getGenre().equalsIgnoreCase("enfance")) {
                    nbEnfance++;
                }
            }
        }
        System.out.println("DVD rent : " + nbDVD +
                "\nBlueray rent : " + nbBR +
                "\nK7 rent : " + nbK7);
        System.out.println("\nSF film rent : " + nbSF +
                "\nHorror film rent : " + nbHorreur +
                "\nAction film rent : " + nbAction +
                "\nEnfance film rent : " + nbEnfance);
    }


    //------------------------------------RESERVATION------------------------------------------------


    public void returnFilm(){
        boolean again = true;
        int cpt = -1;
        while (again != false) {
            again = false;
            System.out.println("Please enter your Location ID");
            Scanner scanner = new Scanner(System.in);
            int nLoc = scanner.nextInt();
            for (int i = 0; i < listLocation.size(); i++) {
                if (listLocation.get(i).getIdLocation() == nLoc) {
                    cpt = i;
                }
            }
            if (cpt == -1) {
                System.out.println("Location ID doesn't exist");
                again = true;
            }
        }
        long date2 = System.currentTimeMillis();
        long dateLimit = listLocation.get(cpt).getDateLocation() + listLocation.get(cpt).getDureeLocation()*3600000*24;
        int deltaLoc = (int)(date2 - dateLimit)/3600000;
        if(deltaLoc > 0){
            double penalty = deltaLoc*0.1;
            System.out.println("You have surpassed the limit date. You have to pay "+ penalty +"$");
            chooseMeanOfPayment(penalty);
        } else if (deltaLoc < 0){
            if (-48 > deltaLoc){
                currentClient.getWallet().setAmount(currentClient.getWallet().getAmount() + 4);
            } else if (-36 > deltaLoc && deltaLoc > -48){
                currentClient.getWallet().setAmount(currentClient.getWallet().getAmount() + 3);
            } else if (-24 > deltaLoc && deltaLoc > -36){
                currentClient.getWallet().setAmount(currentClient.getWallet().getAmount() + 2);
            } else if (-12 > deltaLoc && deltaLoc > -24){
                currentClient.getWallet().setAmount(currentClient.getWallet().getAmount() + 1);
            } else if (0 > deltaLoc && deltaLoc > -12){
                currentClient.getWallet().setAmount(currentClient.getWallet().getAmount() + 0.5);
            }
            System.out.println("You have returned the film on time. You have "+ currentClient.getWallet().getAmount() +"$ in your wallet");
        }
        listLocation.get(cpt).getSupportBurned().setAvailable(true);
        listLocation.get(cpt).setLocationFinished(true);
        System.out.println("Return OK");
    }

    public void reservationFilm() throws ParseException {
        SupportBurned choosenSupportBurned = askFilm();
        Film choosenFilm = choosenSupportBurned.getFilm();
        Support choosenSupport = choosenSupportBurned.getSupport();
        Scanner typedate = new Scanner(System.in);
        System.out.println("Enter the year reservation : ");
        int yearReservation = typedate.nextInt();
        System.out.println("Enter the month reservation : ");
        int monthReservation = typedate.nextInt();
        System.out.println("Enter the day reservation : ");
        int dayReservation = typedate.nextInt();
        System.out.println("Enter the hour reservation : ");
        int hourReservation = typedate.nextInt();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date_s = dayReservation + "/" + monthReservation + "/" + yearReservation + " " + hourReservation + ":00";
        Date dateChoosed = sdf.parse(date_s);
        System.out.println("You choosed to reserve the film on : " + dateChoosed.toString());
        System.out.println();
        System.out.println("Please choose a duration Reservation (between 1 and 3 days) : ");
        Scanner howLongTimeReservation = new Scanner(System.in);
        int durationReservation = howLongTimeReservation.nextInt();
        String date_end = dayReservation + durationReservation + "/" + monthReservation + "/" + yearReservation + " " + hourReservation + ":00";
        Date endDate = sdf.parse(date_end);
        System.out.println("You must to return your film at :" + endDate.toString());
        double tarifReservation = 0;
        tarifReservation += durationReservation * 2;
        System.out.println("You have to pay "+ tarifReservation +"$");
        chooseMeanOfPayment(tarifReservation);
        listReservation.add(new Reservation(currentClient, dateChoosed, choosenFilm, choosenSupport, endDate));
    }
}