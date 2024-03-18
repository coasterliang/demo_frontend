package org.example.hsbcbookfrontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.therouter.TheRouter
import com.therouter.router.Route
import org.example.hsbcbookfrontend.adapter.BookAdapter
import org.example.hsbcbookfrontend.databinding.FragmentFirstBinding
import org.example.hsbcbookfrontend.viewmodel.BookViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@Route(path = "listpage")
class FirstFragment : Fragment() {

    private val adapter : BookAdapter = BookAdapter()
    private var _binding: FragmentFirstBinding? = null
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

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.bookList.adapter = adapter
        bookViewModel.bookListLiveData.observe(viewLifecycleOwner) {
            adapter.removeAtRange(0..<adapter.itemCount)
            adapter.addAll(it)
        }
        binding.query.setOnClickListener {
            val keyword = binding.keyword.text.toString()
            bookViewModel.queryBooks(keyword)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookViewModel.getBookList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}