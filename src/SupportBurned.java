public class SupportBurned {
    private Film film;
    private String support;
    private boolean isAvailable;

    public SupportBurned() {

    }


    public String toStringSupportGrave() {
        return film.getNom() + " on " + support; // affiche seulement les films dispo
    }

    public SupportBurned(Film film, String support, boolean isAvailable) {
        this.film = film;
        this.support = support;
        this.isAvailable = isAvailable;
    }

    public Film getFilm() {
        return film;
    }

    public String getSupport() {
        return support;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
