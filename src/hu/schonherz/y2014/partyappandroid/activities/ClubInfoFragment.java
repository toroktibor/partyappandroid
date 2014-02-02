package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ErrorToast;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ClubInfoFragment extends Fragment {

    private Club actualClub;
    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_info, container, false);

        int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
        // Log.i("Átjött", String.valueOf(clubListPosition));
        actualClub = Session.getSearchViewClubs().get(clubListPosition);

        /*
         * if (actualClub.isNotFullDownloaded()) {
         * actualClub.downloadEverything(); }
         */

        return rootView;
    }

    @Override
    public void onStart() {
        

        RatingBar clubRatingBar = (RatingBar) rootView.findViewById(R.id.club_info_ratingbar);
        TextView clubNameTextView = (TextView) rootView.findViewById(R.id.club_info_textview_name);
        TextView clubAddressTextView = (TextView) rootView.findViewById(R.id.club_info_textview_address);
        TextView clubDescriptionTextView = (TextView) rootView.findViewById(R.id.club_info_textview_description);
        TextView clubServicesTextView = (TextView) rootView.findViewById(R.id.textViewServices);
        TextView reviewCounter = (TextView) rootView.findViewById(R.id.club_info_reviewcounter);
        ImageButton call = (ImageButton) rootView.findViewById(R.id.phone_call);
        ImageButton message = (ImageButton) rootView.findViewById(R.id.message);
        ImageButton showOnTheMap = (ImageButton) rootView.findViewById(R.id.showOnTheMap);
        ImageButton routePlanning = (ImageButton) rootView.findViewById(R.id.routePlanning);


        if (actualClub.phonenumber == null || actualClub.phonenumber.equals("null")
                || actualClub.phonenumber.equals("")) {
            call.setImageDrawable(getResources().getDrawable(R.drawable.club_call2));
            call.setEnabled(false);
            call.setBackgroundColor(getResources().getColor(R.color.editTextBackground));
        } else {
            call.setImageDrawable(getResources().getDrawable(R.drawable.club_call));
            call.setEnabled(true);
            call.setBackgroundDrawable(getResources().getDrawable(R.drawable.purple_button));
        }

        if (actualClub.email == null || actualClub.email.equals("null") || actualClub.email.equals("")) {
            message.setImageDrawable(getResources().getDrawable(R.drawable.club_message2));
            message.setEnabled(false);
            message.setBackgroundColor(getResources().getColor(R.color.editTextBackground));
        } else {
            message.setImageDrawable(getResources().getDrawable(R.drawable.club_message));
            message.setEnabled(true);
            message.setBackgroundDrawable(getResources().getDrawable(R.drawable.purple_button));
        }

        if (Session.getActualUser().isMine(actualClub.id)) {
            call.setImageDrawable(getResources().getDrawable(R.drawable.club_call2));
            call.setEnabled(false);
            call.setBackgroundColor(getResources().getColor(R.color.editTextBackground));
            
                message.setImageDrawable(getResources().getDrawable(R.drawable.club_message));
                message.setEnabled(true);
                message.setBackgroundDrawable(getResources().getDrawable(R.drawable.purple_button));
            
        }

        if (actualClub.position == null || actualClub.position.equals("null")) {
            showOnTheMap.setImageDrawable(getResources().getDrawable(R.drawable.club_map2));            
            showOnTheMap.setBackgroundColor(getResources().getColor(R.color.editTextBackground));
            showOnTheMap.setEnabled(false);
            
            routePlanning.setImageDrawable(getResources().getDrawable(R.drawable.club_route2));            
            routePlanning.setBackgroundColor(getResources().getColor(R.color.editTextBackground));
            routePlanning.setEnabled(false);
        } else {
            showOnTheMap.setImageDrawable(getResources().getDrawable(R.drawable.club_map));            
            showOnTheMap.setBackgroundDrawable(getResources().getDrawable(R.drawable.purple_button));
            showOnTheMap.setEnabled(true);
            
            routePlanning.setImageDrawable(getResources().getDrawable(R.drawable.club_route));            
            routePlanning.setBackgroundDrawable(getResources().getDrawable(R.drawable.purple_button));
            routePlanning.setEnabled(true);
        }
        
        showOnTheMap.invalidate();
        routePlanning.invalidate();
        call.invalidate();
        message.invalidate();

        clubNameTextView.setText(actualClub.name);
        clubAddressTextView.setText(actualClub.address);
        clubDescriptionTextView.setText(actualClub.description);
        

        StringBuilder sb = new StringBuilder();
        sb.append("Szolgáltatások:\n");
        String actServ = new String();

        // Log.e("INFO", "NO. OF SERVICES" + actualClub.services.size());
        if (actualClub.services.size() == 0) {
            sb.append("Nincs megjelelölt szolgáltatás");
        } else {
            for (int i = 0; i < actualClub.services.size(); ++i) {
                actServ = actualClub.services.get(i);
                // Log.e("INFO", actServ);
                sb.append(Session.getInstance().servicesNameList.get(
                        Session.getInstance().servicesTokenList.indexOf(actServ.toString())).toString());
                if (i != actualClub.services.size() - 1)
                    sb.append(", ");
            }
        }
        String serviceList = sb.toString();
        // Log.e("INFO", "SERVICES: " + serviceList);
        clubServicesTextView.setText(serviceList);
        // Log.e("INFO", "SERVICES SETTED");

        clubRatingBar.setRating(actualClub.getAvarageRate());
        reviewCounter.setText("(" + actualClub.ratings.size() + " értékelés)");

        OnTouchListener ratingListener = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(getActivity(), ClubRatingsActivity.class);
                startActivity(i);
                return false;
            }
        };

        rootView.findViewById(R.id.club_info_firstblock).setOnTouchListener(ratingListener);

        // clubRatingBar.setOnTouchListener(ratingListener);
        // reviewCounter.setOnTouchListener(ratingListener);

        
        super.onStart();
    }

}
