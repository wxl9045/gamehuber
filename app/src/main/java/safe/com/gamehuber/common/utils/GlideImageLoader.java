package safe.com.gamehuber.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
    }

        @Override
    public ImageView createImageView(Context context) {
        //圆角
        return new RoundAngleImageView(context);
    }

}
