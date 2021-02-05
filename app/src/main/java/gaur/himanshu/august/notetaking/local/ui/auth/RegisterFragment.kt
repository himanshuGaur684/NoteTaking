package gaur.himanshu.august.notetaking.local.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import gaur.himanshu.august.notetaking.ContainerActivity
import gaur.himanshu.august.notetaking.R
import gaur.himanshu.august.notetaking.Status
import gaur.himanshu.august.notetaking.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment @Inject constructor(private val auth: FirebaseAuth) :
    Fragment(R.layout.fragment_register) {


    lateinit var binding: FragmentRegisterBinding

    lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)


        binding.registerButton.setOnClickListener {
            val username = binding.registerUsername.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            if (AuthUtils.validateRegisterInput(username, password, confirmPassword)) {
                viewModel.register(username, password, confirmPassword)
            } else {
                Snackbar.make(binding.root, "Registration is failed", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.navigateToLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.flag.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressRegister.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Snackbar.make(binding.root, "Registration is failed", Snackbar.LENGTH_SHORT)
                        .show()
                    binding.progressRegister.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressRegister.visibility = View.GONE

                    Snackbar.make(binding.root, "Registration is Successful", Snackbar.LENGTH_SHORT)
                        .show()

                    startActivity(Intent(requireContext(),ContainerActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

}