package hr.tvz.android.trcak.briftreger.models

import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import hr.tvz.android.trcak.briftreger.R
import hr.tvz.android.trcak.briftreger.databinding.UserRowNewMessageBinding

class UserItem(private val user: User): BindableItem<UserRowNewMessageBinding>() {

    override fun getLayout() = R.layout.user_row_new_message

    override fun bind(viewBinding: UserRowNewMessageBinding, position: Int) {
        viewBinding.usernameNewMessage.text = user.username

        viewBinding.imageViewNewMessage.setImageURI(user.profileImageUrl)
        //Picasso.get().load(user.profileImageUrl).into(viewBinding.imageViewNewMessage)
    }

    override fun initializeViewBinding(view: View): UserRowNewMessageBinding {
        return UserRowNewMessageBinding.bind(view)
    }


}