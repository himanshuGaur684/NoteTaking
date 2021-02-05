package gaur.himanshu.august.notetaking.local.room

import androidx.room.*
import gaur.himanshu.august.notetaking.local.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
       suspend fun delete(note: Note)

    @Query("SELECT * FROM Note WHERE email == :email")
    suspend fun getAllNotes(email:String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<Note>)

    @Update
    suspend fun update(note: Note)
}