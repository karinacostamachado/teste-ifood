package com.example.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.example.movies.databinding.GenericErrorFragmentBinding
import com.example.movies.di.providesNavController
import com.example.movies.navigation.MoviesNavigation

class GenericErrorFragment : Fragment(R.layout.generic_error_fragment) {

    private var _binding: GenericErrorFragmentBinding? = null
    private val binding get() = _binding!!

    private val navigation: MoviesNavigation by lazy { providesNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GenericErrorFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPressed()
    }

    private fun onBackButtonPressed() {
        binding.appBar.backButton.setOnClickListener {
            navigation
                .popStackBack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
