package edu.jhu.cs.oose.fall2012.group1.jyogurt.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
/**
 * THe Image Loader for Calling back
 * @author Alan
 *
 */
public class CallbackImpl implements AsyncImageLoader.ImageCallback{
    private ImageView imageView;
    public CallbackImpl(ImageView imageView){
        super();
        this.imageView=imageView;
    }

	public void ImageLoaded(Drawable imageDrawable) {
		imageView.setImageDrawable(imageDrawable);
	}
}