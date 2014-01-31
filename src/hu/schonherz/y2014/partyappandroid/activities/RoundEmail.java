package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RoundEmail extends Activity {

    float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_round_email);

        rating = 0.0f;
        Button send = (Button) findViewById(R.id.round_email_send);
        send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
                clubFullDownload(clubListPosition);
                Club actualClub = Session.getSearchViewClubs().get(clubListPosition);

                // TODO:a min értékeléssel rendelkező értékelők email-címeinek
                // kiszedése
                String email = actualClub.email;

                EditText et_subject = (EditText) findViewById(R.id.round_subject);
                String subject = et_subject.getText().toString().trim();

                EditText et_body = (EditText) findViewById(R.id.round_message);
                String body = et_body.getText().toString().trim();

                Log.e("email", "cím: " + email);
                Log.e("email", "tárgy: " + subject);
                Log.e("email", "üzenet: " + body);

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(RoundEmail.this, "There are no email clients installed.",

                    Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void clubFullDownload(int actualClubPosition) {
        Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
        if (actualCLub.isNotFullDownloaded()) {
            Session.getSearchViewClubs().set(actualClubPosition, Session.getInstance

            ().getActualCommunicationInterface().getEverythingFromClub(actualCLub.id));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.round_email, menu);
        return true;
    }

}
