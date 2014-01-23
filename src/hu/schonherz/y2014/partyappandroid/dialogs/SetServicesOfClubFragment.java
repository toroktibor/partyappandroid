package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.activities.SetServicesCommunicator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class SetServicesOfClubFragment extends DialogFragment implements OnClickListener {
	
	SetServicesCommunicator communicator;
	
	Button done, cancel;
	
	Integer[] icons = { R.id.imageButtonBilliard,
						R.id.imageButtonBowling,
						R.id.imageButtonCoctailBar,
						R.id.imageButtonDance,
						R.id.imageButtonDarts,
						R.id.imageButtonDJ,
						R.id.imageButtonFnDControl,
						R.id.imageButtonLiveMusic,
						R.id.imageButtonMenu,
						R.id.imageButtonSportTV,
						R.id.imageButtonWiFi };
	
	String[] services = { 	"billiard",
							"bowling",
							"coctailbar",
							"dance",
							"darts",
							"dj",
							"fndcontrol",
							"livemusic",
							"menu",
							"sporttv",
							"wifi" };
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		communicator = (SetServicesCommunicator) activity;
		Log.e("SETSERVICESFRAGMENT", "ONATTACH SUCCESSFULLY RUN");
	}
	
	@Override	//BE KELL ÁLLÍTANI A KEZDŐ HÁTTÉRSZÍNT, HOGY NE AZ XML-BŐL DOLGOZZON, HANEM A SZERVERRŐL KAPOTT LISTÁBÓL....
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.club_set_services_layout, null);
		Log.e("SETSERVICESFRAGMENT","LAYOUT OF DIALOGFRAGMENT INFLATED");
		//done = (Button) container.findViewById(R.id.button_club_services_setted);
		//cancel = (Button) container.findViewById(R.id.button_club_services_cancel);
		done = (Button) view.findViewById(R.id.button_club_services_setted);	
		cancel = (Button) view.findViewById(R.id.button_club_services_cancel);
		done.setOnClickListener(this);
		cancel.setOnClickListener(this);
		ImageButton[] imagebuttons = { (ImageButton) view.findViewById(R.id.imageButtonBilliard),
				(ImageButton)view.findViewById(R.id.imageButtonBowling),
				(ImageButton)view.findViewById(R.id.imageButtonCoctailBar),
				(ImageButton)view.findViewById(R.id.imageButtonDance),
				(ImageButton)view.findViewById(R.id.imageButtonDarts),
				(ImageButton)view.findViewById(R.id.imageButtonDJ),
				(ImageButton)view.findViewById(R.id.imageButtonFnDControl),
				(ImageButton)view.findViewById(R.id.imageButtonLiveMusic),
				(ImageButton)view.findViewById(R.id.imageButtonMenu),
				(ImageButton)view.findViewById(R.id.imageButtonSportTV),
				(ImageButton)view.findViewById(R.id.imageButtonWiFi) };
		for(int i = 0; i < imagebuttons.length; ++i)
			imagebuttons[i].setOnClickListener(this);
		Log.e("SETSERVICESFRAGMENT","ONCLICKLISTENER OF EVERY BUTTONS SETTED");
		return view;
	}

	@Override
	public void onClick(View v) {
		Log.e("SETSERVICESFRAGMENT","DIALOGFRAGMENT ONCLICKLISTENER CALLED");
		int j = 0;
		while(j < icons.length) {
			if(v.getId() == icons[j])
				break;
			++j;
		}
		if( j < icons.length) {
			Log.e("SETSERVICESFRAGMENT","ONE OF THE SERVICES ICON CLICKED...");
			if(v.getBackground().equals(Color.WHITE)) {
				Log.e("SETSERVICESFRAGMENT","ICON COLOR CHANGED (WHILE->YELLOW)...");
				v.setBackgroundColor(Color.YELLOW);
			}
			else if(v.getBackground().equals(Color.YELLOW)) {
				Log.e("SETSERVICESFRAGMENT","ICON COLOR CHANGED (YELLOW->WHITE)...");
				v.setBackgroundColor(Color.WHITE);
			}
		}
		else if(v.getId() == R.id.button_club_services_cancel) {
			Log.e("SETSERVICESFRAGMENT","CANCEL BUTTON CLICKED");
			dismiss();
		}
		else if(v.getId() == R.id.button_club_services_setted) {
			Log.e("SETSERVICESFRAGMENT","DONE BUTTON CLICKED");
			List<String> result = new ArrayList<String>();
			for(int i = 0; i < icons.length; ++i) {
				if(v.getBackground().equals(Color.YELLOW))
					result.add(services[i]);
			}
			Log.e("SETSERVICESFRAGMENT","");
			communicator.onServicesSetted(result);
			dismiss();
		}
	}
}	
