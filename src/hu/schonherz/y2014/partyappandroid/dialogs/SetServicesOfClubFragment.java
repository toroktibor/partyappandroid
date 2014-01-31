package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.ClubsActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.activities.SetServicesCommunicator;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SetServicesOfClubFragment extends DialogFragment {

    SetServicesCommunicator communicator;

    public static ClubsActionBar cab;
    Button done, cancel;

    static Integer[] checkBoxIDs = { R.id.checkBoxBilliard, R.id.checkBoxBowling, R.id.checkBoxCoctailBar,
            R.id.checkBoxDance, R.id.checkBoxDarts, R.id.checkBoxDJ, R.id.checkBoxFnDControl, R.id.checkBoxLiveMusic,
            R.id.checkBoxMenu, R.id.checkBoxSportTV, R.id.checkBoxWiFi };
    List<CheckBox> checkboxes = new ArrayList<CheckBox>();
    public List<String> servSelected = new ArrayList<String>();

    public void show(SetServicesCommunicator cab, List<String> servSelected, FragmentManager manager, String tag) {
        if (servSelected == null)
            throw new RuntimeException("Itt nagy baj van, a lista null");

        this.servSelected = servSelected;
        communicator = cab;
        super.show(manager, tag);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setPositiveButton("Mentés",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.e("SETSERVICESFRAGMENT", "DIALOGFRAGMENT ONCLICKLISTENER CALLED");

                        List<String> servStrings = new ArrayList<String>();

                        servSelected.clear();
                        for (int j = 0; j < checkboxes.size(); j++) {
                            if (checkboxes.get(j).isChecked()) {
                                SetServicesOfClubFragment.this.servSelected.add(Session.getInstance().servicesTokenList
                                        .get(j));
                                servStrings.add(Session.getInstance().servicesNameList.get(j));
                            }
                        }

                        communicator.onServicesSetted(joinStrings(servStrings, ", "));
                        dismiss();

                    }
                }).setNegativeButton("Mégsem", new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("SETSERVICESFRAGMENT", "CANCEL BUTTON CLICKED");
                dismiss();

            }
        });

        View view = (View) getActivity().getLayoutInflater().inflate(R.layout.club_set_services_layout_b, null);

        for (int i = 0; i < checkBoxIDs.length; ++i) {
            checkboxes.add((CheckBox) view.findViewById(checkBoxIDs[i]));
            if (servSelected.contains(Session.getInstance().servicesTokenList.get(i)))
                checkboxes.get(i).setChecked(true);

            checkboxes.get(i).setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Log.e("SETSERVICESFRAGMENT", "DIALOGFRAGMENT LONGONCLICKLISTENER CALLED");
                    int j = 0;
                    while (j < checkBoxIDs.length) {
                        if (v.getId() == checkBoxIDs[j]) {
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

        adb.setView(view);
        return adb.create();
    }

    public static List<String> tokenToName(List<String> tokenList) {
        ArrayList<String> nameList = new ArrayList<String>();

        for (String sToken : tokenList) {
            for (int i = 0; i < Session.servicesTokenList.size(); i++) {
                if (Session.servicesTokenList.get(i).equals(sToken)) {
                    nameList.add(Session.servicesNameList.get(i));
                    sToken = "---";
                }
            }
        }

        return nameList;
    }

    public static String joinStrings(List<String> list, String delim) {

        StringBuilder sb = new StringBuilder();

        String loopDelim = "";

        for (String s : list) {

            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }

        return sb.toString();
    }

}
