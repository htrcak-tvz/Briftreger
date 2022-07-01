package hr.tvz.android.trcak.briftreger.models

data class User(val uid: String? = null, val username: String? = null, val profileImageUrl: String? = null) {

    override fun toString(): String {
        return "User(uid='$uid', username='$username', profileImageUrl='$profileImageUrl')"
    }
}