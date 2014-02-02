package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Rating;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ClubRatingAdd extends ActionBarActivity {

    Rating actualRating;

    RatingBar ratingBar;
    EditText commentEditText;
    int clubListPosition;
    TextView lettersCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Értékelésed").setLayout();

        setContentView(R.layout.activity_club_rating_add);

        clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
        actualRating = Session.getSearchViewClubs().get(clubListPosition)
                .isRatingThisUser(Session.getActualUser().getId());

        ratingBar = (RatingBar) findViewById(R.id.club_rating_add_ratingbar);
        commentEditText = (EditText) findViewById(R.id.club_rating_add_user_comment_edittext);
        lettersCountTextView = (TextView) findViewById(R.id.letter_count_textview);
        lettersCountTextView.setText("0/255");
        
        commentEditText.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lettersCountTextView.setText(s.length()+"/255");
                
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                
            }
        });

        if (actualRating != null) {
            ratingBar.setRating(actualRating.value);
            commentEditText.setText(actualRating.comment);
            lettersCountTextView.setText(actualRating.comment.length()+"/255");
        }

        Button sendRating = (Button) findViewById(R.id.club_rating_add_rating_button);
        sendRating.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                Float value = ratingBar.getRating();

                if (comment.isEmpty()) {
                    new ErrorToast(getApplicationContext(), "Nem véleményezted a helyet!").show();
                    //Toast toast = Toast.makeText(getApplicationContext(), "Nem véleményezted a helyet!",
                    //        Toast.LENGTH_LONG);
                    //toast.show();
                    return;
                }

                if (actualRating == null) {
                    actualRating = new Rating(Session.getActualUser().getId(), Session.getActualUser().getNickname(),
                            value, comment, 0);
                    Session.getSearchViewClubs().get(clubListPosition).ratings.add(actualRating);
                    
                    Session.getInstance().progressDialog = ProgressDialog.show(ClubRatingAdd.this, "Kérlek várj",
                            "Hozzáadás folyamatban...", true, false);

                    new NetThread(ClubRatingAdd.this, new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance()
                            .getActualCommunicationInterface()
                            .addRating(Session.getInstance().getSearchViewClubs().get(clubListPosition).id,
                                    actualRating.userId, actualRating.value, actualRating.comment);

                            ClubRatingAdd.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Session.getInstance().dismissProgressDialog();
                                    new DoneToast(ClubRatingAdd.this, "Sikeres hozzáadás!").show();
                                    finish();
                                }
                            });
                        }
                    }).start();
                    
                } else {
                    actualRating.value = value;
                    actualRating.comment = comment;
                    
                    Session.getInstance().progressDialog = ProgressDialog.show(ClubRatingAdd.this, "Kérlek várj",
                            "Módosítás folyamatban...", true, false);

                    new NetThread(ClubRatingAdd.this, new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance()
                            .getActualCommunicationInterface()
                            .updateRating(Session.getInstance().getSearchViewClubs().get(clubListPosition).id,
                                    actualRating.userId, actualRating.value, actualRating.comment);

                            ClubRatingAdd.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Session.getInstance().dismissProgressDialog();
                                    new DoneToast(ClubRatingAdd.this, "Sikeres módosítás!").show();
                                    finish();
                                }
                            });
                        }
                    }).start();
                                       
                }
            }
        });
    }

}
