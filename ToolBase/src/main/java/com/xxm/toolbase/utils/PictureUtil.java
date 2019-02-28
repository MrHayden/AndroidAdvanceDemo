package com.xxm.toolbase.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class PictureUtil {

    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        boolean isLongImg;
        if (height / width > 3 || width / height > 3) {
            isLongImg = true;
            return 1;
        }
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 780, 1280);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int[] getLocalImgSize(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(filePath, options);
        int[] size = {options.outWidth, options.outHeight};
        return size;
    }

    public static Bitmap getSmallBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getSrcBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     */
    public static void galleryScanPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取保存图片的目录
     *
     * @return
     */
    public static File getAlbumDir() {
        File dir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getAlbumName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public static String getAlbumName() {
        return "wukong/img";
    }

    public static void saveSmallImg2Path(String fromPath, String toPath) {
        Bitmap bm;
        boolean isLongImg = false;
        int degree = ImageUtils.readPictureDegree(fromPath);
        if (!TextUtils.isEmpty(toPath)) {
            try {
                bm = PictureUtil.getSmallBitmap(fromPath);
                if (degree != 0) {
                    bm = ImageUtils.rotaingImageView(degree, bm);
                }
                if (bm.getWidth() / bm.getHeight() > 3
                        || bm.getHeight() / bm.getWidth() > 3) {
                    isLongImg = true;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                int size = baos.toByteArray().length / 1024;
                int p = 40;
                if (isLongImg) {// 长图大小限制
                    if (size < 2048) {
                        p = 100;
                    } else if (size < 3072) {
                        p = 90;
                    } else if (size < 4096) {
                        p = 80;
                    } else if (size < 5128) {
                        p = 60;
                    } else {
                        p = 40;
                    }
                } else {
                    if (size < 150) {// 150kb以内不做压缩处理
                        p = 100;
                    } else if (size < 300) {
                        p = 90;
                    } else if (size < 500) {
                        p = 80;
                    } else if (size < 700) {
                        p = 70;
                    } else if (size < 900) {
                        p = 60;
                    } else if (size < 1100) {
                        p = 50;
                    } else {
                        p = 40;
                    }
                }
                Log.i("wkxy", "size -- " + size + "   p  ==  " + p);
                FileOutputStream fos = new FileOutputStream(new File(toPath));
                bm.compress(Bitmap.CompressFormat.JPEG, p, fos);
            } catch (Exception e) {
                Log.i("wkxy", "error === " + e);
            }
        }
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static Drawable tintDrawable(Drawable drawable, int colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static Drawable setStroke(Drawable drawable, int borader, int colors) {
        if (drawable instanceof GradientDrawable) {
            final GradientDrawable wrappedDrawable = (GradientDrawable) drawable;
            wrappedDrawable.setStroke(borader, colors);
            return wrappedDrawable;
        }
        return drawable;
    }
}
