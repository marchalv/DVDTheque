import java.util.Date;

public class Reservation {
    private Client client;
    private Date dateReservation;
    private Film film;
    private Support support;

    protected Reservation(Client client, Date dateReservation, Film film, Support support) {
        this.client = client;
        this.dateReservation = dateReservation;
        this.film = film;
        this.support = support;
    }
}
