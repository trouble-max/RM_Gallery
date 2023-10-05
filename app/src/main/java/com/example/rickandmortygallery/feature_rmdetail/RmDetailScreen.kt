package com.example.rickandmortygallery.feature_rmdetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickandmortygallery.data.remote.responses.Character
import com.example.rickandmortygallery.util.Resource

@Composable
fun CharacterDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: RmDetailViewModel = hiltViewModel()
) {
    val characterInfo = produceState<Resource<Character>>(initialValue = Resource.Loading()) {
        value = viewModel.getCharacterDetail(id)
    }.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp)
    ) {
        CharacterDetailStateWrapper(
            characterInfo = characterInfo,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .size(36.dp)
                .offset(24.dp, 24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .clickable { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = characterInfo.data?.name,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun CharacterDetailStateWrapper(
    characterInfo: Resource<Character>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when(characterInfo) {
        is Resource.Success -> {
            CharacterDetail(characterInfo = characterInfo.data!!)
        }
        is Resource.Error -> {
            Text(
                text = characterInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetail(
    characterInfo: Character,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column {
            GlideImage(
                model = characterInfo.image,
                contentDescription = characterInfo.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = characterInfo.name,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                val text =
                    "${characterInfo.status} - ${characterInfo.species} - ${characterInfo.gender}"
                Canvas(
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically),
                    onDraw = {
                        val color = when (characterInfo.status) {
                            "Alive" -> Color.Green
                            "Dead" -> Color.Red
                            else -> Color.Gray
                        }
                        drawCircle(color = color)
                    })
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Last seen location: " + characterInfo.location.name)
        }
    }
}