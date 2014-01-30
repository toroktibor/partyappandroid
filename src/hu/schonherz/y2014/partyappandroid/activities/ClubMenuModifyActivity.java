package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.SimpleActionBar;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ClubMenuModifyActivity extends ActionBarActivity {

    Spinner categorySpinner;
    EditText nameEditText;
    EditText priceEditText;
    Spinner currencySpinner;
    EditText quantityEditText;
    SeekBar discountSeekBar;
    TextView discountSummaryTextView;
    MenuItem actualMenuItem;
    int actualPrice;
    String actualCurrency;
    int actualDiscountInt;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	new SimpleActionBar(this, "Tétel módosítása").setLayout();
	
	setContentView(R.layout.activity_club_menu_modify);

	int menuItemListPosition = getIntent().getExtras().getInt("menuItemListPosition");
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	actualMenuItem = Session.getSearchViewClubs().get(clubListPosition).menuItems.get(menuItemListPosition);
	actualPrice = actualMenuItem.price;
	actualCurrency = actualMenuItem.currency;
	actualDiscountInt = actualMenuItem.discount;

	categorySpinner = (Spinner) findViewById(R.id.club_menu_modify_spinner_category);
	nameEditText = (EditText) findViewById(R.id.club_menu_modify_edittext_name);
	priceEditText = (EditText) findViewById(R.id.club_menu_modify_edittext_price);
	currencySpinner = (Spinner) findViewById(R.id.club_menu_modify_spinner_currency);
	quantityEditText = (EditText) findViewById(R.id.club_menu_modify_edittext_quantity);
	discountSeekBar = (SeekBar) findViewById(R.id.club_menu_modify_seekbar_discount);
	discountSummaryTextView = (TextView) findViewById(R.id.club_menu_modify_textview_discountsummary);
	addButton = (Button) findViewById(R.id.club_menu_modify_button_add);

	nameEditText.setText(actualMenuItem.name);
	priceEditText.setText((new Integer(actualMenuItem.price)).toString());
	quantityEditText.setText(actualMenuItem.unit);
	discountSeekBar.setProgress(actualMenuItem.discount);

	float actualDiscount = (float) ((100 - actualDiscountInt) / 100.0);
	int newPrice = (int) ((int) actualPrice * actualDiscount);
	discountSummaryTextView.setText(actualDiscountInt + "%: " + actualPrice + actualCurrency + " helyett "
		+ newPrice + actualCurrency);

	discountSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar) {
		float actualDiscount = (float) ((100 - seekBar.getProgress()) / 100.0);
		int newPrice = (int) ((int) actualPrice * actualDiscount);
		discountSummaryTextView.setText(seekBar.getProgress() + "%: " + actualPrice + actualCurrency
			+ " helyett " + newPrice + actualCurrency);
		actualDiscountInt = seekBar.getProgress();
	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar) {
		float actualDiscount = (float) ((100 - seekBar.getProgress()) / 100.0);
		int newPrice = (int) ((int) actualPrice * actualDiscount);
		discountSummaryTextView.setText(seekBar.getProgress() + "%: " + actualPrice + actualCurrency
			+ " helyett " + newPrice + actualCurrency);
		actualDiscountInt = seekBar.getProgress();
	    }

	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		float actualDiscount = (float) ((100 - seekBar.getProgress()) / 100.0);
		int newPrice = (int) ((int) actualPrice * actualDiscount);
		discountSummaryTextView.setText(seekBar.getProgress() + "%: " + actualPrice + actualCurrency
			+ " helyett " + newPrice + actualCurrency);
		actualDiscountInt = seekBar.getProgress();
	    }
	});

	addButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		String category = String.valueOf(categorySpinner.getSelectedItem());
		String name = nameEditText.getText().toString();
		int price = Integer.parseInt(priceEditText.getText().toString().isEmpty() ? "0" : priceEditText
			.getText().toString());
		String currency = String.valueOf(currencySpinner.getSelectedItem());
		String unit = quantityEditText.getText().toString();
		int discount = discountSeekBar.getProgress();

		if (name.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg a termék nevét!", Toast.LENGTH_LONG).show();
		    return;
		}

		if (price == 0) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg a termék árát!", Toast.LENGTH_LONG).show();
		    return;
		}

		if (unit.isEmpty()) {
		    Toast.makeText(getApplicationContext(), "Nem adta meg a termék egységét!", Toast.LENGTH_LONG)
			    .show();
		    return;
		}
		// itt kuldunk egy uzenetet a szervernek a valtoztatasokrol
		actualMenuItem.name = name;
		actualMenuItem.menu_category = category;
		actualMenuItem.price = price;
		actualMenuItem.currency = currency;
		actualMenuItem.unit = unit;
		actualMenuItem.discount = discount;
		Session.getInstance().getActualCommunicationInterface().updateAMenuItem(actualMenuItem);
		finish();
	    }
	});

	priceEditText.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void afterTextChanged(Editable s) {
	    if(s.toString().equals("") || s == null){
	    	actualPrice = 0;
	    } else {
	    	actualPrice = Integer.parseInt(s.toString());
	    }
	    float actualDiscount = (float) ((100 - actualDiscountInt) / 100.0);
		int newPrice = (int) ((int) actualPrice * actualDiscount);
		discountSummaryTextView.setText(actualDiscountInt + "%: " + actualPrice + actualCurrency + " helyett "
			+ newPrice + actualCurrency);
	    }
	});

	currencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

	    @Override
	    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		actualCurrency = currencySpinner.getItemAtPosition(arg2).toString();
		float actualDiscount = (float) ((100 - actualDiscountInt) / 100.0);
		int newPrice = (int) ((int) actualPrice * actualDiscount);
		discountSummaryTextView.setText(actualDiscountInt + "%: " + actualPrice + actualCurrency + " helyett "
			+ newPrice + actualCurrency);
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	    }
	});
    }

}
