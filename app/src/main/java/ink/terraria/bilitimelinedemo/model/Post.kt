package ink.terraria.bilitimelinedemo.model

import androidx.annotation.DrawableRes
import java.time.LocalDate


data class Post(
    var author: Up,
    var title: String,
    @field:DrawableRes var cover: Int,
    var postDate: LocalDate
)

