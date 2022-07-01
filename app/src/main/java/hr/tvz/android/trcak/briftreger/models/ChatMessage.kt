package hr.tvz.android.trcak.briftreger.models

class ChatMessage(val id: String,
                  val text: String,
                  val fromId: String,
                  val toId: String,
                  val timestamp: Long) {

    override fun toString(): String {
        return "ChatMessage(id='$id', text='$text', fromId='$fromId', toId='$toId', timestamp=$timestamp)"
    }
}