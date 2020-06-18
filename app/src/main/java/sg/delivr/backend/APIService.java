package sg.delivr.backend;


import sg.delivr.backend.postmodels.PostAssignedActionAQ;
import sg.delivr.backend.postmodels.PostAssignedBitAQ;
import sg.delivr.backend.postmodels.PostDoActionAQ;
import sg.delivr.backend.postmodels.PostDoCompletedJobs;
import sg.delivr.backend.postmodels.PostDoCreateOrder;
import sg.delivr.backend.postmodels.PostDoGetRiders;
import sg.delivr.backend.postmodels.PostDoLogin;
import sg.delivr.backend.postmodels.PostDoOrderHistory;
import sg.delivr.backend.postmodels.PostDoRiderQueue;
import sg.delivr.backend.postmodels.PostGetProfile;
import sg.delivr.backend.postmodels.PostMerchantAuth;
import sg.delivr.backend.postmodels.PostMerchantOrderEntry;
import sg.delivr.backend.postmodels.PostUpdateLatLong;
import sg.delivr.backend.postmodels.PostWalletPaymentIntent;
import sg.delivr.backend.postmodels.PostWalletPaymentStatus;
import sg.delivr.backend.postmodels.PostgetAssignedQueue;
import sg.delivr.backend.responsemodels.ResponseActionAQ;
import sg.delivr.backend.responsemodels.ResponseAssignedActionAQ;
import sg.delivr.backend.responsemodels.ResponseAssignedQueue;
import sg.delivr.backend.responsemodels.ResponseCompletedJobs;
import sg.delivr.backend.responsemodels.ResponseCreateOrder;
import sg.delivr.backend.responsemodels.ResponseGetRiders;
import sg.delivr.backend.responsemodels.ResponseGetWallet;
import sg.delivr.backend.responsemodels.ResponseLocationDatas;
import sg.delivr.backend.responsemodels.ResponseMerchantAuth;
import sg.delivr.backend.responsemodels.ResponseMerchantOrderEntry;
import sg.delivr.backend.responsemodels.ResponseOrderHistory;
import sg.delivr.backend.responsemodels.ResponsePaymentIntentClientSecret;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.backend.responsemodels.ResponseUserLogin;
import sg.delivr.backend.responsemodels.ResponseUserProfile;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sg.delivr.backend.responsemodels.ResponseWalletPaymentStatus;

public interface APIService {
    //***************USING Registration AND GET USER DETAILS SERVICE******************
   /* @POST("member/signup")
    Call<DataEnvelope<ResponseCreateProfileWithRegister>> createProfileWithRegister(@Body PostCreateProfileWithRegister post);

*/
    @GET("member/forgotpwd")
    Call<BaseResponse> forgotPwd(@Query("email") String email);

    @GET("mstAddress.json")
    Call<ArrayList<ResponseLocationDatas>> getLocations();

    //Login
    @POST("Users/signup")
    Call<ResponseUserLogin> checkLogin(@Body PostDoLogin post);

    //Get MyJobsList
    @POST("Users/GetRiders")
    Call<ResponseGetRiders> getRiders(@Body PostDoGetRiders getRiders);

    //Get RiderQueue
    @POST("RiderQueue/GetRiderQueue")
    Call<ArrayList<ResponseRiderQueue>> getRiderQueue(@Body PostDoRiderQueue getRiderQueue);

    @POST("Users/GetProfile")
    Call<ResponseUserProfile> getProfile(@Body PostGetProfile post);

    @POST("Schedule/UpdateLatLong")
    Call<BaseResponse> updateLatLong(@Body PostUpdateLatLong post);

    @POST("RiderQueue/ActionAQ")
    Call<ResponseActionAQ> saveActionAQ(@Body PostDoActionAQ post);

    @POST("RiderQueue/GetAssignedQueue")
    Call<ArrayList<ResponseAssignedQueue>> getAssignedQueue(@Body PostgetAssignedQueue postgetAssignedQueue);

    @POST("RiderQueue/BidAQ")
    Call<ResponseAssignedActionAQ> sendactionBidAQ(@Body PostAssignedBitAQ postgetAssignedQueue);

    @POST("RiderQueue/ActionAQ")
    Call<ResponseAssignedActionAQ> sendassignedActionAQ(@Body PostAssignedActionAQ postgetAssignedQueue);

    @POST("RiderQueue/GetCompletedQueue")
    Call<ArrayList<ResponseCompletedJobs>> getCompletedJobs(@Body PostDoCompletedJobs postDoCompletedJobs);

    @POST("Merchant/OrderTracking")
    Call<ArrayList<ResponseOrderHistory>> getOrderHistory(@Body PostDoOrderHistory postDoOrderHistory);

    //CreateOrder
    @POST("Merchant/OrderEntry")
    Call<ResponseCreateOrder> doCreateOrder(@Body PostDoCreateOrder postDoCreateOrder);

    @POST("Merchant/MerchOrderEntry")
    Call<ArrayList<ResponseMerchantOrderEntry>> getMerchantOrderEntry(@Body PostMerchantOrderEntry post);

    @POST("Merchant/MerchantAuth")
    Call<ArrayList<ResponseMerchantAuth>> getMerchantAuth(@Body PostMerchantAuth post);

    @POST("Wallet/PaymentIntent")
    Call<ResponsePaymentIntentClientSecret> getPaymentIntentClientSecret(@Body PostWalletPaymentIntent post);

    @POST("Wallet/GetWallet")
    Call<ResponseGetWallet> getWallet(@Body PostMerchantAuth post);

    @POST("Wallet/WalletStatus")
    Call<ResponseWalletPaymentStatus> sendPaymentStatus(@Body PostWalletPaymentStatus post);
}

