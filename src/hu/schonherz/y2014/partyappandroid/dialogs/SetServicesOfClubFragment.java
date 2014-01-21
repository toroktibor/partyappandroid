package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.R;

import java.util.zip.Inflater;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SetServicesOfClubFragment extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.club_set_services_layout, null);
	}
	
}
