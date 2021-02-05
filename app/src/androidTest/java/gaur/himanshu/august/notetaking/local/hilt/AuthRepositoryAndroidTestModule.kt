package gaur.himanshu.august.notetaking.local.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import gaur.himanshu.august.notetaking.local.ui.auth.repository.FakeAuthRepositoryAndroidTest
import gaur.himanshu.august.notetaking.local.ui.auth.repository.IAuthRepository
import gaur.himanshu.august.notetaking.local.ui.home.repository.HomeRepositoryTest
import gaur.himanshu.august.notetaking.local.ui.home.repository.IHomeRepository
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AuthRepositoryModule::class]
)
@Module
class AuthRepositoryAndroidTestModule {

    @Provides
    @Singleton
    fun provideFakeRepository(): IAuthRepository {
        return FakeAuthRepositoryAndroidTest()
    }


}

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HomeRepositoryModule::class]
)
@Module
class HomeRepositoryAndroidTestModule {

    @Provides
    @Singleton
    fun provideFakeHomeRepository(): IHomeRepository {
        return HomeRepositoryTest()
    }

}