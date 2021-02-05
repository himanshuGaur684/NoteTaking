package gaur.himanshu.august.notetaking.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gaur.himanshu.august.notetaking.local.models.Note

@Database(entities = [Note::class], version = 1,exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        fun get(context: Context): NoteDatabase {
            return Room.databaseBuilder(context, NoteDatabase::class.java, "note_db").build()
        }
    }


    abstract fun getNoteDb():NoteDao

}