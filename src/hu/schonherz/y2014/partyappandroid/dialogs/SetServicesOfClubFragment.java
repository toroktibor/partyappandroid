package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.activities.SetServicesCommunicator;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class SetServicesOfClubFragment extends DialogFragment {
	
	SetServicesCommunicator communicator;
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
	
	@Override	//BE KELL ÁLLÍTANI A KEZDŐ HÁTTÉRSZÍNT, HOGY NE AZ XML-BŐL DOLGOZZON, HANEM A SZERVERRŐL KAPOTT LISTÁBÓL....
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.club_set_services_layout, null);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(	v.getId() == R.id.imageButtonCoctailBar ||
					v.getId() == R.id.imageButtonDJ ||
					v.getId() == R.id.imageButtonFnDControl ||
					v.getId() == R.id.imageButtonLiveMusic ||
					v.getId() == R.id.imageButtonMenu) {
					Log.e("SETSERVICESFRAGMENT","ONE OF THE SERVICES ICON CLICKED...");
					if(v.getBackground().equals(Color.WHITE)) {
						v.setBackgroundColor(Color.YELLOW);
					}
					else if(v.getBackground().equals(Color.YELLOW)) {
						v.setBackgroundColor(Color.WHITE);
					}
				}
				else if(v.getId() == R.id.button_club_services_cancel) {
					Log.e("SETSERVICESFRAGMENT","DONE BUTTON CLICKED");
					dismiss();
				}
				else if(v.getId() == R.id.button_club_services_setted) {
					Log.e("SETSERVICESFRAGMENT","DONE BUTTON CLICKED");
					List<String> result = new ArrayList<String>();
					for(int i = 0; i < icons.length; ++i) {
						if(getActivity().findViewById(icons[i]).getBackground().equals(Color.YELLOW))
							result.add(services[i]);
					}
					communicator.onServicesSetted(result);
					dismiss();
				}
			}
		});
		return view;
	}
	
	
	
}
