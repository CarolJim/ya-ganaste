package com.pagatodo.yaganaste.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FranciscoManzo on 15/12/2017.
 * Se encarga de hacer la descarga de la URL que llega como parametro, puede ser un PNG, JPG, PDG o
 * MP4
 */

public class FileDownload extends AsyncTask<String, String, Uri> {
    String urlData;
    String nameData;
    String typeData;
    String dirFile;
    FileDownloadListener fileDownloadListener;

    public FileDownload(String urlData, String nameData, String typeData, FileDownloadListener listener) {
        this.urlData = urlData;
        this.nameData = nameData;
        this.typeData = typeData;
        this.fileDownloadListener = listener;
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

        // Crea la Uri con la direccion apuntada a la carpeta /YaGanaste/nombreArchivo
        dirFile = root + "/YaGanaste/" + nameData;
        myUri = Uri.parse(dirFile);
        File af = new File(String.valueOf(myUri));

        // Verificamos que el archivo no exista
        if (!af.exists()) {
            HttpURLConnection conection = null;
            try {
                conection = (HttpURLConnection) url.openConnection();
                conection.connect();
                // Tamaño del archivo
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file

                OutputStream output = new FileOutputStream(dirFile);
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

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return myUri;
    }

    @Override
    protected void onPostExecute(Uri uriPath) {
        // REgresamos el URI y el tipo de data que trabajaremos solo si es un contexto de SplashActivity
        if (fileDownloadListener != null) {
            fileDownloadListener.returnUri(uriPath, typeData);
        } else {
            Toast.makeText(App.getContext(), "Tu Descarga ha Finalizado", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(uriPath);
    }

}
