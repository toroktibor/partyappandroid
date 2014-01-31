package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.activities.ClubEventModifyActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubEventNewActivity;
import hu.schonherz.y2014.partyappandroid.activities.ProfileModifyActivity;
import hu.schonherz.y2014.partyappandroid.activities.RegisterActivity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Button;
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

    @SuppressLint("NewApi")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datepicker = new DatePickerDialog(getActivity(), this, year, month, day);
        
        datepicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Beállít", datepicker);
        datepicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Mégsem", datepicker);
        
        
        /*b = datepicker.getButton(Dialog.BUTTON_POSITIVE);
        b.setText("Beállít");*/
      

        if (android.os.Build.VERSION.SDK_INT < 11)
            return datepicker;

        try {

            if (communicator instanceof RegisterActivity || communicator instanceof ProfileModifyActivity) {
                datepicker.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000 * 1);
            }

            if (communicator instanceof ClubEventNewActivity || communicator instanceof ClubEventModifyActivity) {
                Log.i("asdasd", "Igen");
                datepicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000 * 1);
            }
        } catch (Exception e) {
            // Ha mégsem tudná beállítani a max és min dátumot
        }

        // datepicker.getDatePicker().setMaxDate(maxDate)
        return datepicker;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String result = ((Integer) year).toString() + "-" + ((Integer) (monthOfYear + 1)).toString() + "-"
                + ((Integer) dayOfMonth).toString();
        communicator.onDatePicked(result);
    }
}