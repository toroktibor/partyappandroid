package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerCommunicator;
import hu.schonherz.y2014.partyappandroid.dialogs.DatePickerFragment;
import hu.schonherz.y2014.partyappandroid.dialogs.TimePickerCommunicator;
import hu.schonherz.y2014.partyappandroid.dialogs.TimePickerFragment;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Event;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ClubEventModifyActivity extends ActionBarActivity implements DatePickerCommunicator,TimePickerCommunicator{

    EditText nameEditText;
    EditText dateEditText;
    EditText timeEditText;
    EditText descriptionEditText;
    Spinner musicTypeSpinner;
    Button modifyButton;
    Event actualEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	new SimpleActionBar(this, "Esemény módosítása").setLayout();
	
	setContentView(R.layout.activity_club_event_modify);

	int eventListPosition = getIntent().getExtras().getInt("eventsListPosition");
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	actualEvent = Session.getSearchViewClubs().get(clubListPosition).events.get(eventListPosition);

	nameEditText = (EditText) findViewById(R.id.club_event_modify_edittext_name);
	dateEditText = (EditText) findViewById(R.id.club_event_modify_edittext_date);
	timeEditText = (EditText) findViewById(R.id.club_event_modify_edittext_time);
	descriptionEditText = (EditText) findViewById(R.id.club_event_modify_edittext_description);
	modifyButton = (Button) findViewById(R.id.club_event_modify_button_modify);
	musicTypeSpinner = (Spinner) findViewById(R.id.club_event_modify_music_type_spinner);

	int position = Event.getMusicTypePosition(actualEvent.music_type);
	
	musicTypeSpinner.setSelection(position);
	nameEditText.setText(actualEvent.name);
	dateEditText.setText(actualEvent.start_date);
	descriptionEditText.setText(actualEvent.description);

	
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
	
	modifyButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		String name = nameEditText.getText().toString();
		String date = dateEditText.getText().toString();
		String description = descriptionEditText.getText().toString();
		String musicType = musicTypeSpinner.getSelectedItem().toString();

		if (name.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg az esemény nevét!", Toast.LENGTH_LONG).show();
		    return;
		}
		if (date.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg az esemény dátumát!", Toast.LENGTH_LONG)
			    .show();
		    return;
		}
		if (description.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg az esemény leírását!", Toast.LENGTH_LONG)
			    .show();
		    return;
		}
		
		actualEvent.name = name;
		actualEvent.start_date = date;
		actualEvent.description = description;
		actualEvent.music_type = musicType;
		
		Session.getInstance().getActualCommunicationInterface().updateEvent(actualEvent.id, actualEvent.name, actualEvent.description, actualEvent.start_date, "", actualEvent.music_type);
		finish();
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
