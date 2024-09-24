package ru.knitforlife.freagments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.knitforlife.R
import ru.knitforlife.viewmodel.ColorsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ColorFragment @Inject constructor(): Fragment() {

    companion object {
        fun newInstance() = ColorFragment()
    }

    private val viewModel: ColorsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.colors.value.

        // TODO: Use the ViewModelv
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_color, container, false)
    }
}