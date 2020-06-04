package com.zfxf.zfxfproject.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zfxf.zfxfproject.R;

import org.jetbrains.annotations.NotNull;

public class TopItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TopItemAdapter() {
        super(R.layout.top_item_view);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, String s) {
        holder.setText(R.id.tvTitle,s);
    }
}
