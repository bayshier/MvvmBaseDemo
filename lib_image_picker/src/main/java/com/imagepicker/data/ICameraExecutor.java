package com.imagepicker.data;

import androidx.annotation.Nullable;

import com.imagepicker.bean.ImageItem;

public interface ICameraExecutor {

    void takePhoto();

    void takeVideo();

    void onTakePhotoResult(@Nullable ImageItem imageItem);
}
