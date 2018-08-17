package fm.stcml.com.thomianfm;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class RequestSong extends AppCompatActivity {


    EditText songname, songartist;
    Button request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_song);

        songname = (EditText) findViewById(R.id.songname);
        songartist = (EditText) findViewById(R.id.songartist);
        request = (Button) findViewById(R.id.btnrequest);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(songname.getText().toString().matches("")){
                    songname.setError("This field cannot be empty!");
                }

                if(songartist.getText().toString().matches("")){
                    songartist.setError("This field cannot be empty!");
                }

                if(songname.getText().toString().trim().length() != 0 && songartist.getText().toString().trim().length() != 0 ){
                    String body = "SONG NAME: "+songname.getText().toString()+"\nARTIST: "+songartist.getText().toString();
                    String subject = "Song Request";
                    String to = "radioclubstc@gmail.com";

                    /*
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{to});
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(Intent.EXTRA_TEXT   , body);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(RequestSong.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    */

                    String mailto = "mailto:radioclubstc@gmail.com" +
                            "?subject=" + Uri.encode(subject) +
                            "&body=" + Uri.encode(body);

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailto));

                    try {
                        startActivity(emailIntent);
                    } catch (ActivityNotFoundException e) {
                        //TODO: Handle case where no email app is available
                    }
                }

            }
        });
    }


}

