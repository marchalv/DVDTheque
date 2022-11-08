import java.util.*;

public class Film {


    private String nom;
    private Integer anneeDeSortie;
    private Integer duree;
    private String genre;
    private Integer note;

    private List<Support> supports;

    public Film(){

    }
    public Film(String nom, Integer anneeDeSortie, Integer duree, String genre, Integer note) {
        this.nom = nom;
        this.anneeDeSortie = anneeDeSortie;
        this.duree = duree;
        this.genre = genre;
        this.note = note;
    }




    public String getNom() {
        return nom;
    }

    public int getAnneeDeSortie() {
        return anneeDeSortie;
    }



    @Override
    public String toString() {
        return nom + ", " + anneeDeSortie + ", " + duree +" minutes , " + genre + ", " + note +"/5" + "\n";
    }
}
