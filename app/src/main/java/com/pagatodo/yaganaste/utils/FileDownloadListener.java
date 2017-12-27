package com.pagatodo.yaganaste.utils;

import android.net.Uri;

/**
 * Created by Omar on 21/12/2017.
 */

public interface FileDownloadListener {
    /**
     * Recibimos la URI del archivo que descargamos asi como su tipo de data. Esto los sirve para
     * hacer dierencia entre su tipo de MIME
     *
     * @param uriPath
     * @param typeData
     */
    void returnUri(Uri uriPath, String typeData);
}
