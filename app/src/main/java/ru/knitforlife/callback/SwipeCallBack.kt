package otus.gpb.recyclerview.callback

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeCallBack(
    private val density: Float,
    private val scaledDensity: Float,
    private val iconMarginDpRight: Int,
    private val iconMarginDpTop: Int,
    private val swipeAction: (viewHolder: RecyclerView.ViewHolder) -> Unit,
    private val background: Paint,
    private val icon: Drawable?,
    dragDirs: Int = 0,
    swipeDirs: Int = ItemTouchHelper.LEFT
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    private fun sizeDpInPixel(sizeDp: Int): Int {
        return (density * sizeDp + 0.5f).toInt()
    }

    private fun sizePixelInDp(sizePixel: Int): Int {
        return (sizePixel / density + 0.5f).toInt()
    }

    private fun pxToSp(px: Float): Float{
        return px/scaledDensity
    }
    private fun spToPx(sp: Float): Float{
        return sp * scaledDensity
    }

    // не передвигаем вверх вниз
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeAction.invoke(viewHolder)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
//        val itemHeight = itemView.bottom - itemView.top

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX <= 0f) {
                var iconTop = 0
                var iconLeft = 0
                var iconRight = 0
                var iconBottom = 0
                icon?.let {

                    iconTop = itemView.top + sizeDpInPixel(iconMarginDpTop)
                    iconRight = itemView.right - sizeDpInPixel(iconMarginDpRight)
                    iconBottom = iconTop + icon.intrinsicHeight
                    iconLeft = iconRight - icon.intrinsicWidth
                    c.drawRect(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat(),
                        background
                    )
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom)
                    icon.draw(c)
                }
                val textPaint = Paint()
                textPaint.color = Color.WHITE
                val deleteTextSize = spToPx(13F)
                textPaint.textSize = deleteTextSize


                val textWidth = textPaint.measureText("Archive")
                val textY = itemView.bottom - sizeDpInPixel(16).toFloat()
                val textX = itemView.right - sizeDpInPixel(20).toFloat() - textWidth


                c.drawText("Archive", textX, textY, textPaint)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }
}