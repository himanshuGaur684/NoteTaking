package gaur.himanshu.august.notetaking.local.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import gaur.himanshu.august.notetaking.databinding.FragmentLoginBinding
import javax.inject.Inject


class LoginFragment @Inject constructor(private val auth: FirebaseAuth) :
    Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        binding.loginButton.setOnClickListener {

            val username = binding.loginUsername.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (AuthUtils.validatingLoginInput(username, password)) {
                viewModel.login(username, password)
            } else {
                Snackbar.make(binding.root, "Login Failed", Snackbar.LENGTH_SHORT).show()
            }


        }

        viewModel.flag.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                    binding.loginProgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {

                    binding.loginProgress.visibility = View.GONE
                }
                Status.SUCCESS -> {

                    binding.loginProgress.visibility = View.GONE
                    startActivity(Intent(requireContext(),ContainerActivity::class.java))
                    requireActivity().finish()
                }
            }
        }

        binding.navigateToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}