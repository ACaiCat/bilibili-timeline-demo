package ink.terraria.bilitimelinedemo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UpData(var name: String, var follower: Int, var avatar: Int) : Parcelable
