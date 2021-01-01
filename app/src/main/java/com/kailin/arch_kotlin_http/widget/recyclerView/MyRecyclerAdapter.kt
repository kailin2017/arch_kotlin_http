package com.kailin.arch_kotlin_http.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kailin.arch_kotlin_http.widget.recyclerView.MyRecyclerDiffCallBack

abstract class MyRecyclerAdapter<V : ViewDataBinding, D>(private val onItemClick: View.OnClickListener? = null) :
    RecyclerView.Adapter<ViewHolder<V>>() {

    protected abstract val viewLayoutRes: Int
    protected val data: MutableList<D> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        return ViewHolder(createBinding(parent, viewType)).apply {
            binding.root.setOnClickListener(onItemClick)
        }
    }

    private fun createBinding(parent: ViewGroup, viewType: Int): V {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), viewLayoutRes, parent, false
        )
    }

    override fun getItemCount() = data.size

    fun updateData(newData: MutableList<D>) {
        calculateDiff(data, newData)
    }

    fun <T> calculateDiff(mainData: MutableList<T>, newData: MutableList<T>) {
        val result = DiffUtil.calculateDiff(MyRecyclerDiffCallBack(mainData, newData))
        mainData.clear()
        mainData.addAll(newData)
        result.dispatchUpdatesTo(this@MyRecyclerAdapter)
    }

    fun getContext(): Context {
        return MyApplication.instance
    }
}

class ViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)