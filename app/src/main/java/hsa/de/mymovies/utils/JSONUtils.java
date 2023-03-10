package hsa.de.mymovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hsa.de.mymovies.data.Movie;

    /**
     *  Dieser Code definiert eine Dienstleistungsklasse namens JSONUtils, die eine einzige statische Methode getMoviesFromJSON enthält,
     *  die ein JSONObject als Eingabe annimmt und eine ArrayList von Movie-Objekten zurückgibt.
     */

    /*Die Klasse JSONUtils verfügt über mehrere private statische endgültige String-Konstanten, die Schlüssel darstellen,
    die in einer JSON-Antwort von einer API verwendet werden.

    Diese Schlüssel umfassen KEY_RESULTS..
    Diese Konstanten werden für den Zugriff auf die entsprechenden Werte im JSON-Objekt verwendet.*/
    public class JSONUtils {

    private static final String KEY_RESULTS = "results";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    /*Die Klasse verfügt auch über drei öffentliche statische endgültige String-Konstanten,
    die die Basis-URL und verschiedene Größen von Filmpostern für eine Film-API darstellen.

    Diese Konstanten werden verwendet, um die vollständige Poster-URL zu erstellen.*/
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";


    /*Die Methode getMoviesFromJSON erstellt zunächst eine leere ArrayList von Filmobjekten namens result.
    - Wenn das eingegebene JSONObject null ist, gibt diese Methode einfach die Ergebnisliste zurück.

    - Andernfalls versucht die Methode, mit Hilfe der Konstante KEY_RESULTS das JSONArray der Filme aus dem JSONObject zu extrahieren.
      Sie durchläuft dann jedes Element im JSONArray und erstellt für jeden Film ein neues JSONObject.

    - Für jedes JSONObject eines Films extrahiert die Methode die Werte für jeden Schlüssel unter Verwendung der entsprechenden Konstanten.
      Sie erstellt die vollständige Poster-URL unter Verwendung der Basis-URL und einer der Konstanten für die Postergröße.
      Anschließend erstellt sie ein neues Movie-Objekt mit den extrahierten Werten und fügt es der Ergebnisliste hinzu.

    - Tritt beim Extrahieren der Filmdaten eine JSONException auf, gibt die Methode den Stack-Trace aus und liefert die Ergebnisliste.

    - Schließlich gibt die Methode die Ergebnisliste der Filmobjekte zurück.*/

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String backdrop_Path = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdrop_Path, voteAverage, releaseDate);
                result.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}






/**
 * Die Methode erstellt eine leere ArrayList<Movie> mit dem Namen "result".
 *
 * Überprüfung, ob das übergebene JSON-Objekt "null" ist. Wenn dies der Fall ist, wird die leere ArrayList<Movie> zurückgegeben.
 *
 * Die Methode versucht nun, die JSONArray mit dem Schlüssel "KEY_RESULTS" (ein Konstante String-Wert) aus dem JSON-Objekt zu extrahieren. Der JSONArray enthält eine Liste von JSONObject, die jeweils Informationen zu einem Film enthalten.
 *
 * Durchlaufen der JSONArray, um jedes JSONObject zu extrahieren. Für jedes JSONObject werden die folgenden Daten extrahiert:
 *
 * a. "id" (ein int-Wert) des Films
 * b. "vote_count" (ebenfalls ein int-Wert) des Films
 * c. "title" (ein String-Wert) des Films
 * d. "original_title" (ein String-Wert) des Films
 * e. "overview" (ein String-Wert) des Films
 * f. "poster_path" (ein String-Wert) des Films, der zur Basis-Poster-URL hinzugefügt wird, um das kleine Poster-Image-URL zu bilden
 * g. "poster_path" (ein String-Wert) des Films, der zur Basis-Poster-URL hinzugefügt wird, um die große Poster-Image-URL zu bilden
 * h. "backdrop_path" (ein String-Wert) des Films
 * i. "vote_average" (ein double-Wert) des Films
 * j. "release_date" (ein String-Wert) des Films
 *
 * Die oben genannten Werte werden in der Reihenfolge extrahiert, wie sie im Code aufgeführt sind.
 *
 * Ein neues Movie-Objekt wird erstellt, indem die extrahierten Werte als Argumente an den Konstruktor übergeben werden.
 *
 * Das Movie-Objekt wird der "result"-Liste hinzugefügt.
 *
 * Nachdem alle Filme aus der JSONArray extrahiert und zu "result" hinzugefügt wurden, wird "result" zurückgegeben.
 *
 * Wenn beim Extrahieren der JSON-Daten ein Fehler auftritt, wird eine JSONException ausgelöst, und die Methode gibt eine leere ArrayList<Movie> zurück.
 */