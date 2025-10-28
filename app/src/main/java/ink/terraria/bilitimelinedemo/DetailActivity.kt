package ink.terraria.bilitimelinedemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalActivity.current
            val upData = intent.getParcelableExtra<UpData>("UP_DATA")!!
            Surface(
                color = Color(0xfffff8f6),
                modifier = Modifier.fillMaxSize()
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "UP主页") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xfffff8f6),
                                titleContentColor = Color.Black
                            )
                        )
                    }
                )
                { paddingValues ->

                    Card(
                        colors = CardColors(
                            Color(0xfffcebe7),
                            Color.Black,
                            Color.Gray,
                            Color.DarkGray
                        ),
                        modifier = Modifier
                            .padding(paddingValues)
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                Card(
                                    shape = RoundedCornerShape(100),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 8.dp
                                    ),
                                ) {
                                    Image(
                                        painter = painterResource(upData.avatar),
                                        contentDescription = upData.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(125.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.padding(15.dp))
                                Column {
                                    Text(
                                        text = upData.name,
                                        fontSize = 50.sp,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                    Button(
                                        onClick = {
                                            val intent = Intent().apply {
                                                putExtra("UNFOLLOW", upData.name)
                                            }
                                            setResult(RESULT_OK, intent)
                                            Toast.makeText(context, "取关成功", Toast.LENGTH_SHORT)
                                                .show()
                                            finish()
                                        },
                                        colors = ButtonColors(
                                            containerColor = Color(0xfffedad4),
                                            contentColor = Color.Black,
                                            Color.Gray,
                                            Color.DarkGray
                                        ),
                                        shape = RoundedCornerShape(10)
                                    ) {
                                        Text("取消关注")
                                    }
                                }


                            }

                            Text(
                                text = "粉丝: ${upData.follower}",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

