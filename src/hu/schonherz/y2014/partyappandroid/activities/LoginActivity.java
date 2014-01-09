package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);

    }


    public void onClickHandler(View v) {
    	switch (v.getId()) {
		case R.id.login_button_login:
			startActivity(new Intent(this, ClubsActivity.class));
			break;

		case R.id.login_button_register:
			startActivity(new Intent(this, RegisterActivity.class));
		}

    }

}
