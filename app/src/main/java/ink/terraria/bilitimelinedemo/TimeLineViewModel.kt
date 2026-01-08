package ink.terraria.bilitimelinedemo

import androidx.lifecycle.ViewModel
import ink.terraria.bilitimelinedemo.data.DataSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TimeLineViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TimeLineUiState())

    val uiState: StateFlow<TimeLineUiState> = _uiState.asStateFlow()

    fun removeUp(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ups = currentState.ups.filterNot { it.name == name }.toMutableList(),
                posts = currentState.posts.filterNot { it.author.name == name }.toMutableList(),
            )
        }
    }

    init {
        _uiState.value = TimeLineUiState(DataSender.createUpData(), DataSender.createPostData())
    }

}