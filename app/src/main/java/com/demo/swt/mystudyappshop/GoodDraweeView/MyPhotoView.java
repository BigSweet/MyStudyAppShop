package com.demo.swt.mystudyappshop.GoodDraweeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by pc on 2016/12/13.
 */

public class MyPhotoView extends PhotoView {

    protected DraweeHolder<GenericDraweeHierarchy> mDraweeHolder;

    public MyPhotoView(Context context) {
        this(context, null);
    }

    public MyPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public MyPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        selfInit();
    }

    private void selfInit() {
        if (mDraweeHolder == null) {
            final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                    .setProgressBarImage(new LoadingProgressDrawable(getContext())).build();

            mDraweeHolder = DraweeHolder.create(hierarchy, getContext());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDraweeHolder.onDetach();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mDraweeHolder.onAttach();
    }

    @Override
    protected boolean verifyDrawable(Drawable dr) {
        super.verifyDrawable(dr);
        return dr == mDraweeHolder.getHierarchy().getTopLevelDrawable();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        mDraweeHolder.onDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        mDraweeHolder.onAttach();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDraweeHolder.onTouchEvent(event) || super.onTouchEvent(event);
    }

    public void setImageUri(String uri) {

        final ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setAutoRotateEnabled(true)
                .build();
        final ImagePipeline imagePipeline = Fresco.getImagePipeline();
        final DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mDraweeHolder.getController())
                .setImageRequest(imageRequest)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);

                        CloseableReference<CloseableImage> imageCloseableReference = null;
                        try {
                            imageCloseableReference = dataSource.getResult();
                            if (imageCloseableReference != null) {
                                final CloseableImage image = imageCloseableReference.get();
                                if (image != null && image instanceof CloseableStaticBitmap) {
                                    CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) image;
                                    final Bitmap bitmap = closeableStaticBitmap.getUnderlyingBitmap();
                                    if (bitmap != null) {
                                        setImageBitmap(bitmap);
                                        // 如果是长图，让其宽度放大至与屏幕等宽
                                        //setScaleType(ScaleType.CENTER_INSIDE);
                                    }
                                }
                            }
                        } finally {
                            dataSource.close();
                            CloseableReference.closeSafely(imageCloseableReference);
                        }
                    }
                })
                .build();
        mDraweeHolder.setController(controller);
        setImageDrawable(mDraweeHolder.getTopLevelDrawable());
    }


}
