package hr.tvz.android.trcak.briftreger.models

class ChatMessage(val id: String,
                  val text: String,
                  val fromId: String,
                  val toId: String,
                  val timestamp: Long) {

    constructor() : this("","","","",-1)

    override fun toString(): String {
        return "ChatMessage(id='$id', text='$text', fromId='$fromId', toId='$toId', timestamp=$timestamp)"
    }
}