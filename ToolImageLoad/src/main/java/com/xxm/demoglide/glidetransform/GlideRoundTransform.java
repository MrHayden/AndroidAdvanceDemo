package com.xxm.demoglide.glidetransform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.xxm.demoglide.utils.TransformationUtils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * A Glide {@link BitmapTransformation} to circle crop an image.  Behaves similar to a
 * {@link GlideRoundTransform} transform, but the resulting image is masked to a circle.
 * <p>
 * <p> Uses a PorterDuff blend mode, see http://ssp.impulsetrain.com/porterduff.html. </p>
 */
public class GlideRoundTransform extends BitmapTransformation {
    // The version of this transformation, incremented to correct an error in a previous version.
    // See #455.
    private static final int VERSION = 1;
    private static final String ID = "lib.frame.utils.GlideRoundTransform." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private int borderWidth = 0;
    private int borderColor = 0x000000; // 边框颜色

    private Paint borderPaint;

    public GlideRoundTransform() {
        // Intentionally empty.
        this(0, 0);
    }

    public GlideRoundTransform(int borderWidth, int borderColor) {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    /**
     * @deprecated Use {@link #GlideRoundTransform()}.
     */
    @Deprecated

    public GlideRoundTransform(@SuppressWarnings("unused") Context context) {
        this();
    }

    /**
     * @deprecated Use {@link #GlideRoundTransform()}
     */
    @Deprecated
    public GlideRoundTransform(@SuppressWarnings("unused") BitmapPool bitmapPool) {
        this();
    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int outWidth, int outHeight) {
        int destMinEdge = Math.min(outWidth, outHeight);
        float radius = destMinEdge / 2f;

        int srcWidth = inBitmap.getWidth();
        int srcHeight = inBitmap.getHeight();

        float scaleX = destMinEdge / (float) srcWidth;
        float scaleY = destMinEdge / (float) srcHeight;
        float maxScale = Math.max(scaleX, scaleY);

        float scaledWidth = maxScale * srcWidth;
        float scaledHeight = maxScale * srcHeight;
        float left = (destMinEdge - scaledWidth) / 2f;
        float top = (destMinEdge - scaledHeight) / 2f;

        RectF destRect = new RectF(left + borderWidth, top + borderWidth, left + scaledWidth - borderWidth, top + scaledHeight - borderWidth);

        // Alpha is required for this transformation.
        Bitmap toTransform = TransformationUtils.getAlphaSafeBitmap(pool, inBitmap);

        Bitmap result = pool.get(destMinEdge, destMinEdge, Bitmap.Config.ARGB_8888);
        result.setHasAlpha(true);

        TransformationUtils.BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            // Draw a circle
            canvas.drawCircle(radius, radius, radius - borderWidth, TransformationUtils.CIRCLE_CROP_SHAPE_PAINT);
            // Draw the bitmap in the circle
            canvas.drawBitmap(toTransform, null, destRect, TransformationUtils.CIRCLE_CROP_BITMAP_PAINT);

            //画圆形边框
            borderPaint.setColor(borderColor);
            borderPaint.setStrokeWidth(borderWidth);
            canvas.drawCircle(radius, radius, radius - borderWidth / 2 - 1, borderPaint);
            TransformationUtils.clear(canvas);
        } finally {
            TransformationUtils.BITMAP_DRAWABLE_LOCK.unlock();
        }

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideRoundTransform && ((GlideRoundTransform) o).borderWidth == borderWidth && ((GlideRoundTransform) o).borderColor == borderColor;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + borderWidth + borderColor;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
        byte[] roundData = ByteBuffer.allocate(4).putInt(borderWidth).array();
        messageDigest.update(roundData);
        byte[] colorData = ByteBuffer.allocate(4).putInt(borderColor).array();
        messageDigest.update(colorData);
    }
}
