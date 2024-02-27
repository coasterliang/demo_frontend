package org.example.hsbcbookfrontend.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import org.example.hsbcbookfrontend.R
import org.example.hsbcbookfrontend.network.model.BookListModel

class BookAdapter : BaseQuickAdapter<BookListModel.DataDTO, QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: BookListModel.DataDTO?) {
        holder.getView<TextView>(R.id.title).setText(item?.name)
        holder.getView<Button>(R.id.modify).setOnClickListener {
           LiveEventBus.get<Int>("openmodify").post(item?.id)
        }
        holder.getView<Button>(R.id.delete).setOnClickListener {
            LiveEventBus.get<Int>("delete").post(item?.id)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_book, parent)
    }
}