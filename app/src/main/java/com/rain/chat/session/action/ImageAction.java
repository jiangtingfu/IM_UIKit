package com.rain.chat.session.action;

import android.annotation.SuppressLint;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rain.chat.R;
import com.rain.chat.base.NimHelper;
import com.rain.chat.glide.GlideImageLoader;
import com.rain.chat.session.module.CustomerBaseAction;
import com.rain.crow.PhotoPick;
import com.rain.crow.bean.MediaData;
import com.rain.crow.controller.PhotoPickConfig;
import com.rain.crow.impl.PhotoSelectCallback;
import com.rain.crow.utils.MimeType;
import com.rain.library_netease_sdk.msg.models.MyMessage;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : Rain
 * @CreateDate : 2020/7/27 14:38
 * @Version : 1.0
 * @Descroption : 图片消息功能按钮
 */
public class ImageAction extends CustomerBaseAction {

    public ImageAction() {
        super(R.drawable.action_location_selector, R.string.input_panel_photo);
    }

    @Override
    public void onClick() {
        new MaterialDialog.Builder(getActivity())
                .title(getActivity().getString(R.string.action_image_title))
                .items(R.array.pick_image_arrays)
                .itemsCallback((dialog, itemView, position, text) -> {
                    if (position == 0) {
                        //拍摄
                        showCamera();
                    } else if (position == 1) {
                        //相册
                        showPhotoAlbum();
                    }
                }).show();
    }

    private void showCamera() {
        PhotoPick.useCamera(getActivity())
                .startCompression(true)
                .setCallback((PhotoSelectCallback) photos -> {
                    MediaData mediaData = photos.get(0);
                    File file = new File(mediaData.getCompressionPath());
                    MyMessage message = NimHelper.getIMessageBuilder().createImageMessage(
                            getAccount(), getSessionType(), file, file.getName());
                    getContainer().proxy.sendMessage(message);
                }).build();
    }

    private void showPhotoAlbum() {
        PhotoPick.from(getActivity())
                .imageLoader(new GlideImageLoader())
                .showCamera(false)
                .setMimeType(MimeType.ofImage())
                .pickMode(PhotoPickConfig.MODE_PICK_MORE)
                .maxPickSize(9)
                .startCompression(true)
                .setCallback((PhotoSelectCallback) photos ->
                        sendImageMessages(photos)).build();
    }

    @SuppressLint("CheckResult")
    private void sendImageMessages(ArrayList<MediaData> photos) {
        // FIXME: 2020/8/27 添加compose
        Flowable.fromIterable(photos)
                .map(mediaData -> new File(mediaData.isCompressed() ?
                        mediaData.getCompressionPath() : mediaData.getOriginalPath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            MyMessage message = NimHelper.getIMessageBuilder().createImageMessage(
                                    ImageAction.this.getAccount(), ImageAction.this.getSessionType(), data, data.getName());
                            getContainer().proxy.sendMessage(message);
                        }
                );
    }
}
