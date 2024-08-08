package com.example.questiongame.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.questiongame.R
import com.example.questiongame.app.category.viewmodel.CategoryViewModel
import com.example.questiongame.databinding.HorizontalRecyclerBinding
import com.example.questiongame.model.response.CatData
import com.example.questiongame.utils.Constant.BASEURL
import com.example.questiongame.utils.Constant.selectedItems

class HorizontalAdapter(val data: List<CatData>,val viewModel: CategoryViewModel) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int):HorizontalAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        val binding = HorizontalRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalAdapter.ViewHolder, position: Int) {
        holder.txtImg.text = data[position].category
        holder.item_id.text = data[position].id.toString()
        holder.layout.setBackgroundColor(
            if (selectedItems.contains(data[position].id)) Color.GREEN else 0
        )
        holder.layout.setOnClickListener {

            if (selectedItems.contains(data[position].id)) {
                viewModel.removeButton()
                selectedItems.remove(data[position].id)
                holder.layout.setBackgroundColor(0)
            }
            else if (selectedItems.size < 6) {
                viewModel.removeButton()
                selectedItems.add(data[position].id!!)
                holder.layout.setBackgroundColor(Color.GREEN)
            }
            else {
                Toast.makeText(mContext, "Cant Add more than 6", Toast.LENGTH_SHORT).show()
                viewModel.showButton()
            }
            Log.e("list",selectedItems.toString())
            if(selectedItems.size == 6){
                viewModel.showButton()
            }
        }

        val imageUrl= BASEURL+data[position].buttonImage
        Glide.with(holder.imgView.context)
            .load(imageUrl)
            .placeholder(R.drawable.download) // Placeholder image while loading
            .error(R.drawable.error_image) // Image to display on error
            .into(holder.imgView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding:HorizontalRecyclerBinding):
        RecyclerView.ViewHolder(binding.root) {
            val txtImg=binding.txtImg
        val imgView=binding.imgView
        val layout = binding.mainLayout
        val item_id = binding.itemId
        }
}