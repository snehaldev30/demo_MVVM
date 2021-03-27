package com.demomvvm.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

/**
 * Created by Snehal on 1/15/2020.
 */
public class ImageUtils {
    public static File compressFileForUploading(Context context, File file) {
        try {
            return new Compressor(context)
                    .setMaxHeight(1000)
                    .setMaxWidth(1000)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void glideCenterTransform(Context context, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(TypefaceUtils.convertDpToPixel(7)));
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void glideWithPlaceHolder(Context context, String imageUrl, ImageView imageView, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(TypefaceUtils.convertDpToPixel(7)));
        requestOptions = requestOptions.placeholder(placeholder);
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    public static void glideSquareImage(Context context, String imageUrl, ImageView imageView, int placeholder) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop());
        requestOptions = requestOptions.placeholder(placeholder);
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    public static String getRealPathFromUri(final Context context, final Uri uri) {
        String result = uri+"";
        // DocumentProvider
        if ((result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length-1];
            final String[] dat = imgary.split("%3A");
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }
}
