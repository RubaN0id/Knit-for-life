package ru.knitforlife.color.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import ru.knitforlife.color.R
import ru.knitforlife.color.viewmodel.ColorsViewModel
import javax.inject.Inject


class ColorFragment @Inject constructor(): Fragment() {

    companion object {
        fun newInstance() = ColorFragment()
    }

    private lateinit var  viewModel: ColorsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_color, container, false)
    }
}