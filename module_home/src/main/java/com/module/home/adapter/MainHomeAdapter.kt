package com.module.home.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lib.base.adapter.BaseSkeletonAdapter
import com.lib.domain.entity.Demo
import com.lib.net.utils.ext.view.showToast
import com.module.home.R
import com.module.home.databinding.ItemRecipeBinding

/**
 */
class MainHomeAdapter :
    BaseSkeletonAdapter<Demo, BaseDataBindingHolder<ItemRecipeBinding>>(R.layout.item_recipe) {
    override fun convert(holder: BaseDataBindingHolder<ItemRecipeBinding>, item: Demo) {
        holder.dataBinding?.apply {
            tvCaption.text = item.description
            tvName.text = item.name
            ivRecipeItemImage.apply {
                Glide.with(context)
                    .load(item.thumb)
                    .placeholder(R.drawable.ic_healthy_food)
                    .error(R.drawable.ic_healthy_food)
                    .into(this)
            }

            rlRecipeItem.setOnClickListener {
                "您点击了${this@MainHomeAdapter.getItemPosition(item)}".showToast(
                    context
                )
            }
        }
    }
}
