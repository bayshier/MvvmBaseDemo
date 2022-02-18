package com.module.me.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lib.base.adapter.BaseSkeletonAdapter
import com.lib.net.local.entity.UserTestRoom
import com.module.me.R

/**
 *
 *
 */
class RoomTestAdapter :
    BaseSkeletonAdapter<UserTestRoom, BaseViewHolder>(R.layout.item_user) {
    override fun convert(holder: BaseViewHolder, item: UserTestRoom) {
        holder.setText(R.id.tv_name, "姓名：%s%s".format(item.firstName, item.lastName))
            .setText(R.id.tv_caption, "年龄：%s".format(item.age))
        Glide.with(context)
            .load(item.image)
            .placeholder(R.drawable.loading_anim)
            .error(R.drawable.loading_bg)
            .into(holder.getView(R.id.iv_recipe_item_image))
    }
}
