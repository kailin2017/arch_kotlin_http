package com.kailin.arch_kotlin_http.ui

import android.view.View
import android.view.ViewGroup
import com.kailin.arch_kotlin_http.app.MyRecyclerAdapter
import com.kailin.arch_kotlin_http.app.ViewHolder
import com.kailin.arch_kotlin_http.repo.typicode.TypiCode
import com.kailin.arch_kotlin_http.utils.HttpHelper
import com.kailin.coroutines_arch.R
import com.kailin.coroutines_arch.databinding.ItemMainBinding

class MainAdapter(onClick: View.OnClickListener) :
    MyRecyclerAdapter<ItemMainBinding, TypiCode>(onClick) {

    override val viewLayoutRes: Int = R.layout.item_main
    private val httpHelper: HttpHelper by lazy { HttpHelper() }
    private var lastTag = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemMainBinding> {
        return super.onCreateViewHolder(parent, viewType).apply {
            binding.image.tag = lastTag
            lastTag++
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemMainBinding>, position: Int) {
        holder.binding.also {
            val itemData = data[position]
            it.data = itemData
            it.isLoading = true
            httpHelper.callImage(itemData.thumbnailUrl, it.image.tag) { bitmap ->
                it.isLoading = false
                it.image.setImageBitmap(bitmap)
            }
        }
    }
}