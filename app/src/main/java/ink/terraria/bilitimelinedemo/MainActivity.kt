package ink.terraria.bilitimelinedemo


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ink.terraria.bilitimelinedemo.model.Post
import ink.terraria.bilitimelinedemo.model.Up
import ink.terraria.bilitimelinedemo.ui.theme.TimeLineTheme


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    val viewModel: TimeLineViewModel by viewModels()
    val detailActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            return@registerForActivityResult
        }

        val unfollowUpName =
            result.data?.getStringExtra("UNFOLLOW") ?: return@registerForActivityResult

        viewModel.removeUp(unfollowUpName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeLineTheme {
                TimeLineScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun TimeLineScreen(
    ) {
        val uiState = viewModel.uiState.collectAsState()
        Surface(
            color = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.timeline),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }) { paddingValues ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(horizontal = 8.dp)
                ) {


                    val displayUPs = uiState.value.posts.filter {
                        (uiState.value.currentUpName.isEmpty() || it.author.name == uiState.value.currentUpName)
                    }

                    item {
                        FollowedUp(
                            uiState.value.ups, Modifier.padding(vertical = 16.dp)
                        )
                    }

                    items(displayUPs) { post ->
                        PostCard(post)
                    }
                }
            }

        }
    }

    @Composable
    fun FollowedUp(
        ups: List<Up>, modifier: Modifier = Modifier
    ) {
        Card(
            shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.colorScheme.onSurface,
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), modifier = modifier.fillMaxWidth()
        ) {
            if (ups.isEmpty()) {
                return@Card
            }


            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                items(ups) { data ->


                    UpAvatar(data)
                }
            }
        }
    }

    @Composable
    fun PostCard(post: Post, modifier: Modifier = Modifier) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.colorScheme.onSurface,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Card(shape = CircleShape) {
                        Image(
                            painter = painterResource(post.author.avatar),
                            contentDescription = post.author.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Column {
                        Text(
                            text = post.author.name,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = post.postDate.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Card(shape = RoundedCornerShape(5)) {
                    Image(
                        painter = painterResource(post.cover),
                        contentDescription = post.author.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
    }

    @Composable
    fun UpAvatar(up: Up, modifier: Modifier = Modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .combinedClickable(onLongClick = {
                    val intent = Intent(this, DetailActivity::class.java).apply {
                        putExtra("UP_DATA", up)
                    }
                    detailActivityLauncher.launch(intent)
                }, onClick = {
                    viewModel.switchUpPost(up.name)
                })) {
            Card(
                shape = CircleShape, modifier = Modifier.padding(8.dp)
            ) {

                Image(
                    painter = painterResource(up.avatar),
                    contentDescription = up.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(65.dp)
                )

            }
            Text(
                text = up.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}