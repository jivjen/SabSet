package com.example.test1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OCRService {

    @POST("parse/image/")
    Call<OCRResponse> scanOcr(@Body OCRRequest ocrRequest);
}
