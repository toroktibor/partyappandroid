package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Type.CubemapFace;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClubInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_club_info, container, false);
    	return rootView;
    }
    
    public void onClickHandler(View v){
    	switch(v.getId()){
    	case R.id.club_info_ratingbar:
    		 startActivity(new Intent(getActivity(), ClubReviewsActivity.class)); 
    	}
    }

}