package ink.terraria.bilitimelinedemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpData(var name: String, var follower: Int, var avatar: Int) : Parcelable
