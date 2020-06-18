package sg.delivr;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.stripe.android.PaymentConfiguration;

import sg.delivr.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

//import android.support.multidex.MultiDex;


/**
 * Created by mustofa on 06/03/16.
 */
public class Application extends android.app.Application {
    private static final String TAG = Application.class.getSimpleName();
    // Default maximum disk usage in bytes
    private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;
    // Default cache folder name

    /*private Map<Class<? extends BaseUIListener>, Collection<? extends BaseUIListener>> uiListeners;
    private Map<Class<? extends BaseManagerInterface>, Collection<? extends BaseManagerInterface>> managerInterfaces;*/
    private static Application instance;
    private final String LOGGLY_TOKEN = "83d7097d-9dd5-4c3e-86e5-1091a7d82dde";
    public ProgressDialog progressDialog;
    private ArrayList<Object> registeredManagers;
    private ExecutorService backgroundExecutor;
    // Future for loading process
    private Future<Void> loadFuture;
    // Handler to execute runnable in UI thread
    private Handler handler;
    // Whether has been called
    private boolean closed;

    /*private RequestQueue requestQueue;
    private ImageLoader imageLoader;*/
    // Whether application is to be closed
    private boolean isForeground;
    // Whether lib was initialized
    private boolean initialized;
    // Wheter service started
    private boolean serviceStarted;
    private Dialog dialog;

    /*private Tracker mTracker;*/

    public Application() {
        instance = this;
        /*managerInterfaces = new HashMap<Class<? extends BaseManagerInterface>, Collection<? extends BaseManagerInterface>>();
        uiListeners = new HashMap<Class<? extends BaseUIListener>, Collection<? extends BaseUIListener>>();
        registeredManagers = new ArrayList<Object>();
        handler = new Handler();
        serviceStarted = false;
        closed = false;
        initialized = false;

        backgroundExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "Background Executor Service");
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setDaemon(true);
                return thread;
            }
        });*/
    }

    public static synchronized Application getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.delivr", // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("name not found", e.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans_Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_UUneYjetyYPeNMjd664l4wl300TQYrMirc"
        );

        /*Fabric.with(this, new Crashlytics());

        // Loggly integration
        Timber.plant(new LogglyTree(LOGGLY_TOKEN));

        // Build object manager from resource xml managers
        TypedArray managerClasses = getResources().obtainTypedArray(R.array.managers);
        for (int index = 0; index < managerClasses.length(); index++) {
            try {
                Class.forName(managerClasses.getString(index));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        managerClasses.recycle();

        // Build object table from resource xml tables
        TypedArray tableClasses = getResources().obtainTypedArray(R.array.tables);

        for (int index = 0; index < tableClasses.length(); index++)
            try {
                Class.forName(tableClasses.getString(index));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        tableClasses.recycle();*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public ArrayList<Object> getManagers() {
        return registeredManagers;
    }


}

