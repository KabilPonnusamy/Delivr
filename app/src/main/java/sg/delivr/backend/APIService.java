package sg.delivr.backend;


import sg.delivr.backend.postmodels.PostAssignedActionAQ;
import sg.delivr.backend.postmodels.PostAssignedBitAQ;
import sg.delivr.backend.postmodels.PostDoActionAQ;
import sg.delivr.backend.postmodels.PostDoCompletedJobs;
import sg.delivr.backend.postmodels.PostDoGetRiders;
import sg.delivr.backend.postmodels.PostDoLogin;
import sg.delivr.backend.postmodels.PostDoRiderQueue;
import sg.delivr.backend.postmodels.PostGetProfile;
import sg.delivr.backend.postmodels.PostUpdateLatLong;
import sg.delivr.backend.postmodels.PostgetAssignedQueue;
import sg.delivr.backend.responsemodels.ResponseActionAQ;
import sg.delivr.backend.responsemodels.ResponseAssignedActionAQ;
import sg.delivr.backend.responsemodels.ResponseAssignedQueue;
import sg.delivr.backend.responsemodels.ResponseCompletedJobs;
import sg.delivr.backend.responsemodels.ResponseGetRiders;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.backend.responsemodels.ResponseUserLogin;
import sg.delivr.backend.responsemodels.ResponseUserProfile;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    //***************USING Registration AND GET USER DETAILS SERVICE******************
   /* @POST("member/signup")
    Call<DataEnvelope<ResponseCreateProfileWithRegister>> createProfileWithRegister(@Body PostCreateProfileWithRegister post);

*/
    @GET("member/forgotpwd")
    Call<BaseResponse> forgotPwd(@Query("email") String email);


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




}
