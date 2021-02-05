package gaur.himanshu.august.notetaking

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import gaur.himanshu.august.notetaking.local.adapters.HomeAdapter
import gaur.himanshu.august.notetaking.local.ui.addnote.AddNoteFragment
import gaur.himanshu.august.notetaking.local.ui.auth.LoginFragment
import gaur.himanshu.august.notetaking.local.ui.auth.RegisterFragment
import gaur.himanshu.august.notetaking.local.ui.auth.repository.IAuthRepository
import gaur.himanshu.august.notetaking.local.ui.home.HomeFragment
import javax.inject.Inject

class NoteTakingFragmentFactory @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val iAuthRepository: IAuthRepository
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {

            HomeFragment::class.java.name -> {
                return HomeFragment(firestore)
            }
            LoginFragment::class.java.name -> {
                return LoginFragment(auth)
            }

            RegisterFragment::class.java.name -> {
                return RegisterFragment(auth)
            }

            AddNoteFragment::class.java.name->{
                return AddNoteFragment(firestore)
            }
            else -> {
                return super.instantiate(classLoader, className)
            }

        }

    }
}