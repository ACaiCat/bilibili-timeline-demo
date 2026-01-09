package ink.terraria.bilitimelinedemo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ink.terraria.bilitimelinedemo.model.Up
import ink.terraria.bilitimelinedemo.ui.theme.TimeLineTheme

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeLineTheme {
                val up = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra("UP_DATA", Up::class.java)
                } else {
                    intent.getParcelableExtra("UP_DATA")
                }

                if (up == null) {
                    Toast.makeText(this, stringResource(R.string.failed_get_up), Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    return@TimeLineTheme
                }

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(R.string.up_homepage),
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }, colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    )
                    { paddingValues ->
                        UpHomepage(
                            up = up,
                            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
                        )
                    }
                }
            }
        }

    }

    @Composable
    fun UpHomepage(up: Up, modifier: Modifier = Modifier) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    shape = CircleShape,
                ) {
                    Image(
                        painter = painterResource(up.avatar),
                        contentDescription = up.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = up.name,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Button(
                        onClick = {
                            val intent = Intent().apply {
                                putExtra("UNFOLLOW", up.name)
                            }
                            setResult(RESULT_OK, intent)
                            Toast.makeText(
                                this@DetailActivity,
                                this@DetailActivity.getString(R.string.unfollow_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        },
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    ) {
                        Text(stringResource(R.string.unfollow))
                    }
                }

                Text(
                    text = stringResource(R.string.followers, up.follower),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}


