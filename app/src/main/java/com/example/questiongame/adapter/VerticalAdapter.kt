package com.example.questiongame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questiongame.app.category.viewmodel.CategoryViewModel
import com.example.questiongame.databinding.VerticalRecyclerBinding
import com.example.questiongame.model.response.CatAllData
import com.example.questiongame.model.response.CatData

class VerticalAdapter(val viewModel: CategoryViewModel) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    private var arrayList = CatAllData()
    private lateinit var mContext: Context

    fun setData(list: CatAllData) {
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
        holder.txtCatname.text = tag?.tagName

        val tagCategory = arrayList.tags!![position].category_id.toString()
        val intList = tagCategory.split(",").map { it.toInt() }
        val dataValue: MutableList<CatData> = mutableListOf()
        val uniqueid:MutableList<Int> = mutableListOf()
        for (data1 in arrayList.data!!) {
            if (data1.id !in uniqueid){
                uniqueid.add(data1.id!!)
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

    inner class ViewHolder(private val binding: VerticalRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val txtCatname = binding.txtCatname
        val recyclerview = binding.categoryRecyclerview
    }
}
