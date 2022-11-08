import java.util.Date;

public class Location {
    private SupportBurned supportBurned;
    private Client client;
    private Integer dureeLocation;
    private long dateLocation;
    private Integer idLocation = 2000;
    private boolean isLocationFinished;

    public Location(){

    }


    public Location(SupportBurned supportBurned, Client client, Integer dureeLocation, long dateLocation, Integer idLocation, boolean isLocationFinished ) {
        this.supportBurned = supportBurned;
        this.client = client;
        this.dureeLocation = dureeLocation;
        this.dateLocation = dateLocation;
        this.idLocation = idLocation;
        this.isLocationFinished = isLocationFinished;
    }

    public String toStringLocation() {
        return supportBurned.getFilm().getNom() + " " + supportBurned.getSupport() + " " + client.getnClient() + " " + dureeLocation + "j " + dateLocation;
    }

    public Integer getIdLocation() {
        return idLocation;
    }
    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }
    public long getDateLocation() {
        return dateLocation;
    }
    public Integer getDureeLocation() {
        return dureeLocation;
    }
    public SupportBurned getSupportBurned() {
        return supportBurned;
    }

    public void setLocationFinished(boolean locationFinished) {
        isLocationFinished = locationFinished;
    }
}
