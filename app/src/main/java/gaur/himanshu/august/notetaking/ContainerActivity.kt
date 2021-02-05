package gaur.himanshu.august.notetaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: NoteTakingFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory=fragmentFactory
        setContentView(R.layout.activity_container)
    }
}