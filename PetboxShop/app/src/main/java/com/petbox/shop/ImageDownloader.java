package com.petbox.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by 펫박스 on 2015-10-06.
 */
public class ImageDownloader
{
    public static final int IMGAE_CACHE_LIMIT_SIZE = 50;
    public static HashMap<String, Bitmap> mImageCache = new HashMap<String, Bitmap>();

    public static void download(String url, ImageView imageView)
    {
        Bitmap cachedImage = mImageCache.get(url);
        if(cachedImage != null)
        {
            imageView.setImageBitmap(cachedImage);
        }
        else if(cancelPotentialDownload(url, imageView))
        {
            if(mImageCache.size() > IMGAE_CACHE_LIMIT_SIZE)
            {
                mImageCache.clear();
            }

            ImageDownloaderTask task = new ImageDownloaderTask(url, imageView);
            DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
            imageView.setImageDrawable(downloadedDrawable);
            task.execute(url);

        }
    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView)
    {
        ImageDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if(bitmapDownloaderTask != null)
        {
            String bitmapUrl = bitmapDownloaderTask.url;
            if((bitmapUrl == null) || (!bitmapUrl.equals(url)))
            {
                bitmapDownloaderTask.cancel(true);
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    private static ImageDownloaderTask getBitmapDownloaderTask(ImageView imageView)
    {
        if(imageView != null)
        {
            Drawable drawable = imageView.getDrawable();
            if(drawable instanceof DownloadedDrawable)
            {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    static class DownloadedDrawable extends ColorDrawable
    {
        private final WeakReference<ImageDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(ImageDownloaderTask bitmapDownloaderTask)
        {
            super(Color.TRANSPARENT);
            bitmapDownloaderTaskReference = new WeakReference<ImageDownloaderTask>(bitmapDownloaderTask);
        }

        public ImageDownloaderTask getBitmapDownloaderTask()
        {
            return bitmapDownloaderTaskReference.get();
        }
    }

    static Bitmap downloadBitmap(String[] params)
    {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(params[0]);

        Log.e("downloadBitmap url ", params[0]);

        try
        {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK)
            {
                Log.e("downloadBitmap url ","statusCode != " + HttpStatus.SC_OK);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if(entity != null)
            {
                Log.e("downloadBitmap","entity != null");
                InputStream inputStream = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;


                try
                {
                    Log.e("downloadBitmap"," start download");
                    inputStream = entity.getContent();
                    //final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                    final Bitmap bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream), null, options);

                    Log.e("downloadBitmap"," return bitmap");
                    return bitmap;
                }
                finally
                {
                    if(inputStream != null)
                    {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        }
        catch(Exception e)
        {
            Log.e("downloadBitmap","down try fail Exception");
            getRequest.abort();
        }
        Log.e("downloadBitmap"," return null");
        return null;
    }

    static class FlushedInputStream extends FilterInputStream
    {
        public FlushedInputStream(InputStream inputStream)
        {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException
        {
            long totalBytesSkipped = 0L;
            while(totalBytesSkipped < n)
            {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if(bytesSkipped == 0L)
                {
                    int bytes = read();
                    if(bytes < 0)
                    {
                        break; // we reached EOF
                    }
                    else
                    {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
    public static void DeleteBitmapToFile(String FilePath, String filename)
    {
        File fileCacheItem = new File(FilePath + filename);
        fileCacheItem.delete();
    }

    public static class saveBitmapToFile extends AsyncTask<String, Void, Bitmap>
    {

        protected Bitmap doInBackground(String... strs) {
            Log.e("Start download bitmap", "비트맵을 다운로드 해서 return 합니다.");
            return downloadBitmap(strs);
        }

        protected void onPostExecute(Bitmap bitmap) {
            Log.e("Start download image", "");

        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    }


    public static void SaveBitmapToFileCache(String url, String filename)
    {
        //String url, String FilePath, String filename
        String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.petbox.shop/files/";
        File file = new File(FilePath);
        Bitmap bitmap = null;

        try {
            bitmap = new saveBitmapToFile().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options bmOptions;
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        //bitmap = downloadBitmap(url);

        Log.e("filepath + filename = ",FilePath + filename);

        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            Log.e("파일 없어서 생성함","--------------------------------If no folders");
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }

        File fileCacheItem = new File(FilePath + filename);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            Log.e("비트맵 파일 저장", "printStackTrace");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {
            Log.e("비트맵 파일 저장 에러","printStackTrace");
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e("비트맵 파일 저장 에러","IOException");
                e.printStackTrace();
            }
        }
    }
}