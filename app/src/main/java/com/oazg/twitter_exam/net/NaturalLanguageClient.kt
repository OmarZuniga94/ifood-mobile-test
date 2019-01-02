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
        val builder = Retrofit.Builder().baseUrl("https://language.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create()).build()
        return builder.create(GetNaturalLanguageService::class.java)
    }

    interface GetNaturalLanguageService {
        @POST("/v1/documents:analyzeSentiment")
        fun analyzeText(@Query("key") auth: String, @Body request: AnalyzerRequest): Call<AnalyzerResult>
    }
}

/* WEB SERVICE REQUEST */
class AnalyzerRequest(@SerializedName("document") val document: Document,
                      @SerializedName("encodingType") val encodingType: String = "UTF8"
)

/* Document object inside the analyze request */
class Document(@SerializedName("content") val content: String,
               @SerializedName("type") val type: String = "PLAIN-TEXT"
)

/* WEB SERVICE RESULT */
class AnalyzerResult(@SerializedName("documentSentiment") val documentSentiment: DocumentSentiment)

/* Document Sentiment inside the analyze result, which has the magnitude and the score of text analyzed */
class DocumentSentiment(@SerializedName("magnitude") val magnitude: Double,
                        @SerializedName("score") val score: Double)