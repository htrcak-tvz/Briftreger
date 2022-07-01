package hr.tvz.android.trcak.briftreger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupieAdapter
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ActivityChatLogBinding
import hr.tvz.android.trcak.briftreger.models.ChatItems
import hr.tvz.android.trcak.briftreger.models.User

class ChatLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        if (user != null) {
            supportActionBar?.title = user.username
        }

        setupDummyData()
    }

    private fun setupDummyData() {
        val adapter = GroupieAdapter()

        adapter.add(ChatItems.ChatFromItem("From text sending to you"))
        adapter.add(ChatItems.ChatToItem("Chat text returning back\nSending some more\noh wow"))
        adapter.add(ChatItems.ChatToItem("Chat text returning back\nSending some more\noh wow"))
        adapter.add(ChatItems.ChatToItem("Chat text returning back\nSending some more\noh wow"))
        adapter.add(ChatItems.ChatFromItem("From text sending to you"))
        adapter.add(ChatItems.ChatToItem("Chat text returning back\nSending some more\noh wow"))
        adapter.add(ChatItems.ChatFromItem("From text sending to you"))

        binding.RVChatLog.adapter = adapter
    }

}