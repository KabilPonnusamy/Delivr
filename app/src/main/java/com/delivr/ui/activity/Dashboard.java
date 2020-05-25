package com.delivr.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.delivr.R;
import com.delivr.ui.LocalDB.DbContract;
import com.delivr.ui.LocalDB.DbHelper;
import com.delivr.ui.fragments.Frag_CompletedJobs;
import com.delivr.ui.fragments.Frag_MyJobs;
import com.delivr.ui.fragments.Frag_MyJobsQueue;
import com.delivr.ui.fragments.Frag_MyProfile;
import com.delivr.ui.login.LoginActivity;
import com.delivr.utils.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iceteck.silicompressorr.PathUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.VISIBLE;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    private static final int REQUEST_CAMERA_ACCESS_PERMISSION =5674;
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 5675;

    Activity activity = this;
    ImageView img_hamburg;
    TextView username;
    DrawerLayout drawer;
  //  BottomNavigationView bottom_nav;
    Toolbar dash_toolbar;
    CircleImageView userimage;
    ProgressDialog progressDialog;
    String profile_str = "";

    RelativeLayout myjobs_layout, jobqueue_layout, comp_jobs_layout, profile_layout,
            logout_layout;

    File file1;
    Uri file;
    Bitmap bc;
    Animation animation;

    DbHelper dbHelper;
    SQLiteDatabase database;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));

        toolbar_init();
        initView();
        open_Home();
    }

    private void open_Home() {
        Fragment fragment = new Frag_MyJobs();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, fragment);
            ft.commit();
        }
    }

    private void initView() {
        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();

        userimage = findViewById(R.id.userimage);
        myjobs_layout = findViewById(R.id.myjobs_layout);
        jobqueue_layout = findViewById(R.id.jobqueue_layout);
        comp_jobs_layout = findViewById(R.id.comp_jobs_layout);
        profile_layout = findViewById(R.id.profile_layout);
        logout_layout = findViewById(R.id.logout_layout);

     //   bottom_nav = findViewById(R.id.bottom_nav);
        drawer = findViewById(R.id.drawer_layout);
        img_hamburg = (ImageView) findViewById(R.id.img_hamburg);
        username = (TextView) findViewById(R.id.username);
        animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(500);

        setListeners();
      //  navigation_listener();
    }

    private void setListeners() {
        userimage.setOnClickListener(this);
        myjobs_layout.setOnClickListener(this);
        jobqueue_layout.setOnClickListener(this);
        comp_jobs_layout.setOnClickListener(this);
        profile_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        img_hamburg.setOnClickListener(this);
        username.setText(Prefs.getUserFullname());
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                validateShowImage();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                validateShowImage();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                validateShowImage();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void validateShowImage() {
        String strUserimage = "";
        DbHelper dbHelper = new DbHelper(Dashboard.this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readProfile(database, Prefs.getUserId());

        while(cursor.moveToNext()) {
            strUserimage = cursor.getString(cursor.getColumnIndex(DbContract.P_IMAGE_PATH));
        }

        if(strUserimage.equalsIgnoreCase("")) {
            userimage.setImageResource(R.mipmap.app_logo);
        } else {

            File file = new File(strUserimage);
            if(file.exists()) {
                Bitmap bmImg = BitmapFactory.decodeFile(strUserimage);
                userimage.setImageBitmap(bmImg);
            } else {
                userimage.setImageResource(R.mipmap.app_logo);
            }
        }
    }
   // as per request bottom navigation removed on 23-may-2020
   /* private void navigation_listener() {
        bottom_nav.setItemIconTintList(null);
        bottom_nav.getMenu().getItem(0).setIcon(R.drawable.icon_payment);

        bottom_nav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment fragment = null;
                        bottom_nav.getMenu().getItem(0).setIcon(R.drawable.icon_jobs);
                        bottom_nav.getMenu().getItem(1).setIcon(R.drawable.icon_compjobs);
                        bottom_nav.getMenu().getItem(2).setIcon(R.drawable.icon_profile);
                        switch (menuItem.getItemId()) {
                            case R.id.menu_job:
                                menuItem.setIcon(R.drawable.icon_payment);
                                fragment = new Frag_MyJobs();
                                if (fragment != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout, fragment);
                                    ft.commit();
                                }
                                return true;

                            case R.id.menu_profile:
                                menuItem.setIcon(R.drawable.icon_payment);
                                fragment = new Frag_MyProfile();
                                if (fragment != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout, fragment);
                                    ft.commit();
                                }
                                return true;

                            case R.id.menu_queue:
                                menuItem.setIcon(R.drawable.icon_payment);
                                fragment = new Frag_MyJobsQueue();
                                if (fragment != null) {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_layout, fragment);
                                    ft.commit();
                                }
                                return true;
                        }
                        return false;
                    }
                });
            }*/

    private void toolbar_init() {
        dash_toolbar = (Toolbar) findViewById(R.id.dash_toolbar);
        dash_toolbar.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_hamburg:
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                } else {
                    drawer.closeDrawer(GravityCompat.END);
                }
                break;

            case R.id.userimage:
                /*animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
//                        files = database.getdata();
                            bc = null;

                            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (android.os.Build.VERSION.SDK_INT >= 24) {

                                file1 = Util.getOutputMediaFile();
                                file = FileProvider.getUriForFile(getApplicationContext(),
                                        BuildConfig.APPLICATION_ID + ".provider",
                                        file1);

                                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            } else {
                                file = Uri.fromFile(Util.getOutputMediaFile());
                            }
                            intent1.putExtra(MediaStore.EXTRA_OUTPUT, file);

                            startActivityForResult(intent1, 100);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                userimage.startAnimation(animation);*/

                /*if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), getString(R.string.device_dont_have_camera),
                            Toast.LENGTH_LONG).show();
                    return;
                }*/
                CropImage.activity(null)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setBorderLineColor(getResources().getColor(R.color.colorPrimary))
                        .setGuidelinesColor(getResources().getColor(R.color.white))
                        .start(Dashboard.this);


                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.myjobs_layout:
                Fragment jobsfragment = new Frag_MyJobs();
                FragmentTransaction ftjobs = getSupportFragmentManager().beginTransaction();
                ftjobs.replace(R.id.frame_layout, jobsfragment);
                ftjobs.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.jobqueue_layout:
                Fragment queuefragment = new Frag_MyJobsQueue();
                FragmentTransaction ftqueue = getSupportFragmentManager().beginTransaction();
                ftqueue.replace(R.id.frame_layout, queuefragment);
                ftqueue.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.comp_jobs_layout:
                Fragment compfragment = new Frag_CompletedJobs();
                FragmentTransaction ftcomp = getSupportFragmentManager().beginTransaction();
                ftcomp.replace(R.id.frame_layout, compfragment);
                ftcomp.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.profile_layout:
                Fragment profilefragment = new Frag_MyProfile();
                FragmentTransaction ftprofile = getSupportFragmentManager().beginTransaction();
                ftprofile.replace(R.id.frame_layout, profilefragment);
                ftprofile.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout_layout:
                showLogoutDialog();
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(Dashboard.this)
                .setTitle("Alert!")
                .setMessage("Are you sure want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Prefs.setUserId("");
                        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String path1;
                if (android.os.Build.VERSION.SDK_INT >= 24) {
                    path1 = file1.getAbsolutePath();
                } else {
                    path1 = file.getPath();
                }
                Log.e("delivrApp", "Path: " + path1);


            }
        }*/

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();
                if(uri != null) {
                    try {
                        String filePath= PathUtil.getPath(getApplicationContext(), uri);
                        Log.e("delivrApp", "File_Path: " + filePath);

                        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                        File directory = contextWrapper.getDir(getFilesDir().getName(), Context.MODE_PRIVATE);

                        File newFile = new File(directory + "/uploads/");

                        updatePhoto(filePath);




//                        new ImageCompressionAsyncTask(this, filePath).execute(uri.toString(), "" + newFile);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), getString(R.string.some_issues_text), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updatePhoto(String filePath) {
        Bitmap bmImg = BitmapFactory.decodeFile(filePath);
        userimage.setImageBitmap(bmImg);

        DbHelper dbHelper = new DbHelper(Dashboard.this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.updateProfile(Prefs.getUserId(), filePath, database);
        dbHelper.close();
    }

   /* class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

        Context mContext;
        Uri compressUri = null;
        String filePath;

        public ImageCompressionAsyncTask(Context context, String filePath) {
            mContext = context;
            this.filePath = filePath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Dashboard.this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progressdialog);
        }

        @Override
        protected String doInBackground(String... params) {
            String filePath = SiliCompressor.with(mContext).compress(params[0], new File(params[1]));
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            File imageFile = new File(s);
            compressUri = Uri.fromFile(imageFile);
            Log.e("delivrApp", "FilePath_2: " + s);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), compressUri);

                Bitmap bm = BitmapFactory.decodeFile(s);
                String img_uri = compressUri.toString();
                Log.e("delivrApp", "Image_Path: " + s);
                Log.e("delivrApp", "URI: " + compressUri.toString());
                userimage.setImageURI(Uri.parse(img_uri));

                String base_64_imge = imagevalue(bm);
                *//*base64_image_list.add(base_64_imge);
                image_URI_list.add(img_uri);*//*

                // Checking only file information
                String name = imageFile.getName();
                float length = imageFile.length() / 1024f; // Size in KB
                int compressWidth = bitmap.getWidth();
                int compressHieght = bitmap.getHeight();
                String text = String.format(Locale.US, "Name: %s\nSize: %fKB\nWidth: %d\nHeight: %d", name, length, compressWidth, compressHieght);
                Log.e("delivrApp", "File_Info: " + text);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    private String imagevalue(Bitmap bitmap) {
        profile_str = getStringImage(bitmap);
        Log.e("delivrApp", "Base64_image: " + profile_str);
        return profile_str;
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.e("encodedImage", encodedImage);
        return encodedImage;
    }
}
