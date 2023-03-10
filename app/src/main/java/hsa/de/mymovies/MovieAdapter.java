package hsa.de.mymovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hsa.de.mymovies.data.Movie;

/** Implementierung der Klasse MovieAdapter, die für die Anzeige einer Liste von Filmen in einer RecyclerView verantwortlich ist.
 *
 * Die Klassendeklaration beginnt mit dem extends-Schlüsselwort, um von der Klasse RecyclerView.Adapter zu erben,
 * die den Boilerplate-Code für die Verarbeitung der Daten in der RecyclerView bereitstellt.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

/*	hier definieren wir drei Instanzvariablen:

 * - movies: eine ArrayList von Movie Objekten die der Adapter anzeigen wird
 * - onPosterClickListener: eine Instanz der OnPosterClickListener Schnittstelle, welche verwendet wird um Klicks auf einzelne Filmplakate zu behandeln
 * - onReachEndListener: eine Instanz des OnReachEndListener Interfaces, welches verwendet wird um die Aktivität zu benachrichtigen wenn das Ende der Liste erreicht wurde.*/
    private List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    /*	Der Konstruktor initialisiert die Filme ArrayList.[Code_Block]*/
    public MovieAdapter() {
        movies = new ArrayList<>();
    }
    /*zwei Schnittstellendeklarationen: OnPosterClickListener und OnReachEndListener.
    Beide enthalten eine einzelne Methode, die in der Aktivität implementiert wird, die diesen Adapter verwendet.*/
    interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    interface OnReachEndListener {
        void onReachEnd();
    }
    /*Zwei Methoden werden definiert um die Click Listener für Filmplakate zu setzen und die Aktivität zu benachrichtigen wenn das Ende der Liste erreicht wurde:
     * 21. setOnPosterClickListener: setzt die onPosterClickListener Instanzvariable
     * 22. setOnReachEndListener: setzt die Instanzvariable onReachEndListener*/
    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

   /*Die Methode onCreateViewHolder bläst das Layout für jedes einzelne Filmelement auf und gibt eine neue Instanz der MovieViewHolder-Klasse zurück.*/
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    /*Die onBindViewHolder-Methode ist für die Bindung der Filmdaten an den Viewholder verantwortlich.
    Hier verwenden wir auch  die Picasso-Bibliothek, um das Filmplakatbild in die imageViewSmallPoster ImageView zu laden.*/
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        if (i > movies.size() - 4 && onPosterClickListener != null){
            onReachEndListener.onReachEnd();
        }
        Movie movie = movies.get(i);
        Picasso.get().load(movie.getPosterPath()).into(movieViewHolder.imageViewSmallPoster);

    }
    /*Die getItemCount Methode gibt die Anzahl der Filme in der movies ArrayList zurück.*/
    @Override
    public int getItemCount() {

        return movies.size();
    }

    /*Die innere Klasse MovieViewHolder repräsentiert ein einzelnes Element in der RecyclerView.
     Sie erweitert RecyclerView.ViewHolder und enthält eine einzelne ImageView-Instanzvariable für die Anzeige des Filmposters.*/
    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;

        /*
        -  Klasse MovieViewHolder hält das Layout für jede Zeile der RecyclerView
        - enthält eine Instanz der ImageView und weist das Klicken auf diese ImageView einem OnPosterClickListener zu
        - MovieViewHolder Konstruktor setzt einen OnClickListener auf die Elementansicht, um Klicks auf das Filmposter zu behandeln.*/
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }

                }
            });
        }
    }
    /*Die setMovies Methode setzt die movies ArrayList auf die bereitgestellte Liste von Filmen und benachrichtigt den Adapter das sich die Daten geändert haben.*/
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    /*Die addMovies Methode fügt die angegebene Liste von Filmen am Ende der movies ArrayList hinzu und benachrichtigt den Adapter das sich die Daten geändert haben.*/
    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
    /*Die getMovies Methode gibt die movies ArrayList zurück.*/
    public List<Movie> getMovies() {

        return movies;
    }
}

