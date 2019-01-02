package com.oazg.twitter_exam.net

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

open class NaturalLanguageClient {

    fun getCustomService(): GetNaturalLanguageService {
        val builder = Retrofit.Builder().baseUrl("https://language.googleapis.com").addConverterFactory(GsonConverterFactory.create()).build()
        return builder.create(GetNaturalLanguageService::class.java)
    }

    interface GetNaturalLanguageService {
        @POST("/v1/documents:analyzeSentiment")
        fun analyzeText(@Query("key") auth: String, @Body request: AnalyzerRequest): Call<AnalyzerResult>
    }
}

/* Request Class for the Web Service */
class AnalyzerRequest(@SerializedName("document") val document: AnalyzerHelper)

/* Analyze helper inside the request json */
class AnalyzerHelper(@SerializedName("content") val content: String,
                     @SerializedName("type") val type: String = "PLAIN-TEXT")

/* Result Class for the Web Service */
class AnalyzerResult(@SerializedName("document") val document: AnalyzerHelper,
                     @SerializedName("encodingType") val encodingType: String = "UTF8")