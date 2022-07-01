package hr.tvz.android.trcak.briftreger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.tvz.android.trcak.briftreger.R

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = getString(R.string.chat_log)
    }
}