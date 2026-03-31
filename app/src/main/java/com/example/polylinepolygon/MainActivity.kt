package com.example.polylinepolygon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.polylinepolygon.ui.theme.PolylinePolygonTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PolylinePolygonTheme {
                MapScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val easternTrail = LatLng(44.34336,-68.24686)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.Builder()
            .target(easternTrail)
            .zoom(12f)
            .build()
    }

    val cadillacTrailPoints = listOf(
        LatLng(44.37851, -68.22968),
        LatLng(44.37779, -68.22898),
        LatLng(44.37705, -68.22844),
        LatLng(44.37667, -68.22885),
        LatLng(44.37613, -68.22922),
        LatLng(44.37583, -68.22943),
        LatLng(44.37539, -68.22991),
        LatLng(44.37474, -68.22972),
        LatLng(44.37417, -68.22970),
        LatLng(44.37391, -68.22914),
        LatLng(44.37257, -68.22955),
        LatLng(44.36935, -68.22842),
        LatLng(44.36869, -68.22903),
        LatLng(44.36802, -68.23018),
        LatLng(44.36711, -68.23030),
        LatLng(44.36581, -68.23125),
        LatLng(44.36491, -68.23135),
        LatLng(44.36399, -68.23112),
        LatLng(44.36368, -68.23059),
        LatLng(44.36278, -68.23013),
        LatLng(44.36244, -68.23044),
        LatLng(44.36197, -68.23045),
        LatLng(44.36118, -68.22996),
        LatLng(44.36032, -68.22854),
        LatLng(44.35985, -68.22833),
        LatLng(44.35899, -68.22703),
        LatLng(44.35815, -68.22633),
        LatLng(44.35678, -68.22609),
        LatLng(44.35496, -68.22565),
        LatLng(44.35427, -68.22570),
        LatLng(44.35370, -68.22550),
        LatLng(44.35341, -68.22568),
        LatLng(44.35305, -68.22534)
    )

    val acadiaMountDesertIsland = listOf(
        LatLng(44.41421, -68.27994),
        LatLng(44.41342, -68.25225),
        LatLng(44.38952, -68.21871),
        LatLng(44.38149, -68.23013),
        LatLng(44.35998, -68.20161),
        LatLng(44.36505, -68.18325),
        LatLng(44.32600, -68.17817),
        LatLng(44.32886, -68.18342),
        LatLng(44.30734, -68.19042),
        LatLng(44.31718, -68.19958),
        LatLng(44.30186, -68.20289),
        LatLng(44.29679, -68.21538),
        LatLng(44.31020, -68.24435),
        LatLng(44.30986, -68.30374),
        LatLng(44.33745, -68.30421),
        LatLng(44.36131, -68.29155),
        LatLng(44.36230, -68.30919),
        LatLng(44.37935, -68.31652),
        LatLng(44.38644, -68.30628),
        LatLng(44.36535, -68.30335),
        LatLng(44.37499, -68.29534),
        LatLng(44.38466, -68.29645),
        LatLng(44.38814, -68.28646),
        LatLng(44.38121, -68.28201),
        LatLng(44.37512, -68.27147),
        LatLng(44.41421, -68.27994),
    )

    var hue by rememberSaveable{ mutableStateOf(0f) }
    var width by rememberSaveable{ mutableStateOf(90f) }
    var showAlert by rememberSaveable{ mutableStateOf(false) }
    var alertMessage by rememberSaveable{ mutableStateOf("") }

    val color = Color.hsv(
        hue = hue,
        saturation = 1f,
        value = 1f,
    )
    val mult = width / 90

    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Polyline(
                points = cadillacTrailPoints,
                color = color,
                width = 8f * mult,
                clickable = true,
                onClick = {
                    alertMessage = "North Ridge Trail, Cadillac Mountain, Maine. Approximately 2 miles long and an elevation gain of 1100 feet."
                    showAlert = true
                }
            )
            Polygon(
                points = acadiaMountDesertIsland,
                strokeColor = color,
                fillColor = color.copy(alpha = 0.1f),
                strokeWidth = 4f * mult,
                clickable = true,
                onClick = {
                    alertMessage = "Acadia National Park, Mt Desert Island, Maine. This section only spans the Bar Harbor side of the park on Mt Desert Island. Acadia National Park also includes areas near Birch Harbor and Southwest Harbor."
                    showAlert = true
                }
            )
        }
        Column(
            modifier = Modifier.padding(42.dp)
        ) {
            SliderCard("Hue", hue, {newVal -> hue = newVal})
            SliderCard("Width", width, {newVal -> width = newVal})
        }
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text(text="Information") },
                text = { Text(alertMessage) },
                confirmButton = { TextButton(
                    onClick = { showAlert = false }
                ) {
                    Text("Ok")
                }
                }
            )
        }
    }
}

@Composable
fun SliderCard(text: String, value: Float, onChange: (Float) -> Unit) {
    Text(text = text)
    Slider(
        value = value,
        valueRange = 0f..360f,
        onValueChange = { newVal -> onChange(newVal) }
    )
}