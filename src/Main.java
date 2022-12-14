import java.text.ParseException;
import java.util.Scanner;

//Created by:
//AZEROT Andy
//MARCHAL Valentin
//MD SOFIAN Megat
//MOUS Ichem

public class Main {
    public static void main(String[] args) throws ParseException {
        new Main().run();
    }

    public void run() throws ParseException {
        Store store = new Store();

        //initialization
        store.loadData();

        //store.displayFilms();
        //store.displayClientList();
        //store.displayLocations();

        //start program
        store.askIfClient();
        store.displayClientList();


        boolean exit = false;
        while(exit != true){
            System.out.println("\n****************" +
                    "\nWelcome to the DVD Store! \nSelect an option below: " +
                    "\n\n 1) display inventory " +
                    "\n 2) display customer info " +
                    "\n 3) rent DVD " +
                    "\n 4) return " +
                    "\n 5) customer stats" +
                    "\n 6) reservation" +
                    "\n 7) quit "+
                    "\n****************" +
                    "\n Select an option above:");

            boolean again = true;
            while(again==true){
                again=false;

                Scanner scanner = new Scanner(System.in);
                int option = scanner.nextInt();

                switch (option){
                    case 1:
                        System.out.println("1) display inventory");
                        store.displayListSupportGrave();
                        break;
                    case 2:
                        System.out.println("2) display customer info");
                        store.displayClientList();
                        break;
                    case 3:
                        System.out.println("3) rent DVD");
                        store.rent();
                        break;
                    case 4:
                        System.out.println("4) return DVD");
                        store.returnFilm();
                        break;
                    case 5:
                        store.clientStats();
                        break;
                    case 6:
                        store.reservationFilm();
                        break;
                    case 7:
                        System.out.println("\n Are you sure you want to quit? (Y/N) - all your data will be lost.");
                        scanner.nextLine(); //skip the newline
                        String checkExit = scanner.nextLine();
                        if (checkExit.equalsIgnoreCase("Y") ) {
                            System.out.println("Bye~");
                            exit = true;
                        }
                        break;
                    default:
                        System.out.println("Please try again");
                        again=true;
                }
            }
        }
    }
}

