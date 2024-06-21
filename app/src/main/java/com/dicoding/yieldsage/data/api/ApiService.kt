package com.dicoding.yieldsage.data.api

import com.dicoding.yieldsage.data.response.CreateProjectResponse
import com.dicoding.yieldsage.data.response.GetNDVIResponse
import com.dicoding.yieldsage.data.response.GetProjectIdResponse
import com.dicoding.yieldsage.data.response.GetProjectResponse
import com.dicoding.yieldsage.data.response.GetResultIdResponse
import com.dicoding.yieldsage.data.response.GetResultResponse
import com.dicoding.yieldsage.data.response.LoginResponse
import com.dicoding.yieldsage.data.response.PredictResponse
import com.dicoding.yieldsage.data.response.RegisterResponse
import com.dicoding.yieldsage.data.response.UploadImageResponse
import com.dicoding.yieldsage.data.response.UploadNDVIResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body requestBody: RequestBody
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): LoginResponse

    @GET("projects")
    suspend fun getProjects(): GetProjectResponse

    @POST("projects")
    suspend fun createProject(
        @Body requestBody: RequestBody
    ): CreateProjectResponse

    @GET("projects/{id}")
    suspend fun getProjectId(
        @Path("id") id: String
    ): GetProjectIdResponse

    @Multipart
    @POST("projects/upload/{id}")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Path("id") id: String
    ): UploadImageResponse

    @POST("projects/predict/{id}")
    suspend fun predict(
        @Path("id") id: String
    ): PredictResponse

    @GET("projects/result")
    suspend fun getResults(): GetResultResponse

    @GET("projects/result/{id}")
    suspend fun getResultId(
        @Path("id") id: String
    ): GetResultIdResponse

    @GET("projects/ndvi-mapping")
    suspend fun getNdvi(): GetNDVIResponse

    @Multipart
    @POST("projects/ndvi-mapping")
    suspend fun uploadNdvi(
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part
    ): UploadNDVIResponse
}