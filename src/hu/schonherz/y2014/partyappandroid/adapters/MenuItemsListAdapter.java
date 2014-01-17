package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuItemsListAdapter extends ArrayAdapter {
	public MenuItemsListAdapter(Context context, MenuItem[] objects) {
		super(context, R.layout.menuitem_list_item, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if (item == null) {
			LayoutInflater inflater = ((Activity) getContext())
					.getLayoutInflater();
			item = inflater.inflate(R.layout.menuitem_list_item, parent, false);
		}
		((TextView) item.findViewById(R.id.menuitem_list_item_name))
				.setText(((MenuItem) getItem(position)).name);
		((TextView) item.findViewById(R.id.menuitem_list_item_price))
				.setText(((MenuItem) getItem(position)).price);
		((TextView) item.findViewById(R.id.menuitem_list_item_currency))
				.setText(((MenuItem) getItem(position)).currency);
		((TextView) item.findViewById(R.id.menuitem_list_item_unit))
		.setText(((MenuItem) getItem(position)).unit);
		
		if(((MenuItem) getItem(position)).discount > 0){
			TextView discountDetector = (TextView) item.findViewById(R.id.menuitem_list_item_discount);
			int actualPrice = ((MenuItem) getItem(position)).price; 
			float actualDiscount = (float) ((((MenuItem) getItem(position)).discount) / 100.0);
			int newPrice = (int) ((int) actualPrice*actualDiscount);
			discountDetector.setText("AKCIÓ");
			item.setBackgroundColor(Color.YELLOW);
			((TextView) item.findViewById(R.id.menuitem_list_item_price))
			.setText(newPrice);
		}
		
		item.setTag((MenuItem) getItem(position));
		return item;
	}
}
