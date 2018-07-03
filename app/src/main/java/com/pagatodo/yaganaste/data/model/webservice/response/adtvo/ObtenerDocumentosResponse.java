package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import android.os.Parcel;
import android.os.Parcelable;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.ui.adquirente.fragments.DocumentosFragment;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.STATUS_DOCTO_RECHAZADO;

/**
 * Created by flima on 21/03/2017.
 */

public class ObtenerDocumentosResponse extends GenericResponse implements Parcelable {

    private ResponseDocumentos Data;

    public ObtenerDocumentosResponse() {
        Data = new ResponseDocumentos();
    }

    public ResponseDocumentos getData() {
        return Data;
    }

    public List<EstatusDocumentosResponse> getDocumentos() {
        return Data.Documentos;
    }

    public String getEstatus() {
        return Data.Estatus;
    }

    public String getFolio() {
        return Data.Folio;
    }

    public String getMotivo() {
        return Data.Motivo;
    }

    public int getIdEstatus() {
        return Data.IdEstatus;
    }

    public boolean hasErrorInDocs() {
        return Data.docsWithError();
    }

    public void setData(ResponseDocumentos data) {
        Data = data;
    }

    class ResponseDocumentos implements Parcelable {
        List<EstatusDocumentosResponse> Documentos;
        String Estatus, Folio, Motivo;
        int IdEstatus;

        public boolean docsWithError() {
            for (EstatusDocumentosResponse docs : Documentos) {
                if (docs.getIdEstatus() == STATUS_DOCTO_RECHAZADO) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.Documentos);
            dest.writeString(this.Estatus);
            dest.writeString(this.Folio);
            dest.writeString(this.Motivo);
            dest.writeInt(this.IdEstatus);
        }

        public ResponseDocumentos() {
        }

        protected ResponseDocumentos(Parcel in) {
            this.Documentos = new ArrayList<EstatusDocumentosResponse>();
            in.readList(this.Documentos, EstatusDocumentosResponse.class.getClassLoader());
            this.Estatus = in.readString();
            this.Folio = in.readString();
            this.Motivo = in.readString();
            this.IdEstatus = in.readInt();
        }

        public final Parcelable.Creator<ResponseDocumentos> CREATOR = new Parcelable.Creator<ResponseDocumentos>() {
            @Override
            public ResponseDocumentos createFromParcel(Parcel source) {
                return new ResponseDocumentos(source);
            }

            @Override
            public ResponseDocumentos[] newArray(int size) {
                return new ResponseDocumentos[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.Data, flags);
    }

    protected ObtenerDocumentosResponse(Parcel in) {
        this.Data = in.readParcelable(ResponseDocumentos.class.getClassLoader());
    }

    public static final Parcelable.Creator<ObtenerDocumentosResponse> CREATOR = new Parcelable.Creator<ObtenerDocumentosResponse>() {
        @Override
        public ObtenerDocumentosResponse createFromParcel(Parcel source) {
            return new ObtenerDocumentosResponse(source);
        }

        @Override
        public ObtenerDocumentosResponse[] newArray(int size) {
            return new ObtenerDocumentosResponse[size];
        }
    };
}
