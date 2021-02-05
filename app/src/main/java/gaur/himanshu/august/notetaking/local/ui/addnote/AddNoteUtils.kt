package gaur.himanshu.august.notetaking.local.ui.addnote

object AddNoteUtils {


    fun validateNote(title: String, desc: String): Boolean {
        if (title.isEmpty() || desc.isEmpty()) {
            return false
        }
        if (title.length <= 2) {
            return false
        }
        if (desc.length <= 2) {
            return false
        }
        return true
    }

}