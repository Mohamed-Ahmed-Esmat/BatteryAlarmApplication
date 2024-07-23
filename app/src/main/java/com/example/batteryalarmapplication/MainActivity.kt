package com.example.batteryalarmapplication

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.batteryalarmapplication.ui.theme.BatteryAlarmApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatteryAlarmApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BatteryAlarm(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Composable
fun BatteryAlarm(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var batteryLevel by remember { mutableIntStateOf(0) }

        val batteryReceiver = BatteryChanger { level ->
            batteryLevel = level
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)



    if (batteryLevel < 20) {
        BatteryImage(
            batteryImage = R.drawable.battery_low,
            batteryText = "Battery low",
            modifier = modifier
        )
    } else {
        BatteryImage(
            batteryImage = R.drawable.battery_full,
            batteryText = "Battery charging full",
            modifier = modifier
        )
    }
}

@Composable
fun BatteryImage(
    @DrawableRes batteryImage: Int,
    batteryText: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = batteryImage),
            contentDescription = batteryText,
            modifier = Modifier.size(200.dp).align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BatteryAlarmPreview() {
    BatteryAlarm()
}

