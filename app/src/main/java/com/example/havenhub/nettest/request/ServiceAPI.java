package com.example.havenhub.nettest.request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceAPI {
  @POST("api/student/info")
    Call<Student>getStudentInfo(@Body Student student);

  @POST("api/student/update")
  Call<StudentUpdateResponse>updateStudentPassword(@Body StudentUpdateRequest request);

  // 登录接口
  @POST("api/login")
  Call<LoginResponse> login(@Body LoginRequest request);

  // 注册接口
  @POST("api/register")
  Call<LoginResponse> register(@Body RegisterRquest RegisterRquest);

  // 测试接口
  @GET("api/test")
  Call<String> testConnection();


}
