package com.questions.game.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.questions.game.app.category.viewmodel.CategoryViewModel
import com.questions.game.databinding.VerticalRecyclerBinding
import com.questions.game.app.category.model.CatAllDataRes
import com.questions.game.app.category.model.CatDataRes

class VerticalAdapter(private val viewModel: CategoryViewModel) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    private var arrayList = CatAllDataRes()
    private lateinit var mContext: Context

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: CatAllDataRes) {
        this.arrayList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        val binding = VerticalRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalAdapter.ViewHolder, position: Int) {
        val tag = arrayList.tags?.get(position)
        holder.txtCatName.text = tag?.tagName

        val tagCategory = arrayList.tags!![position].category_id.toString()
        val intList = tagCategory.split(",").map { it.toInt() }
        val dataValue: MutableList<CatDataRes> = mutableListOf()
        val uniqueId:MutableList<Int> = mutableListOf()
        for (data1 in arrayList.data!!) {
            if (data1.id !in uniqueId){
                uniqueId.add(data1.id!!)
                for (intItem in intList) {
                    if (data1.id == intItem) {
                        dataValue.add(data1)
                    }
                }
            }
        }
        holder.recyclerview.adapter = HorizontalAdapter(dataValue,viewModel)
        holder.recyclerview.layoutManager =
            LinearLayoutManager(holder.recyclerview.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return arrayList.tags?.size ?: 0 // Return 0 if tags is null
    }

    inner class ViewHolder(binding: VerticalRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val txtCatName = binding.txtCatname
        val recyclerview = binding.categoryRecyclerview
    }
}
