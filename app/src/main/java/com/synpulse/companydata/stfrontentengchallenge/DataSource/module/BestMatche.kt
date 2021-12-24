package com.synpulse.companydata.stfrontentengchallenge.DataSource.module


import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestMatche(
    @SerialName("8. currency")
    val currency: String?,
    @SerialName("6. marketClose")
    val marketClose: String?,
    @SerialName("5. marketOpen")
    val marketOpen: String?,
    @SerialName("9. matchScore")
    val matchScore: String?,
    @SerialName("2. name")
    val name: String?,
    @SerialName("4. region")
    val region: String?,
    @SerialName("1. symbol")
    val symbol: String?,
    @SerialName("7. timezone")
    val timezone: String?,
    @SerialName("3. type")
    val type: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency)
        parcel.writeString(marketClose)
        parcel.writeString(marketOpen)
        parcel.writeString(matchScore)
        parcel.writeString(name)
        parcel.writeString(region)
        parcel.writeString(symbol)
        parcel.writeString(timezone)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BestMatche> {
        override fun createFromParcel(parcel: Parcel): BestMatche {
            return BestMatche(parcel)
        }

        override fun newArray(size: Int): Array<BestMatche?> {
            return arrayOfNulls(size)
        }
    }
}