package com.imagepicker.data;


import com.imagepicker.bean.ImageItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description: 图片选择器回调
 * <p>
 * Author: peixing.yang
 * Date: 2019/2/21
 */
public interface OnImagePickCompleteListener extends Serializable {
    void onImagePickComplete(ArrayList<ImageItem> items);
}
