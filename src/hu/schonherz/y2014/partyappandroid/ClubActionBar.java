package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubGaleryFragment;
import hu.schonherz.y2014.partyappandroid.activities.ClubInfoModifyActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubMenuActivity;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ClubActionBar implements OnClickListener, OnMenuItemClickListener {

	private ClubActivity activity;

	public ClubActionBar(ClubActivity activity) {
		this.activity = activity;
	}

	public void setLayout() {
		ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater()
				.inflate(R.layout.actionbar_club, null);
		ActionBar ab = activity.getSupportActionBar();
		ab.setDisplayShowHomeEnabled(false);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowCustomEnabled(true);
		ab.setCustomView(actionBarLayout);

		activity.findViewById(R.id.actionbar_club_button_b).setOnClickListener(
				this);
		activity.findViewById(R.id.actionbar_club_button_c).setOnClickListener(
				this);
		activity.findViewById(R.id.actionbar_club_button_d).setOnClickListener(
				this);
		activity.findViewById(R.id.actionbar_club_button_e).setOnClickListener(
				this);

		((ImageView) activity.findViewById(R.id.actionbar_club_button_b))
				.setBackgroundDrawable(activity.getResources().getDrawable(
						R.drawable.ab_selected));
		((ImageView) activity.findViewById(R.id.actionbar_club_button_c))
				.setBackgroundDrawable(null);
		((ImageView) activity.findViewById(R.id.actionbar_club_button_d))
				.setBackgroundDrawable(null);

	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.actionbar_club_button_b:
			activity.viewPager.setCurrentItem(0);
			((ImageView) activity.findViewById(R.id.actionbar_club_button_b))
					.setBackgroundDrawable(activity.getResources().getDrawable(
							R.drawable.ab_selected));
			((ImageView) activity.findViewById(R.id.actionbar_club_button_c))
					.setBackgroundDrawable(null);
			((ImageView) activity.findViewById(R.id.actionbar_club_button_d))
					.setBackgroundDrawable(null);
			break;

		case R.id.actionbar_club_button_c:
			activity.viewPager.setCurrentItem(1);
			((ImageView) activity.findViewById(R.id.actionbar_club_button_c))
					.setBackgroundDrawable(activity.getResources().getDrawable(
							R.drawable.ab_selected));
			((ImageView) activity.findViewById(R.id.actionbar_club_button_b))
					.setBackgroundDrawable(null);
			((ImageView) activity.findViewById(R.id.actionbar_club_button_d))
					.setBackgroundDrawable(null);
			break;

		case R.id.actionbar_club_button_d:
			activity.viewPager.setCurrentItem(2);
			((ImageView) activity.findViewById(R.id.actionbar_club_button_d))
					.setBackgroundDrawable(activity.getResources().getDrawable(
							R.drawable.ab_selected));
			((ImageView) activity.findViewById(R.id.actionbar_club_button_b))
					.setBackgroundDrawable(null);
			((ImageView) activity.findViewById(R.id.actionbar_club_button_c))
					.setBackgroundDrawable(null);
			break;

		case R.id.actionbar_club_button_e:
			PopupMenu popupmenu = new PopupMenu(activity, v);
			MenuItem item;

			item = popupmenu.getMenu().add(0, 999, 0,
					"[debug] Adatok frissítése");
			item.setOnMenuItemClickListener(this);

			item = popupmenu.getMenu().add(0, 1, 0, "Árlista");
			item.setOnMenuItemClickListener(this);
			if (activity.viewPager.getCurrentItem() == 2) {
				item = popupmenu.getMenu().add(0, 2, 0, "Képfeltöltés");
				item.setOnMenuItemClickListener(this);
			}

			int clubListPosition = ClubActivity.intent.getExtras().getInt(
					"listPosition");
			int club_id = Session.getSearchViewClubs().get(clubListPosition).id;
			if (!Session.getActualUser().isMine(club_id)) {
				item = popupmenu.getMenu().add(0, 3, 0, "Én vagyok a tulaj");
				item.setOnMenuItemClickListener(this);
				if(Session.getActualUser().getType()==1){
					item = popupmenu.getMenu().add(0, 6, 0, "Hely szerkesztése");
					item.setOnMenuItemClickListener(this);
				}
			} else {
				item = popupmenu.getMenu().add(0, 6, 0, "Hely szerkesztése");
				item.setOnMenuItemClickListener(this);
				item = popupmenu.getMenu().add(0, 7, 0, "Kiemelés igénylése");
				item.setOnMenuItemClickListener(this);
			}

			if (Session.getActualUser().isLike(club_id)) {
				item = popupmenu.getMenu().add(0, 4, 0, "Nem kedvencem");
				item.setOnMenuItemClickListener(this);
			} else {
				item = popupmenu.getMenu().add(0, 5, 0, "Kedvencek közzé");
				item.setOnMenuItemClickListener(this);
			}
			
			popupmenu.show();
			break;

		default:
			Log.e(this.getClass().getName(), "Nem kezelt onClick view");
			break;
		}

	}

	@Override
	public boolean onMenuItemClick(MenuItem arg0) {
		Intent i;
		int user_id = Session.getActualUser().getId();
		int clubListPosition = ClubActivity.intent.getExtras().getInt(
				"listPosition");
		int club_id = Session.getSearchViewClubs().get(clubListPosition).id;
		switch (arg0.getItemId()) {
		case 1:
			i = new Intent(activity, ClubMenuActivity.class);
			activity.startActivity(i);
			break;
		case 2:
			Log.e(this.getClass().getName(), "képfeltöltés");
			ClubGaleryFragment f = (ClubGaleryFragment) activity
					.getSupportFragmentManager().findFragmentById(
							activity.viewPager.getId());
			f.uploadPicture();
			break;
		case 3:
			Session.getInstance().getActualCommunicationInterface()
					.setOwnerForClub(user_id, club_id);			
			new DoneToast(activity,"Tulajdonosi kérelmedet fogadtuk, kérlek várj türelemmel az admin jóváhagyására!").show();
			break;
		case 4:
			Session.getInstance().getActualCommunicationInterface()
					.deleteFavoriteClubForUser(club_id, user_id);
			Session.getActualUser().favoriteClubs.add(Session
					.getSearchViewClubs().remove(clubListPosition));
			new DoneToast(activity,"A szórakozóhely kikerült a kedvenceidből!").show();
			break;
		case 5:
			Session.getInstance().getActualCommunicationInterface()
					.setFavoriteClubForUser(user_id, club_id);
			Session.getActualUser().favoriteClubs.add(Session
					.getSearchViewClubs().get(clubListPosition));
			new DoneToast(activity,"A szórakozóhely bekerült a kedvenceid közé!").show();
			break;
		case 6:
			i = new Intent(activity, ClubInfoModifyActivity.class);
			i.putExtra("listPosition", ClubActivity.intent.getExtras().getInt("listPosition"));
			activity.startActivity(i);
			break;
		case 7:
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewGroup view = (ViewGroup) inflater.inflate(R.layout.highlight_expire_dialog, null);
			
			final EditText daysEditText = (EditText) view.findViewById(R.id.highlight_expire_dialog_number_edittext);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setView(view);
			builder.setNegativeButton("Igénylés", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String daysString = daysEditText.getText().toString();
					if(daysString.isEmpty()){
						Toast t = Toast.makeText(activity, "Nem adtad meg hány napra szeretnéd a kiemelést.", Toast.LENGTH_LONG);
						t.show();
						return;
					}
					
					int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
					
					int clubId = Session.getSearchViewClubs().get(clubListPosition).id;
					int days = Integer.parseInt(daysString);
					
					if(days<=0){
						Toast t = Toast.makeText(activity, "Nem kérhetsz nullánál kisebb napnyi kiemelést!", Toast.LENGTH_LONG);
						t.show();
						return;
					}
					
					Session.getInstance().getActualCommunicationInterface().setHighlightExpire(clubId, days);

				}
			});
			builder.setPositiveButton("Mégse",
			new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					

				}
			});
			builder.setTitle("Kiemelés igénylése.");

			final AlertDialog dialog = builder.create();

			dialog.show();
			break;
		case 999:
			activity.actualClub.downloadEverything();
			new DoneToast(activity,
					"A szórakozóhely adatai le lettek töltve ismét!").show();
			break;
		default:
			Log.e(this.getClass().getName(), "Nem kezelt onMenuItemClick");
		}

		return true;
	}
}
