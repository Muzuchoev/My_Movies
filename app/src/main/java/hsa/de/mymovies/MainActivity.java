package hsa.de.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hsa.de.mymovies.data.MainViewModel;
import hsa.de.mymovies.data.Movie;
import hsa.de.mymovies.utils.JSONUtils;
import hsa.de.mymovies.utils.NetworkUtils;

/**
 * zeigt eine Liste von Filmen und ermöglicht es dem Benutzer, sie nach Beliebtheit oder Bewertung zu sortieren.
 * Verwendung der Filmdatenbank-API, um Filminformationen im JSON-Format zu erhalten.
 */

public class MainActivity extends AppCompatActivity {


    private Switch switchSort;                           /*eines Schalters, um zwischen der Sortierung nach Beliebtheit oder Bewertung umzuschalten*/
    private RecyclerView recyclerViewPosters;            /*RecyclerView, um die Filmplakate anzuzeigen*/
    private MovieAdapter movieAdapter;                   /*MovieAdapter, um die Filmdaten zu verarbeiten*/
    private TextView textViewTopRated;
    private TextView textViewPopularity;                 /*zwei TextViews, um die ausgewählte Sortiermethode anzuzeigen.*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFaourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFaourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private MainViewModel viewModel;                                                                                                /*




                                                                                                     * String url = NetworkUtils.buildURL(NetworkUtils.POPULARITY, 1).toString();
                                                                                                     * Log.i("MyResult", url)
                                                                                                     */

    @Override
    /*In der onCreate()-Methode wird die Layout-Datei für die App festgelegt und die privaten Instanzvariablen werden initialisiert.*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        switchSort = findViewById(R.id.switchSort);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        /*Die RecyclerView erhält einen GridLayoutManager mit zwei Spalten, und der MovieAdapter wird auf die RecyclerView gesetzt.*/
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this,2 ));
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonButton, boolean isChecked) {
                setMedhodOfSort(isChecked);
            }
        });
                                                                                                    /*Der switchSort wird überprüft und ein OnCheckedChangeListener wird gesetzt, um die setMedhodOfSort() Methode aufzurufen, wenn der Benutzer den Schalter umschaltet.*/
        switchSort.setChecked(false);                                                               /*Der MovieAdapter erhält OnPosterClickListener und OnReachEndListener, um Klicks auf Filmplakate und das Erreichen des Endes der Liste zu behandeln.*/
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
            Movie movie = movieAdapter.getMovies().get(position);
                Intent Intent = new Intent(MainActivity.this, DetailActivity.class);
                getIntent().putExtra("id",movie.getId());
                startActivity(Intent);
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "Ende der Liste", Toast.LENGTH_SHORT).show();
            }
        });
        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }
    /*Zwei Methoden werden definiert, onClickSetPopularity() und onClickSetTopRated(), die aufgerufen werden, wenn die entsprechenden TextViews angeklickt werden.
    Diese Methoden setzen die Sortiermethode und schalten den Schalter um.
    * */
    public void onClickSetPopularity(View view) {
        setMedhodOfSort(false);
        switchSort.setChecked(false);
    }

    public void onClickSetTopRated(View view) {
        setMedhodOfSort(true);
        switchSort.setChecked(true);
    }

    /*Die Methode setMedhodOfSort() wird definiert, die einen booleschen Parameter annimmt, der bestimmt, ob die Filme nach Bewertung oder Popularität sortiert werden sollen.
    Je nach Parameter wird die entsprechende Sortiermethode eingestellt, die Textfarbe der TextViews wird geändert, um die ausgewählte Sortiermethode anzuzeigen,
    --> der MovieAdapter wird mit den sortierten Filmdaten aktualisiert.
    */
    private void setMedhodOfSort(boolean isTopRated) {
        int methodOfSort;
        if (isTopRated) {
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white_color));
            methodOfSort = NetworkUtils.TOP_RATED;
        } else {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
        }
        downloadData(methodOfSort, 1);
    }
    private void downloadData (int methodOfSort, int page) {
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort,1);              /*Die Methoden getJSONFromNetwork() und getMoviesFromJSON() aus den Klassen NetworkUtils */
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);/* und JSONUtils werden verwendet, um die Filmdaten im JSON-Format abzurufen und zu parsen.*/
        if (movies != null && !movies.isEmpty()) {
            viewModel.deleteAllMovies();
            for (Movie movie : movies) {
                viewModel.insertMovie(movie);
            }
        }
    }
}

