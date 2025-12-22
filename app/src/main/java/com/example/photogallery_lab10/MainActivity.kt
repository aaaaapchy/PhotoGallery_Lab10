package com.example.photogallery_lab10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.photogallery_lab10.ui.theme.PhotoGallery_Lab10Theme

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
                        PhotoGalleryScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoGalleryScreen(modifier: Modifier = Modifier) {
    val photoViewModel: PhotoViewModel = viewModel()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = PhotoRepository(database.photoDao())
    val viewModelDB: ViewModelDB = viewModel(factory = ViewModelDB.factory(repository))

    var isFavoriteScreen by remember { mutableStateOf(false) }
    var isFavoriteBD by remember { mutableStateOf(false) }
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val favoritePhotos = remember { mutableStateListOf<PhotoDB>() }

    LaunchedEffect(isFavoriteScreen || isFavoriteBD) {
        if (isFavoriteScreen || isFavoriteBD) {
            favoritePhotos.clear()
            favoritePhotos.addAll(repository.getFavorites())
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        //Заголовок
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFF30281D)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isFavoriteScreen || isFavoriteBD) {
                    IconButton(onClick = {
                        isFavoriteScreen = false
                        isFavoriteBD = false
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color(0xFFEEF7F6),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Text(
                    text = when {
                        isFavoriteBD -> "Список"
                        isFavoriteScreen -> "Избранное"
                        else -> "PhotoGallery"
                    },
                    color = Color(0xFFEEF7F6),
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(
                        start = if (isFavoriteScreen || isFavoriteBD) 0.dp else 16.dp
                    )
                )
            }
            //Лупапупа
            Row {
                if (!isFavoriteScreen && !isFavoriteBD) {
                    IconButton(onClick = { isSearchExpanded = !isSearchExpanded }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Поиск",
                            tint = Color(0xFFEEF7F6),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                var menuExpanded by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Меню",
                            tint = Color(0xFFEEF7F6),
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        containerColor = Color(0xFF30281D),
                        offset = DpOffset(0.dp, 18.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Избранное",
                                    color = Color(0xFFEEF7F6),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 16.sp
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                isFavoriteScreen = true
                                isFavoriteBD = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Список избранных в БД",
                                    color = Color(0xFFEEF7F6),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 16.sp
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                isFavoriteBD = true
                                isFavoriteScreen = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Удалить все избранные",
                                    color = Color(0xFFEEF7F6),
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 16.sp
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                viewModelDB.clearAllFavorites()
                                favoritePhotos.clear()
                            }
                        )
                    }
                }
            }
        }
        //Поиск
        AnimatedVisibility(
            visible = isSearchExpanded && !isFavoriteScreen && !isFavoriteBD,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF30281D))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text("Введите запрос", color = Color(0xFFAAAAAA))
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xFFEEF7F6),
                        unfocusedTextColor = Color(0xFFEEF7F6),
                        cursorColor = Color(0xFFEEF7F6),
                        focusedIndicatorColor = Color(0xFFEEF7F6),
                        unfocusedIndicatorColor = Color(0xFF777777)
                    ),
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Очистить",
                                    tint = Color(0xFFAAAAAA)
                                )
                            }
                        }
                    }
                )

                TextButton(onClick = {
                    photoViewModel.searchPhotos(
                        if (searchQuery.isBlank()) null else searchQuery.trim()
                    )
                }) {
                    Text(
                        "Найти",
                        color = Color(0xFFEEF7F6),
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }

        when {
            isFavoriteBD -> {
                FavoritesBDTableScreen(favoritePhotos)
            }

            isFavoriteScreen -> {
                if (favoritePhotos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Избранное пусто",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(favoritePhotos) { photo ->
                            FavoritePhotoItem(photo) {
                                viewModelDB.deleteFavorite(photo.id)
                                favoritePhotos.remove(photo)
                            }
                        }
                    }
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(photoViewModel.photos) { photo ->
                        PhotoItem(photo, viewModelDB)
                    }
                }
            }
        }
    }
}

@Composable
fun FavoritesBDTableScreen(favoritePhotos: List<PhotoDB>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Список избранных фотографий в БД (${favoritePhotos.size})",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favoritePhotos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет данных в базе", color = Color.White, fontSize = 18.sp)
            }
        } else {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF30281D))
                        .padding(10.dp)
                ) {
                    Text("ID", modifier = Modifier.weight(1f), color = Color(0xFFEEF7F6), fontSize = 14.sp)
                    Text("Название", modifier = Modifier.weight(2f), color = Color(0xFFEEF7F6), fontSize = 14.sp)
                    Text("URL", modifier = Modifier.weight(3f), color = Color(0xFFEEF7F6), fontSize = 14.sp)
                }

                favoritePhotos.forEach { photo ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1E1810))
                            .padding(10.dp)
                    ) {
                        Text(photo.id, modifier = Modifier.weight(1f), color = Color(0xFFAAAAAA), fontSize = 12.sp, maxLines = 1)
                        Text(photo.title.ifBlank { "<Без названия>" }, modifier = Modifier.weight(2f), color = Color.White, fontSize = 13.sp, maxLines = 2)
                        Text(photo.url, modifier = Modifier.weight(3f), color = Color(0xFF88CC88), fontSize = 10.sp, maxLines = 2)
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, viewModel: ViewModelDB) {
    AsyncImage(
        model = photo.imageUrl(),
        contentDescription = photo.title,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                viewModel.addToFavorites(
                    PhotoDB(
                        id = photo.id,
                        title = photo.title,
                        url = photo.imageUrl()
                    )
                )
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FavoritePhotoItem(photo: PhotoDB, onDelete: () -> Unit) {
    Box {
        AsyncImage(
            model = photo.url,
            contentDescription = photo.title,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(32.dp)
                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Удалить",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
