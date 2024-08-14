package ir.mhp.snappfoddwar.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity

class LaunchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            // Send user to MainActivity as soon as this activity loads
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // remove this activity from the stack
            finish()
        }, 2500)
    }
}

