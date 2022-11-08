public class SupportBurned {
    private Film film;
    private Support support;
    private boolean isAvailable;

    public SupportBurned() {

    }


    public String toStringSupportGrave() {
        return film.getNom() + " on " + support; // affiche seulement les films dispo
    }

    public SupportBurned(Film film, Support support, boolean isAvailable) {
        this.film = film;
        this.support = support;
        this.isAvailable = isAvailable;
    }

    public Film getFilm() {
        return film;
    }

    public Support getSupport() {
        return support;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
