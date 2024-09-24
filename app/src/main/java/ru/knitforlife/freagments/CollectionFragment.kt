package ru.knitforlife.freagments

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import otus.gpb.recyclerview.callback.SwipeCallBack
import ru.knitforlife.R
import ru.knitforlife.adapter.MyColorRecyclerViewAdapter
import ru.knitforlife.databinding.FragmentCollectionListBinding
import ru.knitforlife.listner.ColorClickListner

import ru.knitforlife.viewmodel.ColorsViewModel
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class CollectionFragment @Inject constructor(
    val colorFragment: ColorFragment
) : Fragment() {

    private var columnCount = 1
    private lateinit var myColorRecyclerViewAdapter: MyColorRecyclerViewAdapter
    private val viewModel: ColorsViewModel by activityViewModels()
    private var _binding:FragmentCollectionListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = FragmentCollectionListBinding.inflate(layoutInflater)
//
//        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_collection_list, container, false)
//        if (view is RecyclerView) {
//            with(view) {
//                adapter = myColorRecyclerViewAdapter
//            }
//        }
        _binding = FragmentCollectionListBinding.inflate(inflater, container, false)
        val view = binding.root
        subscribe()
        return view
    }

   fun subscribe() {
        viewModel.colors.observe(viewLifecycleOwner) { color ->
            // Update UI with the new count value
            myColorRecyclerViewAdapter.addList(color)
        }

        configureRecycler()
        setupSwipe(binding.list)
        viewModel.load()
    }

    fun configureRecycler() {
        myColorRecyclerViewAdapter = MyColorRecyclerViewAdapter(object : ColorClickListner{
            override fun onItemClick(id: String) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag,colorFragment)
                    .addToBackStack("collection")
                    .commit()
            }

        })
        binding.list.addItemDecoration(getListRecyclerDecoration())
        binding.list.adapter = myColorRecyclerViewAdapter
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    viewModel.load()
                }
            }
        })


    }

    private fun setupSwipe(recyclerView: RecyclerView) {
        val iconMarginDpRight = 29
        val iconMarginDpTop = 16
        val background = Paint()
        val icon = AppCompatResources.getDrawable(requireContext(),R.drawable.archive_24px)

        background.color = requireContext().getColor(R.color.backgroundDelete)
        val callback = SwipeCallBack(
            density = resources.displayMetrics.density,
            scaledDensity = resources.displayMetrics.scaledDensity,
            iconMarginDpRight = iconMarginDpRight,
            iconMarginDpTop = iconMarginDpTop,
            swipeAction = {
                val position = it.adapterPosition
                myColorRecyclerViewAdapter.remove(position)
            },
            background = background,
            icon = icon
        )
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getListRecyclerDecoration(): RecyclerView.ItemDecoration {
        val dividerDrawable =
            AppCompatResources.getDrawable(requireContext(), R.drawable.separator)
        return DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
            dividerDrawable?.let {
                setDrawable(it)
            }
        }
    }
}