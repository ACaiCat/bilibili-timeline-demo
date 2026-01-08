package ink.terraria.bilitimelinedemo

import ink.terraria.bilitimelinedemo.model.Post
import ink.terraria.bilitimelinedemo.model.Up
import java.util.Collections.emptyList

data class TimeLineUiState(
    val ups: MutableList<Up> = emptyList(),
    val posts: MutableList<Post> = emptyList()
)