package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()

        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        onDeleteCity = { cityRepository.deleteCity(it) },
                        modifier = Modifier.padding( paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

class CityRepository()
{
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow",
        "Sydney", "Berlin", "Vienna",
        "Tokyo", "Beijing", "Osaka",
        "New Delhi"
    )

    val cities: List<String>
        get() = _cities

    fun addCity(city: String)
    {
        _cities.add(city)
    }

    fun deleteCity(ind: Int)
    {
        _cities.removeAt(ind)
    }
}

@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onDeleteCity: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf(value = "") }
    var selCityInd by remember { mutableStateOf(value = -1) }
    Column(modifier = modifier.fillMaxSize())
    {
        Row(modifier = Modifier.padding(all = 16.dp))
        {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column()
            {
                Button(
                    onClick = {
                        if (newCityName.isNotBlank())
                        {
                            onAddCity(newCityName)
                            newCityName = ""
                        }
                    }

                )
                {
                    Text("Add City")
                }
                Button(
                    onClick = {
                        if (selCityInd in cities.indices)
                        {
                            onDeleteCity(selCityInd)
                        }
                    }
                )
                {
                    Text("Delete City")
                }
            }
        }
        LazyColumn(modifier = modifier.fillMaxSize())
        {
            itemsIndexed(cities)
            { ind, city ->
                CityRow(
                    city = city,
                    isSelected = selCityInd == ind,
                    onClick = { selCityInd = ind }
                )
            }
        }
    }
}

@Composable
fun CityRow(
    city: String,
    isSelected: Boolean,
    onClick: () -> Unit
)
{
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .background(
                if (isSelected)
                    Color.Gray
                else
                    Color.Transparent
            )
            .selectable(
                selected = isSelected,
                onClick = onClick
            )
    )
}