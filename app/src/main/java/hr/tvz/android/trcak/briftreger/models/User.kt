package hr.tvz.android.trcak.briftreger.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val uid: String? = null, val username: String? = null, val profileImageUrl: String? = null): Parcelable {

    override fun toString(): String {
        return "User(uid='$uid', username='$username', profileImageUrl='$profileImageUrl')"
    }
}