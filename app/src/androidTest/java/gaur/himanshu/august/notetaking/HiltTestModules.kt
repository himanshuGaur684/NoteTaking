package gaur.himanshu.august.notetaking

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.august.notetaking.local.room.NoteDatabase
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object HiltTestModules {

    @Provides
    @Named("fake_db")
    fun provideRoomDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries().build()
    }

}