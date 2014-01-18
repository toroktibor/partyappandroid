package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class ClubMenuModifyActivity extends Activity {
	
	Spinner categorySpinner;
	EditText nameEditText;
	EditText priceEditText;
	Spinner currencySpinner;
	EditText quantityEditText;
	SeekBar discountSeekBar;
	TextView discountSummaryTextView;
	MenuItem actualMenuItem;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_club_menu_modify);
		
		int menuItemListPosition = getIntent().getExtras().getInt("menuItemListPosition");
		int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
		actualMenuItem = Session.getSearchViewClubs().get(clubListPosition).menuItems.get(menuItemListPosition);
		
		categorySpinner = (Spinner) findViewById(R.id.club_menu_modify_spinner_category);
		nameEditText = (EditText) findViewById(R.id.club_menu_modify_edittext_name);
		priceEditText = (EditText) findViewById(R.id.club_menu_modify_edittext_price);
		currencySpinner = (Spinner) findViewById(R.id.club_menu_modify_spinner_currency);
		quantityEditText = (EditText) findViewById(R.id.club_menu_modify_edittext_quantity);
		discountSeekBar = (SeekBar) findViewById(R.id.club_menu_modify_seekbar_discount);
		discountSummaryTextView = (TextView) findViewById(R.id.club_menu_modify_textview_discountsummary);
		
		nameEditText.setText(actualMenuItem.name);
		priceEditText.setText((new Integer(actualMenuItem.price)).toString());
		quantityEditText.setText(actualMenuItem.unit);
		discountSeekBar.setProgress(actualMenuItem.discount);
		
		float actualDiscount = (float) ((100 - actualMenuItem.discount) / 100.0);
		int newPrice = (int) ((int) actualMenuItem.price*actualDiscount);
		discountSummaryTextView.setText(actualMenuItem.discount+"%: "+actualMenuItem.price+actualMenuItem.currency+" helyett "+newPrice+actualMenuItem.currency);
		
		discountSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				float actualDiscount = (float) ((100 - seekBar.getProgress()) / 100.0);
				int newPrice = (int) ((int) actualMenuItem.price*actualDiscount);
				discountSummaryTextView.setText(seekBar.getProgress()+"%: "+actualMenuItem.price+actualMenuItem.currency+" helyett "+newPrice+actualMenuItem.currency);				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				float actualDiscount = (float) ((100 - seekBar.getProgress()) / 100.0);
				int newPrice = (int) ((int) actualMenuItem.price*actualDiscount);
				discountSummaryTextView.setText(seekBar.getProgress()+"%: "+actualMenuItem.price+actualMenuItem.currency+" helyett "+newPrice+actualMenuItem.currency);				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float actualDiscount = (float) ((100 - seekBar.getProgress()) / 100.0);
				int newPrice = (int) ((int) actualMenuItem.price*actualDiscount);
				discountSummaryTextView.setText(seekBar.getProgress()+"%: "+actualMenuItem.price+actualMenuItem.currency+" helyett "+newPrice+actualMenuItem.currency);
			}
		});
    }

}
