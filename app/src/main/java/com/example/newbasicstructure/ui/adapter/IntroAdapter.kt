package com.example.newbasicstructure.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbasicstructure.R
import com.example.newbasicstructure.data.local.IntroScreenData
import com.example.newbasicstructure.databinding.ViewItemIntroBinding

/**
 * Created by Khush Pajwani.
 * Date 3/7/2022
 */
class IntroAdapter(
    private val context: Context, list: ArrayList<IntroScreenData?>,
) :
    RecyclerView.Adapter<IntroAdapter.CustomViewHolder?>() {

    private val items: ArrayList<IntroScreenData?> = list
    lateinit var binding: ViewItemIntroBinding
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int,
    ): CustomViewHolder {
//        binding = ViewItemIntroBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_item_intro,
            viewGroup,
            false
        )
        return CustomViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, i: Int) {
        binding.itemData = items[i]
        binding.apply {
            itemData = items[i]
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        override fun onClick(p0: View?) {
        }
    }
}