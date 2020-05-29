package sg.delivr.ui.LocalDB;

/**
 * Created by govindaraj on 14/06/18.
 */

public class DbContract {

    public static final int SYNC_STATUS_OK = 1;
    public static final int SYNC_STATUS_FAILED = 0;

    public static final int MM_SYNC_STATUS_OK = 1;
    public static final int MM_SYNC_STATUS_FAILED = 0;

    public static final int MA_SYNC_STATUS_OK = 1;
    public static final int MA_SYNC_STATUS_FAILED = 0;

    public static final String UI_UPDATE_BROADCASR = "com.delivr.ui.LocalDB.uiupdatebroadcast";

    // DB
    public static final String DATABASE_NAME = "delivr_database"; // DB name

    //Filtered Match Users Profile Table
    public static final String PROFILE_TABLE = "profile_table";
    public static final String P_ID = "p_id";
    public static final String P_USER_ID = "p_user_id";
    public static final String P_IMAGE_PATH = "p_image_path";
    public static final String P_CREATED = "p_created";

}