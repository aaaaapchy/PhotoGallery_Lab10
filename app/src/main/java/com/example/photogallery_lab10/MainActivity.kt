package com.example.photogallery_lab10

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photogallery_lab10.ui.theme.PhotoGallery_Lab10Theme
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset

import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
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
    val photoViewModel: PhotoViewModel = viewModel()
    Column(modifier) {

        //Верхняя панель
        Row(
            modifier = Modifier.fillMaxWidth().height(70.dp).background(Color(0xFF30281D)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Заголовок
            Text(
                "PhotoGallery",
                modifier = Modifier.padding(10.dp),
                color = Color(0xFFEEF7F6),
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp
            )
            var expanded by remember { mutableStateOf(false) }
            //Поиск
            IconButton(onClick = {  }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Информация о приложении", modifier = Modifier.size(30.dp),
                    tint = Color(0xFFEEF7F6)
                )
            }
            //Выпадающее меню
            Box() {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert,
                        contentDescription = "Показать меню",
                        tint = Color(0xFFEEF7F6),
                        modifier = Modifier.size(30.dp))
                }
                DropdownMenu(
                    expanded = expanded,
                    containerColor = Color(0xFF30281D),
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 0.dp, y = 18.dp)

                ) {
                    DropdownMenuItem(
                        onClick = {},
                        text = { Text("Перейти в избранное",modifier = Modifier.padding(10.dp),
                            color = Color(0xFFEEF7F6),
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp ) }
                    )
                    DropdownMenuItem(
                        onClick = {  },
                        text = { Text("Удалить все в избранном",modifier = Modifier.padding(10.dp),
                            color = Color(0xFFEEF7F6),
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp ) }
                    )

                }
            }
        }


        //Список фото
        Row() {

            /*Text(
                text = "Найдено: ${photoViewModel.photos.size} фото",
                modifier = Modifier.padding(16.dp),
                color = Color.White,
                fontSize = 18.sp
            )*/
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(photoViewModel.photos) { photo ->
                    PhotoItem(photo = photo)
                }
            }


        }
    }
}


@Composable
fun PhotoItem(photo: Photo) {
    AsyncImage(
        model = photo.imageUrl(),
        contentDescription = photo.title,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        placeholder = rememberAsyncImagePainter(android.R.drawable.ic_menu_gallery),  // Заглушка
        error = rememberAsyncImagePainter(android.R.drawable.stat_notify_error)
    )
}