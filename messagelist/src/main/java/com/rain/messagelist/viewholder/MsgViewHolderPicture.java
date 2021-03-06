package com.rain.messagelist.viewholder;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.rain.messagelist.R;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: PictureItemProvider
 * @CreateDate: 2020/6/6 16:46
 * @Describe: 图片消息
 */
public class MsgViewHolderPicture extends MsgViewHolderThumbBase {

    public MsgViewHolderPicture(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.im_message_item_picture;
    }

    @Override
    public int viewType() {
        return MessageType.image.getValue();
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
    }

    @Override
    protected void onItemClick() {
        getMsgAdapter().getViewHolderEventListener().onPictureViewHolderClick(mImageView, message);
    }
}
