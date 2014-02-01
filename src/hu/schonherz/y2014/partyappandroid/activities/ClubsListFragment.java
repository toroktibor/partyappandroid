package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.ClubListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ClubsListFragment extends Fragment implements ClubsUpdateableFragment {

    public ListView clubsListView;
    private static ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("asdasd", "Lista onCreateView");
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_clubs_list, container, false);
            
            TextView tv = (TextView) rootView.findViewById(R.id.clubs_list_textview_message);
            if (Session.getSearchViewClubs().size() == 0){
                tv.setText("Sajnos nincs találat :(");
                tv.setVisibility(View.VISIBLE);
            }
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        clubsListView = new ListView(getActivity().getApplicationContext());
        Log.d("FELSOROLÁS ELEJE", "HURRÁ BÉBI! :D");
        Club[] clubArray = getClubArrayFromClubsList(Session.getSearchViewClubs());
        // Log.i("jojo",clubArray[0].address);
        clubsListView.setAdapter(new ClubListAdapter(getActivity(), clubArray));
        clubsListView.setCacheColorHint(Color.TRANSPARENT);
        // clubsListView.setClickable(true);
        
        clubsListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Log.d("ONCLICKLISTENER FUT", (new Integer(arg2).toString()));
                // Intent intent = new Intent(getActivity(),
                // ClubActivity.class);
                // intent.putExtra("listPosition", arg2);
                // activity.startActivity(intent);
                final Activity activity = getActivity();
                final Intent i = new Intent(activity, ClubActivity.class);
                i.putExtra("listPosition", arg2);
                final Club actualClub = Session.getSearchViewClubs().get(arg2);

                if (actualClub.isNotFullDownloaded()) {

                    Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj",
                            "Adatok betöltése...", true, false);
                    new NetThread(getActivity(), new Runnable() {

                        @Override
                        public void run() {
                            actualClub.downloadEverything();
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    activity.startActivity(i);
                                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                                    Session.getInstance().dismissProgressDialog();
                                    if (Session.getInstance().oldPhone) {
                                        getActivity().finish();
                                    }

                                }
                            });
                        }
                    }).start();
                } else {
                    activity.startActivity(i);
                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    if (Session.getInstance().oldPhone) {
                        getActivity().finish();
                    }
                }
            }

        });

        if (Session.getActualUser().getType() == 1) {
            registerForContextMenu(clubsListView);
        }

        rootView.addView(clubsListView);
        return rootView;
    }

    private Club[] getClubArrayFromClubsList(List<Club> clubList) {
        Club[] clubArray = new Club[clubList.size()];
        for (int i = 0; i < clubList.size(); ++i) {
            clubArray[i] = clubList.get(i);
        }
        return clubArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("asdasd", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("asdasd", "Lista hozzáadása a fragmentekhez");
        ((ClubsActivity) activity).fragments[0] = this;
    }

    @Override
    public void updateResults() {
        clubsListView.setAdapter(new ClubListAdapter(getActivity(), getClubArrayFromClubsList(Session
                .getSearchViewClubs())));

        ListView lv = (ListView) getView().findViewById(R.id.clubs_list_listview);
        // ClubListAdapter cla = (ClubListAdapter) lv.getAdapter();
        lv.invalidateViews();

        TextView tv = (TextView) getActivity().findViewById(R.id.clubs_list_textview_message);

        if (Session.getSearchViewClubs().size() == 0) {
            tv.setText("Sajnos nincs találat :(");
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);

        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.club_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
        case R.id.delete_club:
            int clubId = Session.getSearchViewClubs().get(index).id;
            Session.getInstance().getActualCommunicationInterface().deleteClub(clubId);
            Session.getSearchViewClubs().remove(index);
            onResume();
            return true;
        case R.id.modify_club:
            Activity activity = getActivity();
            Intent i = new Intent(activity, ClubInfoModifyActivity.class);
            i.putExtra("listPosition", index);
            activity.startActivity(i);
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }
}
