package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.MenuItemsListAdapter;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ClubMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_club_menu);
	
	int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
	ListView menuItemListView = (ListView) findViewById(R.id.club_menu_listview);
	MenuItem[] menuItemArray = getMenuItemArrayFromList((Session.getSearchViewClubs().get(clubListPosition)).menuItems);
	menuItemListView.setAdapter(new MenuItemsListAdapter(this, menuItemArray));
	
    }

    private MenuItem[] getMenuItemArrayFromList(List<MenuItem> menuItemList){
    	MenuItem[] menuItemArray = new MenuItem[menuItemList.size()];
    	for(int i = 0; i < menuItemList.size(); ++i){
    		menuItemArray[i] = menuItemList.get(i);
    	}
    	return menuItemArray;
    }
    
}
