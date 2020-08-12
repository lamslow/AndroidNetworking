package com.example.lab4_androidnetworking.loader;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SaveImageLoader extends AsyncTask<String,String,String> {

    String title;
    Context context;

    public SaveImageLoader(Context context,String title) {
        this.context = context;
        this.title=title;
    }

    ProgressDialog progressDialog;

    @Override
    protected String doInBackground(String... strings) {
        String link=strings[0];
//        int fileSize;
//        int count;
//        long totalSize = 0;
//        byte dataArray[] = new byte[1024];
//        try {
//
//            URL url=new URL(link);
//            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//            httpURLConnection.connect();
//            fileSize = httpURLConnection.getContentLength();
//            InputStream inputStream=httpURLConnection.getInputStream();
//            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
//            while ((count = inputStream.read(dataArray)) != -1) {
//
//                totalSize += count;
//                publishProgress(""+(int)(fileSize));
//
//            }
//            String saveImg= MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,title,title);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Uri image_uri=Uri.parse(link);
        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(image_uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(title);
        request.setDescription("Downloading...");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS,"image.jpg");
        downloadManager.enqueue(request);
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, "Image Downloaded Successfully", Toast.LENGTH_SHORT).show();

    }

}
