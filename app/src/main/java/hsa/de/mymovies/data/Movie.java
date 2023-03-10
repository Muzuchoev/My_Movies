package hsa.de.mymovies.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Dieser Code definiert eine Java-Klasse namens Movie.
 * Die Klasse enthält private Mitgliedsvariablen, die verschiedene Eigenschaften eines Films darstellen,
 * z. B. id, ...
 */

@Entity(tableName = "movies")

public class Movie {
    @PrimaryKey
    private int id;
    private int voteCount;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String bigPosterPath;
    private String backdropPath;
    private double voteAverage;
    private String releaseDate;


    /* *Die Klasse Movie bietet auch eine Konstruktormethode, um diese Mitgliedsvariablen mit den übergebenen Argumenten zu initialisieren.
       -Darüber hinaus bietet die Klasse eine Getter- und eine Setter-Methode für jede der Mitgliedsvariablen.
       Diese Methoden werden verwendet, um auf den Wert der Mitgliedsvariablen außerhalb der Klasse zuzugreifen und ihn zu ändern.
     */
    public Movie(int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backdropPath, double voteAverage, String releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    /*Die Methode getBigPosterPath() gibt den Wert der Mitgliedsvariablen bigPosterPath zurück, und die Methode setBigPosterPath() setzt den Wert
      der Mitgliedsvariablen bigPosterPath.
      Diese Methoden ermöglichen es, den Wert der bigPosterPath-Mitgliedervariable außerhalb der Klasse abzurufen und zu setzen.
     */
    public String getBigPosterPath() {

        return bigPosterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }

    /**
     * Getter/Setter ( Damit wir die Klasse in der Datenbank benutzen können )
     */


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
