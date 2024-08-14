package ir.mhp.snappfoddwar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController
import ir.mhp.utils.constants.SCOPE_ID
import ir.mhp.utils.constants.SCOPE_NAME
import ir.mhp.utils.extension.getOrCreateScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import ir.mhp.snappfoddwar.presentation.navigation.SetUpNavController
import ir.mhp.snappfoddwar.presentation.ui.theme.SnappfoodWar
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModelScope =
            getOrCreateScope(SCOPE_ID, SCOPE_NAME)

    val gson : Gson by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ensure scope is closed when activity is destroyed
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                viewModelScope.close()
                super.onDestroy(owner)
            }
        })
        setContent {
            SnappfoodWar {
                Surface(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val systemUiController = rememberSystemUiController()

                    systemUiController.setSystemBarsColor(
                        color = Color.Black
                    )
                    SetUpNavController(rememberNavController(), viewModelScope,gson)
                }
            }
        }
    }
}