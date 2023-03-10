package hsa.de.mymovies.utils;


import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

    /**
     *
    * Dieser Code definiert eine Dienstleistungsklasse namens NetworkUtils, die Funktionen für die Interaktion mit einer API zum Abrufen von Informationen über Filme bereitstellt.
    * Die in diesem Code verwendete API ist die The Movie Database (TMDb) API, auf die über HTTP-Anfragen zugegriffen wird. Der Code besteht aus den folgenden Komponenten:
    */
    public class NetworkUtils {

    /* 	Definition von Konstanten:
      - Diese Konstanten werden in der gesamten Klasse verwendet, um die URL für die HTTP-Anfrage zu konstruieren und um verschiedene Parameter der Anfrage zu setzen.*/
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";


    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";

    private static final String API_KEY = "2ab80e487d2b84d39cde18ee58b612aa";
    private static final String LANGUAGE_VALUE = "en-US";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote.average.desc";

    /*Definition von zwei Integer-Konstanten: POPULARITY und TOP_RATED.
     -Diese Konstanten werden verwendet, um zu bestimmen, ob die Liste der Filme nach Beliebtheit oder nach Bewertung sortiert werden soll.*/
    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    /*Die buildURL()-Methode, die die URL für die HTTP-Anfrage konstruiert.
     -Methode: zwei ganzzahlige Argumente: sortBy und page.
     -Sie konstruiert die URL auf der Grundlage der Konstanten und der übergebenen Argumente und gibt die resultierende URL zurück.*/
    private static URL buildURL(int sortBy, int page) {
        URL result = null;
        String methodOfSort;
        if (sortBy == POPULARITY) {
            methodOfSort = SORT_BY_POPULARITY;
        } else {
            methodOfSort = SORT_BY_TOP_RATED;
        }

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;

    }

    /*Die Methode getJSONFromNetwork(), die die JSON-Antwort von der API abruft.
     zwei ganzzahlige Argumente: sortBy und page.
    Sie konstruiert die URL durch Aufruf der buildURL()-Methode und erstellt dann eine Instanz der JSONLoadTask-Klasse und führt sie mit der execute()-Methode aus.
    Die get()-Methode wird für das AsyncTask-Objekt aufgerufen, um den aktuellen Thread zu blockieren und auf das Ergebnis der Hintergrundaufgabe zu warten.
    Das Ergebnis wird dann als JSONObject zurückgegeben.
     */
    public static JSONObject getJSONFromNetwork(int sortBy, int page){
        JSONObject result = null;
        URL url = buildURL(sortBy, page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    /*Die Klasse JSONLoadTask, die eine private statische innere Klasse von NetworkUtils ist.
    Diese Klasse erweitert die AsyncTask-Klasse und ist für die Ausführung der HTTP-Anfrage im Hintergrund verantwortlich.
    Die Klasse nimmt als Eingabeparameter ein URL-Objekt, das über die Methode execute() übergeben wird.
    Die Methode doInBackground() wird überschrieben, um die HTTP-Anfrage auszuführen und die JSON-Antwort zu parsen.

    Die Methode erstellt ein HttpsURLConnection-Objekt und stellt eine Verbindung zur API her,
    indem sie eine Verbindung zur URL öffnet.
    Sie ruft dann die Antwort als Eingabestrom ab und verwendet einen BufferedReader, um den Strom zu lesen und einen StringBuilder zu erstellen.

    Der StringBuilder wird dann verwendet, um ein JSONObject aus der Antwort zu erstellen, das als Ergebnis der Hintergrundaufgabe zurückgegeben wird.
    Der finally-Block der Methode wird verwendet, um die Verbindung zur API zu schließen.
     */
    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }

            }
            return result;
        }
    }
 /*Dieser Code eine bequeme Möglichkeit, Filmdaten von der TMDb-API abzurufen,
 indem eine URL mit den entsprechenden Parametern konstruiert, die Methode getJSONFromNetwork() aufgerufen und die resultierende JSON-Antwort analysiert wird.
  */
}





/**
 * Der Code definiert eine private statische innere Klasse namens JSONLoadTask, die von der AsyncTask-Klasse erbt. Der Zweck dieser Klasse besteht darin, asynchrone Netzwerkoperationen durchzuführen, um JSON-Daten von einer URL abzurufen.
 *
 * Die Klasse enthält eine Methode namens doInBackground, die URLs als Parameter akzeptiert und eine JSONObject-Instanz zurückgibt. Diese Methode wird im Hintergrund ausgeführt und führt Netzwerkoperationen durch, um die Daten von der URL abzurufen.
 *
 * In der Methode wird zuerst ein HttpsURLConnection-Objekt erstellt, um eine Verbindung zur angegebenen URL herzustellen. Anschließend werden InputStream, InputStreamReader und BufferedReader verwendet, um die JSON-Daten in Form eines Strings zu lesen.
 *
 * Dann wird ein StringBuilder erstellt, um den gelesenen Text zu einem vollständigen String zusammenzufügen. Schließlich wird dieser String in ein JSONObject geparst, und das Ergebnis wird zurückgegeben.
 *
 * Falls ein Fehler bei der Netzwerkverbindung oder der Datenverarbeitung auftritt, werden die entsprechenden Exceptions abgefangen und der Stacktrace ausgegeben. In jedem Fall wird das HttpsURLConnection-Objekt am Ende geschlossen.
 */
