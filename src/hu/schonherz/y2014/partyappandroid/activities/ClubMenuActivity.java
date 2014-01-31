package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.DoneToast;
import hu.schonherz.y2014.partyappandroid.MenuActionBar;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.MenuItemsListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import hu.schonherz.y2014.partyappandroid.xls.FileChooser;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ClubMenuActivity extends ActionBarActivity {


    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
	// TODO Auto-generated method stub
	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	final int index = info.position;
	switch (item.getItemId()) {
	case R.id.delete_club_menu_item:
	    final int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	    final int menuid = Session.getSearchViewClubs().get(clubListPosition).menuItems.get(index).id;
	    
		Session.getInstance().progressDialog = ProgressDialog.show(this, "Kérlek várj",
                "Törlés folyamatban...", true, false);
		
		new Thread(new Runnable() {

            @Override
            public void run() {
            	Session.getInstance().getActualCommunicationInterface().removeEMenuItem(menuid);
        	    Session.getSearchViewClubs().get(clubListPosition).menuItems.remove(index);
            	ClubMenuActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Session.getInstance().dismissProgressDialog();
                        new DoneToast(ClubMenuActivity.this, "Sikeres törlés!").show();
                        onResume();
                    }
                });
            }
        }).start();	    
	    return true;
	case R.id.modify_club_menu_item:
	    Intent i = new Intent(getApplicationContext(), ClubMenuModifyActivity.class);
	    i.putExtra("menuItemListPosition", index);
	    startActivity(i);
	    return true;
	default:
	    return super.onContextItemSelected(item);
	}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	// TODO Auto-generated method stub
	super.onCreateContextMenu(menu, v, menuInfo);

	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.club_menu_context_menu, menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	new MenuActionBar(this).setLayout();
	
	setContentView(R.layout.activity_club_menu);

	
	
	// buttonok lekerese
	ImageView addButton = (ImageView) findViewById(R.id.actionbar_menu_button_a);
	ImageView importButton = (ImageView) findViewById(R.id.actionbar_menu_button_b);
	addButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent i = new Intent(getApplicationContext(), ClubMenuAddActivity.class);
		startActivity(i);
	    }
	});
	importButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// Excel fájl beolvasása
		Intent i = new Intent(getApplicationContext(), FileChooser.class);
		startActivity(i);

	    }
	});


	// tulajextra funkcioi
	if (!ClubActivity.isClubOfActualUser &&  Session.getActualUser().getType()!=1) {
	    addButton.setVisibility(View.INVISIBLE);
	    importButton.setVisibility(View.INVISIBLE);
	}
    }

    private MenuItem[] getMenuItemArrayFromList(List<MenuItem> menuItemList) {
	MenuItem[] menuItemArray = new MenuItem[menuItemList.size()];
	for (int i = 0; i < menuItemList.size(); ++i) {
	    menuItemArray[i] = menuItemList.get(i);
	}
	return menuItemArray;
    }

    @Override
    protected void onResume() {
	// lista felfujasa
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	ListView menuItemListView = (ListView) findViewById(R.id.club_menu_listview);
	MenuItem[] menuItemArray = getMenuItemArrayFromList((Session.getSearchViewClubs().get(clubListPosition)).menuItems);
	menuItemListView.setAdapter(new MenuItemsListAdapter(this, menuItemArray));

	// tulajextra funkcioi
	if (ClubActivity.isClubOfActualUser ||  Session.getActualUser().getType()==1) {
	    registerForContextMenu(menuItemListView);
	}
	super.onResume();
    }
}
