package ru.knitforlife.color.fragments

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.knitforlife.color.R
import ru.knitforlife.color.adapter.MyColorRecyclerViewAdapter
import ru.knitforlife.color.callback.SwipeCallBack
import ru.knitforlife.color.databinding.FragmentCollectionListBinding
import ru.knitforlife.color.listner.ColorClickListner
import ru.knitforlife.color.viewmodel.ColorsViewModel
import javax.inject.Inject
import kotlin.getValue

/**
 * A fragment representing a list of Items.
 */

@AndroidEntryPoint
class CollectionFragment @Inject constructor(
    val colorFragment: ColorFragment
) : Fragment() {

    private var columnCount = 1
    private lateinit var myColorRecyclerViewAdapter: MyColorRecyclerViewAdapter
    val viewModel: ColorsViewModel by viewModels()
    private var _binding:FragmentCollectionListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCollectionListBinding.inflate(inflater, container, false)
        val view = binding.root
        subscribe()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

   fun subscribe() {


       viewModel.colorFlow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
           .onEach { myColorRecyclerViewAdapter.addList(it!!) }
           .launchIn(lifecycleScope)


        configureRecycler()
        setupSwipe(binding.list)

    }

    fun configureRecycler() {

        // Временно убран для исправления бага в навигации
        myColorRecyclerViewAdapter = MyColorRecyclerViewAdapter(object : ColorClickListner {
            override fun onItemClick(id: Int) {
//                parentFragmentManager.beginTransaction()
////                    .replace(R.id.fragment_container_view_tag, colorFragment)
//                    .addToBackStack("collection")
//                    .commit()
            }

        })
        binding.list.addItemDecoration(getListRecyclerDecoration())

        binding.list.adapter = myColorRecyclerViewAdapter
        // TODO включить при работе с пагинацией
//        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount = layoutManager.itemCount
//                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                    && firstVisibleItemPosition >= 0
//                ) {
//                    viewModel.load()
//                }
//            }
//        })


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