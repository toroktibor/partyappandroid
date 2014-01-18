package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.MenuItemsListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ClubMenuActivity extends ActionBarActivity {
	
	Button addButton;
	Button importButton;
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		// TODO Auto-generated method stub
		Log.i("kerdes", ((AdapterContextMenuInfo)item.getMenuInfo()).position+"");
		switch(item.getItemId()) {
		case R.id.delete_club_menu_item:
			//kuldunk majd egy delete uzcsit
			return true;
		case R.id.modify_club_menu_item:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		    int index = info.position;
			Intent i = new Intent(getApplicationContext(),ClubMenuModifyActivity.class);
			i.putExtra("menuItemListPosition", index);
			startActivity(i);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.club_menu_context_menu, menu);
			
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_club_menu);
				
		//actoinbar begyalazasa
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_club_menu);
		
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.club__menu_actoinbar);
		
		//buttonok lekerese
		addButton = (Button) findViewById(R.id.club_menu_item_actionbar_add_button);
		importButton = (Button) findViewById(R.id.club_menu_item_actionbar_import_button);
		
		//lista felfujasa
		int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
		ListView menuItemListView = (ListView) findViewById(R.id.club_menu_listview);
		MenuItem[] menuItemArray = getMenuItemArrayFromList((Session.getSearchViewClubs().get(clubListPosition)).menuItems);
		menuItemListView.setAdapter(new MenuItemsListAdapter(this, menuItemArray));
		
		//tulajextra funkcioi
		if(ClubActivity.isClubOfActualUser){
			registerForContextMenu(menuItemListView);
		}else{
			addButton.setVisibility(View.INVISIBLE);
			importButton.setVisibility(View.INVISIBLE);
		}
    }

    private MenuItem[] getMenuItemArrayFromList(List<MenuItem> menuItemList){
    	MenuItem[] menuItemArray = new MenuItem[menuItemList.size()];
    	for(int i = 0; i < menuItemList.size(); ++i){
    		menuItemArray[i] = menuItemList.get(i);
    	}
    	return menuItemArray;
    }
    
}
