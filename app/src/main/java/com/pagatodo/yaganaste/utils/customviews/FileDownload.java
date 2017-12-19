package com.pagatodo.yaganaste.utils.customviews;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by FranciscoManzo on 15/12/2017.
 */

public class FileDownload extends AsyncTask<String, String, Uri> {
    SplashActivity splashActivity;
    String urlData;
    String nameData;

    public FileDownload(SplashActivity splashActivity, String urlData, String nameData) {
        this.splashActivity = splashActivity;
        this.urlData = urlData;
        this.nameData = nameData;
    }

    // https://drive.google.com/file/d/1AC2BoUQKeXqQr04047UcZcyARLGsAwB4/view?usp=sharing// https://drive.google.com/file/d/1AC2BoUQKeXqQr04047UcZcyARLGsAwB4/view?usp=sharing
    // http://www.africau.edu/images/default/sample.pdf
    @Override
    protected Uri doInBackground(String... strings) {
        String root = Environment.getExternalStorageDirectory().toString();
        int count;
        URL url = null;
        Uri myUri = null;

        try {
            url = new URL(urlData);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        myUri = Uri.parse(root + "/" + nameData);
        File af = new File(String.valueOf(myUri));

        // Verificamos que el archivo no exista
        if(!af.exists()) {
            URLConnection conection = null;
            try {
                conection = url.openConnection();

                conection.connect();
                // Tamaño del archivo
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file

                OutputStream output = new FileOutputStream(root + "/" + nameData);
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);

                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

                myUri = Uri.parse(root + "/" + nameData);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return myUri;
    }

    @Override
    protected void onPostExecute(Uri uriPath) {
        splashActivity.returnUri(uriPath);
        super.onPostExecute(uriPath);
    }

}
