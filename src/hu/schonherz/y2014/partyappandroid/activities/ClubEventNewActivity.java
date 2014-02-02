package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerCommunicator;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerFragment;
import hu.schonherz.y2014.partyappandroid.dialogs.TimePickerCommunicator;
import hu.schonherz.y2014.partyappandroid.dialogs.TimePickerFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ClubEventNewActivity extends ActionBarActivity implements DatePickerCommunicator, TimePickerCommunicator {

    EditText nameEditText;
    EditText dateEditText;
    EditText descriptionEditText;
    Button addButton;
    Spinner musicTypeSpinner;
    int clubListPosition;
    EditText timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Új esemény").setLayout();

        setContentView(R.layout.activity_club_event_new);

        clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");

        nameEditText = (EditText) findViewById(R.id.club_event_new_edittext_name);
        dateEditText = (EditText) findViewById(R.id.club_event_new_edittext_date);
        timeEditText = (EditText) findViewById(R.id.club_event_new_edittext_time);
        descriptionEditText = (EditText) findViewById(R.id.club_event_new_edittext_description);
        addButton = (Button) findViewById(R.id.club_event_new_button_new);
        musicTypeSpinner = (Spinner) findViewById(R.id.club_event_new_music_type_spinner);

        dateEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");

            }
        });

        timeEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerFragment().show(getSupportFragmentManager(), "timePicker");

            }
        });

        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final String date = dateEditText.getText().toString();
                final String time = timeEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                final String musicType = musicTypeSpinner.getSelectedItem().toString();

                if (name.isEmpty()) {
                    new ErrorToast(getApplicationContext(), "Nem adta meg az esemény nevét!").show();
                    //Toast.makeText(getApplicationContext(), "Nem adta meg az esemény nevét!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (date.isEmpty()) {
                    new ErrorToast(getApplicationContext(), "Nem adta meg az esemény dátumát!").show();
                    //Toast.makeText(getApplicationContext(), "Nem adta meg az esemény dátumát!", Toast.LENGTH_LONG)
                    //        .show();
                    return;
                }
                if (time.isEmpty()) {
                    new ErrorToast(getApplicationContext(), "Nem adta meg az esemény pontos idejét!").show();
                    //Toast.makeText(getApplicationContext(), "Nem adta meg az esemény pontos idejét!", Toast.LENGTH_LONG)
                    //        .show();
                    return;
                }
                if (description.isEmpty()) {
                    new ErrorToast(getApplicationContext(), "Nem adta meg az esemény leírását!").show();
                    //Toast.makeText(getApplicationContext(), "Nem adta meg az esemény leírását!", Toast.LENGTH_LONG)
                    //        .show();
                    return;
                }
                // Log.e("EVENT", Date.valueOf(time) + "==" + (new
                // java.util.Date()).toLocaleString() );

                Session.getInstance().progressDialog = ProgressDialog.show(ClubEventNewActivity.this, "Kérlek várj",
                        "Hozzáadás folyamatban...", true, false);

                new NetThread(ClubEventNewActivity.this, new Runnable() {

                    @Override
                    public void run() {
                        int eventId = Session
                                .getInstance()
                                .getActualCommunicationInterface()
                                .addEvent(Session.getSearchViewClubs().get(clubListPosition).id, name, description,
                                        date + " " + time, "", musicType);
                        Log.i("asdasd",date + " " + time);
                        Event newEvent = new Event(eventId, name, description, date + " " + time, musicType, 1);
                        Session.getSearchViewClubs().get(clubListPosition).events.add(newEvent);
                        ClubEventNewActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Session.getInstance().dismissProgressDialog();
                                new DoneToast(ClubEventNewActivity.this, "Sikeres hozzáadás!").show();
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });

    }

    @Override
    public void onDatePicked(String date) {
        dateEditText.setText(date);
    }

    @Override
    public void onTimePicked(String time) {
        timeEditText.setText(time);

    }

}
