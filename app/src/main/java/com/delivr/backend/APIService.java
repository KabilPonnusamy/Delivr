package com.delivr.backend;


import com.delivr.backend.postmodels.PostDoLogin;
import com.delivr.backend.responsemodels.ResponseUserLogin;




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













}
