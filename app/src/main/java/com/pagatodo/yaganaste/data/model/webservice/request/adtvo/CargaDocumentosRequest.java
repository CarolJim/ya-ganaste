package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class CargaDocumentosRequest implements Parcelable{

    private List<DataDocuments> Documentos;

    public CargaDocumentosRequest() {

        Documentos = new ArrayList<DataDocuments>();
    }

    protected CargaDocumentosRequest(Parcel in) {
    }

    public static final Creator<CargaDocumentosRequest> CREATOR = new Creator<CargaDocumentosRequest>() {
        @Override
        public CargaDocumentosRequest createFromParcel(Parcel in) {
            return new CargaDocumentosRequest(in);
        }

        @Override
        public CargaDocumentosRequest[] newArray(int size) {
            return new CargaDocumentosRequest[size];
        }
    };

    public List<DataDocuments> getDocumentos() {
        return Documentos;
    }

    public void setDocumentos(List<DataDocuments> documentos) {
        Documentos = documentos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
