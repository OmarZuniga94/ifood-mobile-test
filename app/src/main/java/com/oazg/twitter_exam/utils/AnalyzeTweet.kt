package com.oazg.twitter_exam.utils

import android.os.Parcel
import android.os.Parcelable

class AnalyzeTweet(val tweet: String, val urlPhoto: String, val score: Double, val magnitude: Double,
                   val name: String, val screenName: String, val dateTweeted: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tweet)
        parcel.writeString(urlPhoto)
        parcel.writeDouble(score)
        parcel.writeDouble(magnitude)
        parcel.writeString(name)
        parcel.writeString(screenName)
        parcel.writeString(dateTweeted)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnalyzeTweet> {
        override fun createFromParcel(parcel: Parcel): AnalyzeTweet {
            return AnalyzeTweet(parcel)
        }

        override fun newArray(size: Int): Array<AnalyzeTweet?> {
            return arrayOfNulls(size)
        }
    }
}