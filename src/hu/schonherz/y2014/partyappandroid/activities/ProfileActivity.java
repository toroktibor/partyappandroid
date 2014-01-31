package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends ActionBarActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SimpleActionBar(this, "Profilom").setLayout();

        setContentView(R.layout.activity_profile);

        user = Session.getActualUser();
    }

    @Override
    protected void onResume() {

        TextView te;

        te = (TextView) findViewById(R.id.profile_textview_name_value);
        te.setText(user.getNickname());

        te = (TextView) findViewById(R.id.profile_textview_email_value);
        te.setText(user.getEmail());

        te = (TextView) findViewById(R.id.profile_textview_dateofbirth_value);
        te.setText(user.getBirthday());

        te = (TextView) findViewById(R.id.profile_textview_sex_value);
        te.setText(String.valueOf(user.getSex() == 0 ? "Férfi" : "Nő"));

        super.onResume();
    }

    public void onClickHandler(View v) {
        Intent i;
        switch (v.getId()) {
        case R.id.profile_button_modify:
            i = new Intent(this, ProfileModifyActivity.class);
            startActivity(i);
            break;

        case R.id.profile_button_password:
            i = new Intent(this, ProfilePasswordActivity.class);
            startActivity(i);
            break;

        default:
            break;
        }
    }

}
