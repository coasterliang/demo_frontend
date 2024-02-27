package org.example.hsbcbookfrontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.therouter.TheRouter
import com.therouter.router.Autowired
import com.therouter.router.Route
import org.example.hsbcbookfrontend.databinding.FragmentSecondBinding
import org.example.hsbcbookfrontend.network.model.Book
import org.example.hsbcbookfrontend.viewmodel.BookViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@Route(path = "updatepage")
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    @Autowired(name = "id")
    protected lateinit var id: String
    private val bookViewModel: BookViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheRouter.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addUpdate.setOnClickListener {
            val book = Book(0, binding.title.text.toString(), binding.author.text.toString(), binding.intro.text.toString(),
                binding.pubyear.text.toString().toInt(), binding.isbn.text.toString())
            bookViewModel.addUpdate(book)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}