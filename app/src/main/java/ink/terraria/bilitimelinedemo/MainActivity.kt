package ink.terraria.bilitimelinedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val upData = mutableStateListOf<UpData>()
    private val postData = mutableStateListOf<PostData>()

    private val detailActivityLuncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val unfollowUpName = result.data?.getStringExtra("UNFOLLOW")!!

            upData.removeAll { data -> data.name == unfollowUpName }
            postData.removeAll { data -> data.author.name == unfollowUpName }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upData.addAll(DataSender().createUpData())
        postData.addAll(DataSender().createPostData())
        enableEdgeToEdge()
        setContent {
            Surface(
                color = Color(0xfffff8f6),
                modifier = Modifier.fillMaxSize()
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "动态") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xfffff8f6),
                                titleContentColor = Color.Black
                            )
                        )
                    }
                )
                { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardColors(
                                Color(0xfffcebe7),
                                Color.Black,
                                Color.Gray,
                                Color.DarkGray
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            ),
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                itemsIndexed(upData) { index, data ->
                                    UpView(data)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(10.dp))


                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            itemsIndexed(postData) { index, data ->
                                PostView(data)
                            }

                        }


                    }

                }
            }
        }
    }

    @Composable
    fun PostView(postData: PostData) {
        Card(
            shape = RoundedCornerShape(15.dp),
            colors = CardColors(
                Color(0xfffcebe7),
                Color.Black,
                Color.Gray,
                Color.DarkGray
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Row {
                    Card(shape = RoundedCornerShape(100)) {
                        Image(
                            painter = painterResource(postData.author.avatar),
                            contentDescription = postData.author.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(2.dp))

                    Column {
                        Text(
                            text = postData.author.name,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Text(
                            text = postData.postDate.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                Text(
                    text = postData.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Card(shape = RoundedCornerShape(5)) {
                    Image(
                        painter = painterResource(postData.cover),
                        contentDescription = postData.author.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }

        }
    }

    @Composable
    fun UpView(upData: UpData) {
        val context = LocalContext.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("UP_DATA", upData)
                        detailActivityLuncher.launch(intent)
                    },
                    indication = null,
                    interactionSource = null
                )
        ) {

            Card(
                shape = RoundedCornerShape(100),
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Image(
                    painter = painterResource(upData.avatar),
                    contentDescription = upData.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(65.dp)
                )

            }
            Text(
                text = upData.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
