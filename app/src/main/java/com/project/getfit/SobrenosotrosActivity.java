package com.project.getfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;

public class SobrenosotrosActivity extends AppCompatActivity {
    private static Fragment fragment;
    private ImageView botonTwitter;
    private ImageView botonInstagram;
    private ImageView botonYoutube;
    private Button textTwitter;
    String urlTwitter = "https://twitter.com/GetFitAppCM";
    String urlYoutube = "https://www.youtube.com/channel/UCVV02_UWJsfxz9VKZrgltrw";
    String urlInstagram = "https://www.instagram.com/getfitappcm/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobrenosotros);

        // Links de perfiles redes sociales

        botonInstagram = findViewById(R.id.icono_instagram);
        botonTwitter = findViewById(R.id.icono_twitter);
        botonYoutube = findViewById(R.id.icono_youtube);

        textTwitter = findViewById(R.id.text_twitter);

        botonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri link = Uri.parse(urlInstagram);
                Intent i = new Intent(Intent.ACTION_VIEW, link);
                startActivity(i);

            }
        });

        botonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri link = Uri.parse(urlTwitter);
                Intent i = new Intent(Intent.ACTION_VIEW, link);
                startActivity(i);

            }
        });

        botonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri link = Uri.parse(urlYoutube);
                Intent i = new Intent(Intent.ACTION_VIEW, link);
                startActivity(i);

            }
        });

        textTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "Cargando tweets...");
                //Buscamos a ver si hay algún fragmento en el contenedor
                fragment = getSupportFragmentManager().findFragmentById(R.id.container);

                // En caso de que no haya ningún fragmento, obtenemos los Tweets del Timeline de la API de twtitter y manejamos el Callback
                TweetRepository.getInstance().getTimelineAsync(timelineListener); // => timelineListener


            }
        });

        // FIN


        //Tweets
        //Buscamos a ver si hay algún fragmento en el contenedor
        fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        // En caso de que no haya ningún fragmento, obtenemos los Tweets del Timeline de la API de twtitter y manejamos el Callback
        if (fragment == null) {
            TweetRepository.getInstance().getTimelineAsync(timelineListener); // => timelineListener
        } else {
            View linearLayoutProgressBar = findViewById(R.id.linear_progress_bar);
            linearLayoutProgressBar.setVisibility(View.GONE);
        }
    }

    //Mostrar Tweets

    // Esta clase interna es la encargada de manejar el callback,  tiene dos métodos para manejar la posibilidad de éxito y de error.
    TwitterListener timelineListener = new TwitterAdapter() {

        @Override
        public void gotHomeTimeline(ResponseList<Status> statuses) {
            showTimeline(statuses);
        }

        @Override
        public void onException(TwitterException te, TwitterMethod method) {
            Log.e("Error", te.getErrorMessage());
            showError();
        }

        private void showError() {
            Log.e("Error", "Error al imprimir un Tweet");
        }
    };

    private void showTimeline(ResponseList<Status> statuses) {
        // Creamos un array de Strings con el texto de los Status( Tweets )
        String[] tweets = new String[statuses.size()];
        int counter = 0;
        for (Status status : statuses) {
            tweets[counter] = status.getText();
            counter++;
        }

        // Lo guardamos en un bundle, el cual le pasaremos al Fragment
        final Bundle bundle = new Bundle();
        bundle.putStringArray("tweets", tweets);

        // Debido a que el callback se está ejecutando en otro Thread distinto al Thread de UI, necesitamos mandar un mensaje
        // Al Thread de UI para poder actualizar la vista, para ello usamos el método runOnUiThread de la clase Activity
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Ocultamos la barra de progreso
                View progressBar = findViewById(R.id.linear_progress_bar);
                progressBar.setVisibility(View.GONE);

                // Insertamos el TimelineFragment
                Fragment fragment = new TimelineFragment();
                //Añadimos el bundle con los tweets que hemos creado anteriormente
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, fragment)
                        .commit();
            }
        });
    }

    //Fin Tweets
}