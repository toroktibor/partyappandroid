package hu.schonherz.y2014.partyappandroid.dialogs;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	Calendar c;
	int year;
	int month;
	int day;
	DatePickerCommunicator communicator;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		communicator = (DatePickerCommunicator) activity;
	}
		
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the current time as the default values for the picker
    	Calendar c = Calendar.getInstance();
    	this.year = c.get(Calendar.YEAR);
    	this.month = c.get(Calendar.MONTH);
    	this.day = c.get(Calendar.DAY_OF_MONTH);

	// Create a new instance of DatePickerDialog and return it
	return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    	String result = ((Integer)year).toString() + "-" + ((Integer)(monthOfYear+1)).toString() + "-" + ((Integer)dayOfMonth).toString();
    	communicator.onDatePicked(result);
    }
}