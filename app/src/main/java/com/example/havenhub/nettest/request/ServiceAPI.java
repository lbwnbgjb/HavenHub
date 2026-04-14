package com.example.havenhub.nettest.request;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceAPI {
  @GET("api/student/info")
    Call<Student>getStudentInfo();

}
