package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.activities.SetServicesCommunicator;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SetServicesOfClubFragment extends DialogFragment implements OnClickListener {

    SetServicesCommunicator communicator;

    Button done, cancel;

    Integer[] icons = { R.id.checkBoxBilliard, R.id.checkBoxBowling, R.id.checkBoxCoctailBar, R.id.checkBoxDance,
	    R.id.checkBoxDarts, R.id.checkBoxDJ, R.id.checkBoxFnDControl, R.id.checkBoxLiveMusic, R.id.checkBoxMenu,
	    R.id.checkBoxSportTV, R.id.checkBoxWiFi };


    List<CheckBox> checkboxes = new ArrayList<CheckBox>();

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);
	communicator = (SetServicesCommunicator) activity;
	Log.e("SETSERVICESFRAGMENT", "ONATTACH SUCCESSFULLY RUN");
    }

    @Override
    // BE KELL ÁLLÍTANI A KEZDŐ HÁTTÉRSZÍNT, HOGY NE AZ XML-BŐL DOLGOZZON, HANEM
    // A SZERVERRŐL KAPOTT LISTÁBÓL....
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = (View) inflater.inflate(R.layout.club_set_services_layout_b, null);
	Log.e("SETSERVICESFRAGMENT", "LAYOUT OF DIALOGFRAGMENT INFLATED");
	getDialog().setTitle("Szolgáltatások");
	done = (Button) view.findViewById(R.id.button_club_services_setted);
	cancel = (Button) view.findViewById(R.id.button_club_services_cancel);
	done.setOnClickListener(this);
	cancel.setOnClickListener(this);
	for (int i = 0; i < icons.length; ++i) {
	    checkboxes.add((CheckBox) view.findViewById(icons[i]));
	    checkboxes.get(i).setOnClickListener(this);
	    checkboxes.get(i).setOnLongClickListener(new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
		    Log.e("SETSERVICESFRAGMENT", "DIALOGFRAGMENT LONGONCLICKLISTENER CALLED");
		    int j = 0;
		    while (j < icons.length) {
			if (v.getId() == icons[j]) {
			    Toast.makeText(v.getContext(), v.getContentDescription(), Toast.LENGTH_LONG).show();
			    break;
			}

			++j;
		    }
		    return true;
		}
	    });
	}
	Log.e("SETSERVICESFRAGMENT", "ONCLICKLISTENER OF EVERY BUTTONS SETTED");
	return view;
    }

    @Override
    public void onClick(View v) {
	Log.e("SETSERVICESFRAGMENT", "DIALOGFRAGMENT ONCLICKLISTENER CALLED");
	int j = 0;
	while (j < icons.length) {
	    if (v.getId() == icons[j])
		break;
	    ++j;
	}
	if (j < icons.length) {
	    Log.e("SETSERVICESFRAGMENT", "ONE OF THE SERVICES ICON CLICKED...");
	} else if (v.getId() == R.id.button_club_services_cancel) {
	    Log.e("SETSERVICESFRAGMENT", "CANCEL BUTTON CLICKED");
	    dismiss();
	} else if (v.getId() == R.id.button_club_services_setted) {

	    Log.e("SETSERVICESFRAGMENT", "DONE BUTTON CLICKED");
	    List<String> result = new ArrayList<String>();
	    for (int i = 0; i < icons.length; ++i) {
		if (checkboxes.get(i).isChecked()) {
		    Log.e("SETSERVICESFRAGMENT", "SERVICES: " + Session.getInstance().servicesTokenList.get(i));
		    result.add(Session.getInstance().servicesTokenList.get(i));
		}
	    }
	    Log.e("SETSERVICESFRAGMENT", "");
	    communicator.onServicesSetted(result);
	    dismiss();
	}
    }
}
