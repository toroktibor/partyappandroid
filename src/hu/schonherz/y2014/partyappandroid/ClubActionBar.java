package hu.schonherz.y2014.partyappandroid;

import hu.schonherz.y2014.partyappandroid.activities.ClubActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubGaleryFragment;
import hu.schonherz.y2014.partyappandroid.activities.ClubInfoModifyActivity;
import hu.schonherz.y2014.partyappandroid.activities.ClubMenuActivity;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class ClubActionBar implements OnClickListener, OnMenuItemClickListener {

    private ClubActivity activity;

    public ClubActionBar(ClubActivity activity) {
        this.activity = activity;
    }

    public void setLayout() {
        ViewGroup actionBarLayout = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.actionbar_club, null);
        ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        ab.setCustomView(actionBarLayout);

        activity.findViewById(R.id.actionbar_club_button_b).setOnClickListener(this);
        activity.findViewById(R.id.actionbar_club_button_c).setOnClickListener(this);
        activity.findViewById(R.id.actionbar_club_button_d).setOnClickListener(this);
        activity.findViewById(R.id.actionbar_club_button_e).setOnClickListener(this);

        ((ImageView) activity.findViewById(R.id.actionbar_club_button_b)).setBackgroundDrawable(activity.getResources()
                .getDrawable(R.drawable.ab_selected));
        ((ImageView) activity.findViewById(R.id.actionbar_club_button_c)).setBackgroundDrawable(null);
        ((ImageView) activity.findViewById(R.id.actionbar_club_button_d)).setBackgroundDrawable(null);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
        case R.id.actionbar_club_button_b:
            activity.viewPager.setCurrentItem(0);
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_b)).setBackgroundDrawable(activity
                    .getResources().getDrawable(R.drawable.ab_selected));
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_c)).setBackgroundDrawable(null);
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_d)).setBackgroundDrawable(null);
            break;

        case R.id.actionbar_club_button_c:
            activity.viewPager.setCurrentItem(1);
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_c)).setBackgroundDrawable(activity
                    .getResources().getDrawable(R.drawable.ab_selected));
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_b)).setBackgroundDrawable(null);
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_d)).setBackgroundDrawable(null);
            break;

        case R.id.actionbar_club_button_d:
            activity.viewPager.setCurrentItem(2);
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_d)).setBackgroundDrawable(activity
                    .getResources().getDrawable(R.drawable.ab_selected));
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_b)).setBackgroundDrawable(null);
            ((ImageView) activity.findViewById(R.id.actionbar_club_button_c)).setBackgroundDrawable(null);
            break;

        case R.id.actionbar_club_button_e:
            PopupMenu popupmenu = new PopupMenu(activity, v);
            MenuItem item;

            item = popupmenu.getMenu().add(0, 999, 0, "[debug] Adatok frissítése");
            item.setOnMenuItemClickListener(this);

            item = popupmenu.getMenu().add(0, 1, 0, "Árlista");
            item.setOnMenuItemClickListener(this);
            if (activity.viewPager.getCurrentItem() == 2) {
                item = popupmenu.getMenu().add(0, 2, 0, "Képfeltöltés");
                item.setOnMenuItemClickListener(this);
            }

            int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
            int club_id = Session.getSearchViewClubs().get(clubListPosition).id;
            if (!Session.getActualUser().isMine(club_id)) {
                item = popupmenu.getMenu().add(0, 3, 0, "Én vagyok a tulaj");
                item.setOnMenuItemClickListener(this);
                if (Session.getActualUser().getType() == 1) {
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
        final int user_id = Session.getActualUser().getId();
        final int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
        final int club_id = Session.getSearchViewClubs().get(clubListPosition).id;
        switch (arg0.getItemId()) {
        case 1:
            i = new Intent(activity, ClubMenuActivity.class);
            activity.startActivity(i);
            break;
        case 2:
            Log.e(this.getClass().getName(), "képfeltöltés");
            ClubGaleryFragment f = (ClubGaleryFragment) activity.getSupportFragmentManager().findFragmentById(
                    activity.viewPager.getId());
            f.uploadPicture();
            break;
        case 3:
            Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj!",
                    "A művelet folyamatban van..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().setOwnerForClub(user_id, club_id);
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(activity,
                                    "Tulajdonosi kérelmedet fogadtuk, kérlek várj türelemmel az admin jóváhagyására!")
                                    .show();
                        }
                    });
                }
            }).start();

            break;
        case 4:
            Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj!",
                    "A művelet folyamatban van..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().deleteFavoriteClubForUser(club_id, user_id);
                    Session.getActualUser().favoriteClubs.remove(Session.getSearchViewClubs().remove(clubListPosition));
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(activity, "A szórakozóhely kikerült a kedvenceidből!").show();
                        }
                    });
                }
            }).start();

            break;
        case 5:
            Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj!",
                    "A művelet folyamatban van..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Session.getInstance().getActualCommunicationInterface().setFavoriteClubForUser(club_id, user_id);
                    Session.getActualUser().favoriteClubs.add(Session.getSearchViewClubs().get(clubListPosition));
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Session.getInstance().dismissProgressDialog();
                            new DoneToast(activity, "A szórakozóhely bekerült a kedvenceid közé!").show();
                        }
                    });
                }
            }).start();

            break;
        case 6:
            i = new Intent(activity, ClubInfoModifyActivity.class);
            i.putExtra("listPosition", ClubActivity.intent.getExtras().getInt("listPosition"));
            activity.startActivity(i);
            break;
        case 7:
            if (!Session.getSearchViewClubs().get(clubListPosition).highlite_expire.equals("null")) {
                // Toast t = Toast.makeText(activity,
                // "Erre a helyre már van beállítva kiemelés.",
                // Toast.LENGTH_LONG);
                // t.show();
                new ErrorToast(activity, "Erre a helyre már van beállítva kiemelés.").show();
                break;
            }

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.highlight_expire_dialog, null);

            final EditText daysEditText = (EditText) view.findViewById(R.id.highlight_expire_dialog_number_edittext);

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setView(view);
            builder.setPositiveButton("Igénylés", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String daysString = daysEditText.getText().toString();
                    if (daysString.isEmpty()) {
                        new ErrorToast(activity, "Nem adtad meg hány napra szeretnéd a kiemelést.").show();
                        return;
                    }

                    final int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");

                    final int clubId = Session.getSearchViewClubs().get(clubListPosition).id;
                    final int days = Integer.parseInt(daysString);

                    if (days <= 0) {
                        new ErrorToast(activity, "Nem kérhetsz egynél kisebb napnyi kiemelést!").show();
                        return;
                    }

                    Session.getInstance().progressDialog = ProgressDialog.show(activity, "Kérlek várj",
                            "Kiemelés folyamatban...", true, false);

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            String highExpire = Session.getInstance()
                                    .getActualCommunicationInterface().setHighlightExpire(clubId, days);
                            
                            Session.getSearchViewClubs().get(clubListPosition).highlite_expire = highExpire;
                            Club club1 = Session.getActualUser().searchInLocalList(clubId, Session.getActualUser().favoriteClubs);
                            if( club1 != null ){
                                club1.highlite_expire = highExpire;
                            }
                            club1 = Session.getActualUser().searchInLocalList(clubId, Session.getActualUser().usersClubs);
                            if( club1 != null ){
                                club1.highlite_expire = highExpire;
                            }

                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    new DoneToast(activity, "Sikeres igénylés").show();
                                    Session.getInstance().dismissProgressDialog();
                                }
                            });
                        }
                    }).start();

                    return;

                }
            });
            builder.setNegativeButton("Mégsem", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setTitle("Kiemelés igénylése");

            final AlertDialog dialog = builder.create();

            dialog.show();
            break;
        case 999:
            activity.actualClub.downloadEverything();
            new DoneToast(activity, "A szórakozóhely adatai le lettek töltve ismét!").show();
            break;
        default:
            Log.e(this.getClass().getName(), "Nem kezelt onMenuItemClick");
        }

        return true;
    }
}
