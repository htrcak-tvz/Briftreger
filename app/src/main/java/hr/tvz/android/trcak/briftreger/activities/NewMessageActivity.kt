package hr.tvz.android.trcak.briftreger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.GroupieViewHolder
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.ActivityNewMessageBinding
import hr.tvz.android.trcak.briftreger.models.UserItem

class NewMessageActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = getString(R.string.select_user)

        val adapter = GroupieAdapter()
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())

        binding.recyclerviewNewmessage.adapter = adapter
    }
}