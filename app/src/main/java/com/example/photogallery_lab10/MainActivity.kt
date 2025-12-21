package com.example.photogallery_lab10

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photogallery_lab10.ui.theme.PhotoGallery_Lab10Theme

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PhotoGallery_Lab10Theme {

                MaterialTheme(
                    colorScheme = lightColorScheme(
                        primary = Color(0xFF808080),
                        surface = Color.Gray,
                        background = Color.Gray
                    )
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    ) { innerPadding ->
                        PhotoGalleryScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun PhotoGalleryScreen(modifier: Modifier) {

    Column(modifier) {
        //Заголовок, сюда же потом панель интструментов
        Row(modifier= Modifier.fillMaxWidth().height(80.dp).background(Color(0xFF30281D)), verticalAlignment = Alignment.CenterVertically){
        Text("PhotoGallery", modifier=Modifier.padding(10.dp), color = Color(0xFFEEF7F6), fontFamily = FontFamily.Serif, fontSize = 30.sp)

        }

        //Список фото
        Row() {




        }
    }
}


