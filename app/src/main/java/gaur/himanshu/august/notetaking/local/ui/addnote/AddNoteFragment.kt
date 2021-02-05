package gaur.himanshu.august.notetaking.local.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import gaur.himanshu.august.notetaking.R
import gaur.himanshu.august.notetaking.databinding.FragmentAddNoteBinding
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.local.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddNoteFragment @Inject constructor(private val firestore: FirebaseFirestore) : Fragment(R.layout.fragment_add_note) {

    lateinit var viewModel: HomeViewModel

    lateinit var binding: FragmentAddNoteBinding

    val args by navArgs<AddNoteFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (args.note != null) {
            args.note?.let {
                binding.addTitle.setText(it.title)
                binding.addDiscription.setText(it.desc)
            }
        }

        binding.saveNote.setOnClickListener {
            val title = binding.addTitle.text.toString().trim()
            val desc = binding.addDiscription.text.toString().trim()

            val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
            val currentDateAndTime: String = simpleDateFormat.format(Date())

            if (AddNoteUtils.validateNote(title, desc)) {
                if (args.note != null) {
                    args.note?.let {
                        it.title = title
                        it.desc = desc
                    }
                    viewModel.updateNote(requireContext(),args.note!!)
                    findNavController().popBackStack()
                } else {

                    viewModel.insertNote(requireContext(),
                        Note(
                            title,
                            desc,
                            currentDateAndTime,
                            email = viewModel.data
                        )
                    )



                    findNavController().popBackStack()
                }
            } else {
                Snackbar.make(binding.root, "Check your Entries", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}