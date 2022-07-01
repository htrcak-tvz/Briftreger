package hr.tvz.android.trcak.briftreger.models

import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import hr.tvz.android.trcak.briftreger.R

class UserItem: Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // will be called in our list for each user object later on...
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }

}