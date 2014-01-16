package hu.schonherz.y2014.partyappandroid.dialogs;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.communication.InternetConnectionContinue;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;




public class InternetConnectionDialog extends Dialog implements android.view.View.OnClickListener {
	private InternetConnectionContinue ICContinue;
	
	public InternetConnectionDialog(Context context) {
		super(context);
	}

	public InternetConnectionDialog(Context context,
			InternetConnectionContinue ICContinue) {
		this(context);
		this.ICContinue=ICContinue;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_internetconnection_offline, null);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		setContentView(view);
		
		
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		//cancel();
		ICContinue.onResume();
		cancel();
	}
	
	
}
