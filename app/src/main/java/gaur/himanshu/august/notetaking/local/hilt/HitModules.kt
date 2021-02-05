package gaur.himanshu.august.notetaking.local.hilt

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.local.room.NoteDao
import gaur.himanshu.august.notetaking.local.room.NoteDatabase
import gaur.himanshu.august.notetaking.local.ui.auth.repository.AuthRepository
import gaur.himanshu.august.notetaking.local.ui.auth.repository.IAuthRepository
import gaur.himanshu.august.notetaking.local.ui.home.repository.HomeRepository
import gaur.himanshu.august.notetaking.local.ui.home.repository.IHomeRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HitModules {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class dataStore


    @Singleton
    @Provides
    fun provideNoteDb(@ApplicationContext context: Context): NoteDatabase {
        return NoteDatabase.get(context)
    }


    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.getNoteDb()
    }

    @Singleton
    @Provides
    fun provideFirebaseCloudRefrence():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthentication():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }


    @Singleton
    @Provides
    @dataStore
    fun provideDataStores(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.DATASTORE_NAME,MODE_PRIVATE)
    }

}

@InstallIn(SingletonComponent::class)
@Module
object AuthRepositoryModule{
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth,@HitModules.dataStore dataStore:SharedPreferences):IAuthRepository{
        return AuthRepository(auth,dataStore)
    }

}

@InstallIn(SingletonComponent::class)
@Module
object HomeRepositoryModule{
    @Provides
    fun provideHomeRepository(notedao:NoteDao,@HitModules.dataStore dataStore: SharedPreferences,firestore: FirebaseFirestore): IHomeRepository {
        return HomeRepository(notedao,dataStore,firestore)
    }

}