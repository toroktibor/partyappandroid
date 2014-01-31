package hu.schonherz.y2014.partyappandroid.activities;

import hu.schonherz.y2014.partyappandroid.ImageUtils;
import hu.schonherz.y2014.partyappandroid.NetThread;
import hu.schonherz.y2014.partyappandroid.R;
import hu.schonherz.y2014.partyappandroid.adapters.GridViewAdapter;
import hu.schonherz.y2014.partyappandroid.adapters.ImageItem;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.GaleryImage;
import hu.schonherz.y2014.partyappandroid.util.datamodell.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class ClubGaleryFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    ArrayList<Bitmap> imgs = new ArrayList<Bitmap>();
    private Context mContext;
    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PICTURE = 2;
    ArrayList<Integer> lista;
    public static Club actualClub;
    private TextView emptyMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_club_galery, container, false);

        // TODO:képek lekérése a szervertől
        // imgs.add (StringToBitMap
        // (Session.getInstance().getActualCommunicationInterface().DownLoadAnImage(4))
        // );

        int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
        actualClub = Session.getSearchViewClubs().get(clubListPosition);

        // int club_id = actualClub.id;
        /*
         * lista = Session.getInstance().getActualCommunicationInterface().
         * selectClubsImagesIds(club_id); for (int i = 0; i < lista.size();
         * i++){ Log.e("galery", "i: "+i); imgs.add(StringToBitMap
         * (Session.getInstance
         * ().getActualCommunicationInterface().DownLoadAnImage(lista.get(i)))
         * ); }
         */

        gridView = new GridView(mContext);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid,
                getDataForGridView());
        gridView.setAdapter(customGridAdapter);

        if (Session.getActualUser().getType() == 0 || Session.getActualUser().isMine(actualClub.id)) {
            registerForContextMenu(gridView);
        }

        emptyMessage = (TextView) rootView.findViewById(R.id.club_galery_textview_emptymessage);

        /**
         * On Click event for Single Gridview Item
         * */
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                Intent in1 = new Intent(getActivity(), FullImageActivity.class);
                in1.putExtra("imageid", position);
                startActivity(in1);
                getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.row_grid,
                getDataForGridView());
        gridView.setAdapter(customGridAdapter);
        gridView.invalidate();

        if (customGridAdapter.getCount() == 0) {
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

    private ArrayList getDataForGridView() {
        final ArrayList imageItems = new ArrayList();
        // retrieve String drawable array
        // TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);

        /*
         * for (int i = 0; i < imgs.size(); i++) { imageItems.add(new
         * ImageItem(imgs.get(i), "Image#" + i)); Log.e("getData",
         * "i: "+i+imgs.get(i)); }
         */

        for (int i = 0; i < actualClub.images.size(); i++) {
            imageItems.add(new ImageItem(actualClub.images.get(i).getBitmap_thumbnail(), "Image#" + i));
            Log.e("getData", "i: " + i + ", " + imageItems.get(i));
        }

        return imageItems;

    }

    public void uploadPicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Honnan szeretnél feltölteni?");
        builder.setNegativeButton("Galéria", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("uploadPicture", "Galéria");

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE);

            }
        });
        builder.setPositiveButton("Kamera", new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("uploadPicture", "Kamera");
                /*
                 * Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 * startActivityForResult(Intent.createChooser(intent,
                 * "Képfeltöltés"), TAKE_PICTURE);
                 */

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = ImageUtils.createImageFile();
                    } catch (IOException ex) {
                        Log.e("asdasd", "createImageFile hiba van", ex);
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(Intent.createChooser(takePictureIntent, "Képfeltöltés"), TAKE_PICTURE);
                    }
                }

            }
        });

        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {

                Uri uri = selectedImageUri;

                final int orientation = ImageUtils.tryGetOrientation(getActivity(), uri);

                final Bitmap b = ImageUtils.decodeUri(getActivity(), uri);

                Log.e("kép", "galéria kép hozzáadva");
                Log.i("asdasd", "A galériából feltöltendő kép mérete: " + b.getWidth() + "x" + b.getHeight());

                Session.getInstance().progressDialog = ProgressDialog.show(getActivity(), "Kérlek várj",
                        "Kép feltöltése...", true, false);
                new NetThread(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        // ezt kell elküldeni a szervernek
                        final String picture = ImageUtils.BitMapToString(b);

                        final int newID = Session.getInstance().getActualCommunicationInterface()
                                .uploadAnImage(ClubGaleryFragment.actualClub.id, picture, orientation);

                        ClubGaleryFragment.actualClub.images.add(new GaleryImage(newID, ImageUtils
                                .StringToBitMap(Session.getInstance().getActualCommunicationInterface()
                                        .DownLoadAnImageThumbnail(newID))));

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(),
                                        R.layout.row_grid, getDataForGridView());
                                gridView.setAdapter(customGridAdapter);
                                gridView.invalidate();

                                if (customGridAdapter.getCount() == 0) {
                                    emptyMessage.setVisibility(View.VISIBLE);
                                } else {
                                    emptyMessage.setVisibility(View.GONE);
                                }
                                Session.getInstance().dismissProgressDialog();

                            }
                        });

                    }
                }).start();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            // Uri selectedImageUri = data.getData();

            try {

                Uri uri = Uri.fromFile(new File(ImageUtils.mCurrentPhotoPath));

                final int orientation = ImageUtils.tryGetOrientation(getActivity(), uri);

                final Bitmap b = ImageUtils.decodeUri(getActivity(), uri);

                Log.e("kép", "kamera kép hozzáadva");
                Log.i("asdasd", "A kamerából feltöltendő kép merete: " + b.getWidth() + "x" + b.getHeight());

                Session.getInstance().progressDialog = ProgressDialog.show(getActivity(), "Kérlek várj",
                        "Kép feltöltése...", true, false);
                new NetThread(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        // ezt kell elküldeni a szervernek
                        final String picture = ImageUtils.BitMapToString(b);

                        final int newID = Session.getInstance().getActualCommunicationInterface()
                                .uploadAnImage(ClubGaleryFragment.actualClub.id, picture, orientation);

                        ClubGaleryFragment.actualClub.images.add(new GaleryImage(newID, ImageUtils
                                .StringToBitMap(Session.getInstance().getActualCommunicationInterface()
                                        .DownLoadAnImageThumbnail(newID))));

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(),
                                        R.layout.row_grid, getDataForGridView());
                                gridView.setAdapter(customGridAdapter);
                                gridView.invalidate();

                                if (customGridAdapter.getCount() == 0) {
                                    emptyMessage.setVisibility(View.VISIBLE);
                                } else {
                                    emptyMessage.setVisibility(View.GONE);
                                }

                                Session.getInstance().dismissProgressDialog();

                            }
                        });

                    }
                }).start();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    protected void clubFullDownload(int actualClubPosition) {
        Club actualCLub = Session.getSearchViewClubs().get(actualClubPosition);
        if (actualCLub.isNotFullDownloaded()) {
            Session.getSearchViewClubs().set(actualClubPosition, Session.getInstance

            ().getActualCommunicationInterface().getEverythingFromClub(actualCLub.id));
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.club_galery_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
        case R.id.delete_club_galery_item:
            int clubListPosition = ClubActivity.intent.getExtras().getInt("listPosition");
            int imageid = Session.getSearchViewClubs().get(clubListPosition).images.get(index).getId();
            Session.getInstance().getActualCommunicationInterface().deleteImage(imageid);
            Session.getSearchViewClubs().get(clubListPosition).images.remove(index);
            onResume();
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }
}
