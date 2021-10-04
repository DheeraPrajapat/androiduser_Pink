package com.marius.valeyou.util.misc;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.marius.valeyou.util.AppUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by jatin on 12/27/2017.
 */

public class FileUtils {
    /**
     * TAG for log messages.
     */
    static final String TAG = "FileUtils";
    private static final int THUMBNAIL_SIZE = 200;

    private FileUtils() {
    } //private constructor to enforce Singleton pattern

    public static String getFileFromInputstream(Context context, Uri uri) throws Exception {
        if (uri.getScheme().equalsIgnoreCase("content")) {
            ContentResolver cR = context.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(uri));
            File storageDir = context.getCacheDir();
            File image = File.createTempFile(
                    "Fli",  /* prefix */
                    "." + type,         /* suffix */
                    storageDir      /* directory */
            );
            InputStream inputStream = cR.openInputStream(uri);
            OutputStream out = new FileOutputStream(image);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            inputStream.close();
            return image.getAbsolutePath();
        } else return null;


    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public static Bitmap getThumbnil(String fileName) {
        int max = AppUtils.convertDpToPixel(THUMBNAIL_SIZE);

        try {

            FileInputStream fis = new FileInputStream(fileName);
            Bitmap bm = BitmapFactory.decodeStream(fis);

            int width = bm.getWidth();
            int height = bm.getHeight();

            Log.d("Pictures", "Width and height are " + width + "--" + height);

            if (width > height) {
                // landscape
                float ratio = (float) width / max;
                width = max;
                height = (int) (height / ratio);
            } else if (height > width) {
                // portrait
                float ratio = (float) height / max;
                height = max;
                width = (int) (width / ratio);
            } else {
                // square
                height = max;
                width = max;
            }
            Log.d("Pictures", "after scaling Width and height are " + width + "--" + height);

            bm = Bitmap.createScaledBitmap(bm, width, height, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] bytes = baos.toByteArray();
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean isImageFile(@NonNull String path) {
        String[] s = {".jpeg", ".gif", ".png", ".bmp", ".jpg"};
        for (String s1 : s) {
            if (path.endsWith(s1) || path.endsWith(s1.toUpperCase()))
                return true;
        }
        return false;
    }

    public static boolean isAudioFile(@NonNull String path) {
        String[] s = {".aac", ".mp3", ".wav", ".wma"};
        for (String s1 : s) {
            if (path.endsWith(s1) || path.endsWith(s1.toUpperCase()))
                return true;
        }
        return false;
    }

    public static boolean isVideoFile(@NonNull String path) {
        String[] s = {".mp4", ".avi", ".mov"};
        for (String s1 : s) {
            if (path.endsWith(s1) || path.endsWith(s1.toUpperCase()))
                return true;
        }
        return false;
    }

    public static boolean isDocFile(@NonNull String path) {
        String[] s = {".doc", ".docx", ".html", ".htm", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", ".pdf"};
        for (String s1 : s) {
            if (path.endsWith(s1) || path.endsWith(s1.toUpperCase()))
                return true;
        }
        return false;
    }

    public static boolean isAllowedExtension(@NonNull String path) {
        if (isAudioFile(path))
            return true;
        if (isImageFile(path))
            return true;
        if (isDocFile(path))
            return true;
        if (isVideoFile(path))
            return true;
        return false;
    }

}
