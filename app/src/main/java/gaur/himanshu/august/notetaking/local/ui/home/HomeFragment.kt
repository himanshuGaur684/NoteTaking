package gaur.himanshu.august.notetaking.local.ui.home

import android.app.AlertDialog
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
import com.google.firebase.firestore.FirebaseFirestore
import gaur.himanshu.august.notetaking.LoginActivity
import gaur.himanshu.august.notetaking.R
import gaur.himanshu.august.notetaking.Status
import gaur.himanshu.august.notetaking.databinding.FragmentHomeBinding
import gaur.himanshu.august.notetaking.local.adapters.HomeAdapter
import gaur.himanshu.august.notetaking.local.adapters.HomeAdaptersClickListioners
import gaur.himanshu.august.notetaking.local.models.Note
import javax.inject.Inject

class HomeFragment @Inject constructor(
    private val firestore: FirebaseFirestore
) :
    Fragment(R.layout.fragment_home), HomeAdaptersClickListioners {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding


    val homeAdapter = HomeAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)



        setRecyclerView()



        binding.swipeRefreshHome.setOnRefreshListener {
            viewModel.getAllNotes()
            binding.swipeRefreshHome.isRefreshing = false
        }


        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.log_out -> {
                    FirebaseAuth.getInstance().signOut()
                    findNavController().popBackStack()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                    return@setOnMenuItemClickListener true
                }
                R.id.sync_data -> {
                    viewModel.syncDataFromNetwork()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }

        }


        viewModel.list.observe(viewLifecycleOwner) {
            when (it.peekContent().status) {
                Status.ERROR -> {
                    binding.swipeRefreshHome.isRefreshing=false
                Snackbar.make(binding.root,it.peekContent().message.toString(),Snackbar.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    binding.swipeRefreshHome.isRefreshing=false
                    homeAdapter.setContentList(it.peekContent().data!!)
                }
                Status.LOADING -> {
                    binding.swipeRefreshHome.isRefreshing=true
                }
            }
        }

        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    private fun setRecyclerView() {
        binding.homeRecycler.adapter = homeAdapter
    }

    override fun clickListioners(note: Note, position: Int) {

        AlertDialog.Builder(requireContext()).setTitle("Delete ").setMessage("\nAre you Sure?")
            .setPositiveButton("Sure") { _, _ ->
                viewModel.deleteNote(requireContext(), note)
            }
            .setNegativeButton("Not Sure") { _, _ ->
                Snackbar.make(requireView(), "Not Deleted", Snackbar.LENGTH_SHORT).show()
            }.show()

    }

    override fun detailsClickListioners(note: Note, position: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(note))
    }
}


