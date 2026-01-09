package ink.terraria.bilitimelinedemo.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Up(
    var name: String,
    var follower: Int,
    @field:DrawableRes var avatar: Int
) : Parcelable
