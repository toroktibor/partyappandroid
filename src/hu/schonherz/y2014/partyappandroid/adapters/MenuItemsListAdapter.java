package hu.schonherz.y2014.partyappandroid.adapters;

import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.util.datamodell.MenuItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
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
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            item = inflater.inflate(R.layout.menuitem_list_item, parent, false);
        }
        ((TextView) item.findViewById(R.id.menuitem_list_item_name)).setText(((MenuItem) getItem(position)).name);
        ((TextView) item.findViewById(R.id.menuitem_list_item_price)).setText((new Integer(
                ((MenuItem) getItem(position)).price)).toString());
        ((TextView) item.findViewById(R.id.menuitem_list_item_currency))
                .setText(((MenuItem) getItem(position)).currency);
        ((TextView) item.findViewById(R.id.menuitem_list_item_unit)).setText("/" + ((MenuItem) getItem(position)).unit);

        if (((MenuItem) getItem(position)).discount > 0) {
            TextView discountDetector = (TextView) item.findViewById(R.id.menuitem_list_item_discount);
            int actualPrice = ((MenuItem) getItem(position)).price;
            float actualDiscount = (float) ((100 - ((MenuItem) getItem(position)).discount) / 100.0);
            int newPrice = (int) ((int) actualPrice * actualDiscount);
            discountDetector.setText((new Integer(((MenuItem) getItem(position)).discount)).toString() + "%-os "
                    + "AKCIÓ!");
            // item.setBackgroundColor(Color.YELLOW);
            ((TextView) item.findViewById(R.id.menuitem_list_item_price)).setText((new Integer(newPrice)).toString());

            item.setBackgroundResource(R.drawable.high_club_item1);
            item.setPadding(15, 7, 7, 7);
        } else {
            item.setBackgroundColor(getContext().getResources().getColor(R.color.applicationWindowBackground));
            item.setPadding(7, 7, 7, 7);
        }

        item.setTag((MenuItem) getItem(position));
        return item;
    }
}
