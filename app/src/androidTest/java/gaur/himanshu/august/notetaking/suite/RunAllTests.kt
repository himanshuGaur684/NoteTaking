package gaur.himanshu.august.notetaking.suite

import gaur.himanshu.august.notetaking.local.ui.auth.LoginFragmentTests
import gaur.himanshu.august.notetaking.local.ui.auth.RegisterFragmentTests
import gaur.himanshu.august.notetaking.local.ui.home.HomeFragmentTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    LoginFragmentTests::class,
    RegisterFragmentTests::class,
    HomeFragmentTest::class
)
@RunWith(Suite::class)
class RunAllTests {
}