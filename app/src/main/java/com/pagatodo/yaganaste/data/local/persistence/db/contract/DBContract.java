package com.pagatodo.yaganaste.data.local.persistence.db.contract;

import android.provider.BaseColumns;

import com.pagatodo.yaganaste.data.local.persistence.db.utils.MappedFrom;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.db.MontoComercio;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

/**
 * Created by Jordan on 21/04/2017.
 */

public class DBContract {

    private DBContract() {
    }

    public static abstract class DBTable implements BaseColumns {
        public static final String DB_STATE = "DB_STATE";

        private DBTable() {
        }
    }

    @MappedFrom(ComercioResponse.class)
    public static final class Comercios extends DBTable {
        public static final String TABLE = "tbl_Comercios";

        public static final String ID_COMERCIO = "ID_Comercio";
        public static final String ID_TIPO_COMERCIO = "ID_Tipo_Comercio";
        public static final String COMERCIO = "Comercio";
        public static final String URL_LOGO = "URLLogo";
        public static final String URL_LOGO_COLOR = "URLLogoColor";
        public static final String URL_IMAGEN = "URLImagen";
        public static final String COLOR_MARCA = "ColorMarca";
        public static final String LONGITUD_REFERENCIA = "LongitudReferencia";
        public static final String FORMATO = "Formato";
        public static final String MENSAJE = "Mensaje";
        public static final String ORDEN = "Orden";
        public static final String SOBRECARGO = "Sobrecargo";
    }

    @MappedFrom(MontoComercio.class)
    public static final class MontosComercio extends DBTable {
        public static final String TABLE = "tbl_MontosComercio";

        public static final String ID_MONTO_COMERCIO = "ID_MontosComercio";
        public static final String ID_COMERCIO = "ID_Comercio";
        public static final String MONTO = "Monto";
    }

    @MappedFrom(Countries.class)
    public static final class Paises extends DBTable{
        public static final String TABLE = "tbl_Paises";

        public static final String ID = "ID";
        public static final String ID_PAIS = "ID_Pais";
        public static final String PAIS = "Pais";
    }
}
