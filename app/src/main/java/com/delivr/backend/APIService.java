package com.delivr.backend;


import com.delivr.backend.postmodels.PostDoGetRiders;
import com.delivr.backend.postmodels.PostDoLogin;
import com.delivr.backend.postmodels.PostDoRiderQueue;
import com.delivr.backend.postmodels.PostGetProfile;
import com.delivr.backend.responsemodels.ResponseGetRiders;
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.backend.responsemodels.ResponseUserLogin;
import com.delivr.backend.responsemodels.ResponseUserProfile;


import java.util.ArrayList;
import java.util.List;

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



}
