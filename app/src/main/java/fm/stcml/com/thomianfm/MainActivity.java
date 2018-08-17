package fm.stcml.com.thomianfm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button request;
    private MediaPlayer mp;
    FloatingActionButton fab;
    ProgressDialog progress;
    boolean isPlaying = false;
    WebView player;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        textView = findViewById(R.id.txtClick);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.radioresonance.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        player = (WebView) findViewById(R.id.player);

        String html = "<audio style=\"width:100%;height:100%;\" controls><source src=\"http://108.163.197.114:8035/;stream.mp3\" type=\"audio/mpeg\"></audio>";
        player.loadData(html, "text/html; charset=UTF-8", null);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_schedule:
                                Intent sch = new Intent(getBaseContext(),ScheduleActivity.class);
                                startActivity(sch);
                                break;
                            case R.id.action_social:
                                Intent soc = new Intent(getBaseContext(),SocialActivity.class);
                                startActivity(soc);
                                break;
                            case R.id.action_info:
                                Intent inf = new Intent(getBaseContext(),AboutApp.class);
                                startActivity(inf);
                                break;
                        }
                        return true;
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(isNetworkAvailable() == false){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Network required")
                    .setMessage("You need to have a working network connection to run this application.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    }).show();
        }

        //Initialize Elements
        /*
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton();
            }
        });
        */

        request = (Button) findViewById(R.id.btnrequest);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reqintent = new Intent(MainActivity.this, RequestSong.class);
                startActivity(reqintent);
            }
        });

    }

    /*
    private void initializeMediaPlayer() {
        mp = new MediaPlayer();
        try {
            mp.setDataSource("http://95.154.202.117/stream.mp3?ipport=95.154.202.117_30759");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startPlaying() {
        isPlaying = true;
        fab.setImageResource(android.R.drawable.ic_media_pause);
        mp.prepareAsync();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    private void stopPlaying() {
        if (mp.isPlaying()) {
            mp.stop();
            mp.release();
            initializeMediaPlayer();
        }
        isPlaying = false;
        fab.setImageResource(android.R.drawable.ic_media_play);
    }

    public void toggleButton(){
        if (isPlaying == true) {
            //Stop
            stopPlaying();
        } else {
            //Start
            startPlaying();
        }
    }
    */

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String html = "<audio style=\"width:100%;height:100%;\" controls><source src=\"http://108.163.197.114:8035/;stream.mp3\" type=\"audio/mpeg\"></audio>";
        player.loadData(html, "text/html; charset=UTF-8", null);
    }
}
