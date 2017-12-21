package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.notifications.MessagingService;
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
 * Se encarga de hacer la descarga de la URL que llega como parametro, puede ser un PNG, JPG, PDG o
 * MP4
 */

public class FileDownload extends AsyncTask<String, String, Uri> {
    Context context;
    String urlData;
    String nameData;
    String typeData;

    public FileDownload(Context context, String urlData, String nameData, String typeData) {
        this.context = context;
        this.urlData = urlData;
        this.nameData = nameData;
        this.typeData = typeData;
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
        myUri = Uri.parse(root + "/YaGanaste/" + nameData);
        File af = new File(String.valueOf(myUri));

        // Verificamos que el archivo no exista
        if (!af.exists()) {
            URLConnection conection = null;
            try {
                conection = url.openConnection();

                conection.connect();
                // Tamaño del archivo
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file

                OutputStream output = new FileOutputStream(root + "/YaGanaste/" + nameData);
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

              //  myUri = Uri.parse(root + "/" + nameData);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return myUri;
    }

    @Override
    protected void onPostExecute(Uri uriPath) {
        // REgresamos el URI y el tipo de data que trabajaremos solo si es un contexto de SplashActivity
        if (context instanceof SplashActivity) {
            ((SplashActivity) context).returnUri(uriPath, typeData);
        }else{
            Toast.makeText(context, "Tu Descarga ha Finalizado", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(uriPath);
    }

}
