package com.pagatodo.yaganaste.utils;

import com.pagatodo.yaganaste.BuildConfig;

public class Recursos {

    //HOST
    public static final String URL_SERVER_ADTVO;
    public static final String URL_SERVER_TRANS;
    public static final String URL_SERVER_ADQ;
    public static final String URL_SERVER_FB;
    public static final String URL_STARBUCKS;

    //BASE DE DATOS
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ya_ganaste.db";

    public static final int TIMEOUT = 60000;

    public static final String PUBLIC_KEY_RSA;
    public static final String PUBLIC_STARBUCKS_KEY_RSA;

    public static final String PIN_ADVO;
    public static final String PIN_TRANS;
    public static final String PIN_YA;
    public static final String PIN_STARBUCKS_1;
    public static final String PIN_STARBUCKS_2;
    public static final String PIN_STARBUCKS_3;
    public static final String PIN_STARBUCKS_4;

    public static final int IDCOMERCIO_YA_GANASTE = 8609;
    public static final int GROUP_FORMAT = 4;

    public static final boolean DEBUG = true;

    /*Freja*/
    public static final String PT_CLIENT_CODE = null;

    public static final long DISCONNECT_TIMEOUT = 120000;
    public static final long FLIP_TIMER = 5000;

    //URL DE WS
    static {

        if (BuildConfig.DEBUG) {
            URL_SERVER_ADTVO = "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc";
            URL_SERVER_TRANS = "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc";
            URL_SERVER_ADQ = "https://agentemovildes.pagatodo.com/AgenteMovil_YG/Middleware.svc";
            URL_SERVER_FB = "http://10.10.45.13:6075/NotificacionesYG.svc/";
            URL_STARBUCKS = "https://crt-rewards.starbucks.mx:17443/CatalogosStarbucks.svc";

            PUBLIC_KEY_RSA = "rk2QHAmXByr9wIf6d1cgU+f9NtKvj2xWvRv2wUcZSMVvhfTkcoWLG/CxEK+weoS3QcxxEWKFrWgwhYABXpkGhlXiqH7GyRIhv2kQtuZlGJJSIExd2asJrtjDnfStu7ZKbdIpLzqFUfo8naDhCuQTzhyApyJQ9HDcOSTFuRhJ7Mz3gXwUXqr98i+he+iYCzyrMViP+o4UPUqfNcpSafUw4NYre9KEZoHMaKcPMR4bMjax3Payt9LDAU3KgBOnWS9Ga6WffE03tpAWqE3ape61CmPw5QKPgRNKSnV70wu7f02jmstEepM35aSf3gL9SKMUv3DkwYIpifhNYPbdKCh+BQ==";
            PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";

            PIN_ADVO = "275a28946f92da9acab52475df6ec73a10a40811";
            PIN_TRANS = "275a28946f92da9acab52475df6ec73a10a40811";
            PIN_YA = "af0758ac6ce95cddd1ea59eceba5ba001636cc1d";
            PIN_STARBUCKS_1 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b187841fc20c45f5bcab2597a7ada23e9cbaf6c139b88bcac2ac56c6e5bb658e444f4dce6fed094ad4af4e109c688b2e957b899b13cae23434c1f35bf3497b6283488174d188786c0253f9bc7f4326575833833b330a17b0d04e9124ad867d6412dc744a34a11d0aea961d0b15fca34b3bce6388d0f82d0c948610cab69a3dcaeb379c00483586295078e84563cd19414ff595ec7b98d4c471b350be28b38fa0b9539cf5ca2c23a9fd1406e818b49ae83c6e81fde4cd3536b351d369ec12ba566e6f9b57c58b14e70ec79ced4a546ac94dc5bf11b1ae1c6781cb445533997f249b3f53457f861af33cfa6d7f81f5b84ad3f585371cb5a6d009e4187b384efa0f0203010001";
            PIN_STARBUCKS_2 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b2d805ca1c742db5175639c54a520996e84bd80cf1689f9a422862c3a530537e5511825b037a0d2fe17904c9b496771981019459f9bcf77a9927822db783dd5a277fb2037a9c5325e9481f464fc89d29f8be7956f6f7fdd93a68da8b4b82334112c3c83cccd6967a84211a22040327178b1c6861930f0e5180331db4b5ceeb7ed062aceeb37b0174ef6935ebcad53da9ee9798ca8daa440e25994a1596a4ce6d02541f2a6a26e2063a6348acb44cd1759350ff132fd6dae1c618f59fc9255df3003ade264db42909cd0f3d236f164a8116fbf28310c3b8d6d855323df1bd0fbd8c52954a16977a522163752f16f9c466bef5b509d8ff2700cd447c6f4b3fb0f70203010001";
            PIN_STARBUCKS_3 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100c771f856c71ed9ccb5adf6b497a3fba1e60b505f50aa3ada0ffc3d292443c61029c1fc554072eebdeadf9fb641f4484bc86efe4f57128b5bfa92dd5ee8adf3f01bb17b4dfbcffdd1e5f8e3dce7f5737fdf0149cf8c56c1bd37e35bbeb54f8b8bf0da4fc7e3dd554769dff25b7b074f3de5ac21c1c81d7ae8e7f60fa1aaf56fdea8654f10899c03f3897aa55e017233eda9e95a1e79f387c8dfc8c5fc37c89a9ad7b876ccb03ee7fde654eadf5f5241785957adf112d67fbcd59f70d3056cfaa37d6758dd26621d31920c79791c8ecfca7bc166afa87448fb8e82c29e2c995c7b2d5d9bbc5b579e7c3a7a13adf2a3185b2b590fcd5c3aeb6833c6281d82d1508b0203010001";
            PIN_STARBUCKS_4 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100bb58c112012e97d87d18aac8c2e585e2176c602ec98d3105391a069856dd54d7118c595b3db154ae4b218532165f5486e6d9b1d860896b58be72daa0004276b127594ccde3bad45cd9a67fbb2b75d54644bdec405c59b7dd599ff16af706fcd62f198a9512ba9acad530d238fc193b5b153b36d0434dd165a1d48bc16041b3d67017cc39c09c0ca03db711224eced9a97ad22a629ca00b4e2ad7c3615a85dd5c10b9543d2d03f849f0bc92b7b79c31c7e9b8aa820b05b931cd085bbb220bf69c8e8a551c764376f0e26ef0dfa82975e7c8a4878b6af1bb08c9361865ee5043b85d72d52839e1533e252cda2b4fdd8a9e5050e06f9ac4d5192689017573099b3b0203010001";

        } else {
            if (DEBUG) {
                URL_SERVER_ADTVO = "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc";
                URL_SERVER_TRANS = "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc";
                URL_SERVER_ADQ = "https://agentemovildes.pagatodo.com/AgenteMovil_YG/Middleware.svc";
                URL_SERVER_FB = "http://10.10.45.13:6075/NotificacionesYG.svc/";
                URL_STARBUCKS = "https://crt-rewards.starbucks.mx:17443/CatalogosStarbucks.svc";

                PUBLIC_KEY_RSA = "rk2QHAmXByr9wIf6d1cgU+f9NtKvj2xWvRv2wUcZSMVvhfTkcoWLG/CxEK+weoS3QcxxEWKFrWgwhYABXpkGhlXiqH7GyRIhv2kQtuZlGJJSIExd2asJrtjDnfStu7ZKbdIpLzqFUfo8naDhCuQTzhyApyJQ9HDcOSTFuRhJ7Mz3gXwUXqr98i+he+iYCzyrMViP+o4UPUqfNcpSafUw4NYre9KEZoHMaKcPMR4bMjax3Payt9LDAU3KgBOnWS9Ga6WffE03tpAWqE3ape61CmPw5QKPgRNKSnV70wu7f02jmstEepM35aSf3gL9SKMUv3DkwYIpifhNYPbdKCh+BQ==";
                PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";

                PIN_ADVO = "275a28946f92da9acab52475df6ec73a10a40811";
                PIN_TRANS = "275a28946f92da9acab52475df6ec73a10a40811";
                PIN_YA = "af0758ac6ce95cddd1ea59eceba5ba001636cc1d";
                PIN_STARBUCKS_1 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b187841fc20c45f5bcab2597a7ada23e9cbaf6c139b88bcac2ac56c6e5bb658e444f4dce6fed094ad4af4e109c688b2e957b899b13cae23434c1f35bf3497b6283488174d188786c0253f9bc7f4326575833833b330a17b0d04e9124ad867d6412dc744a34a11d0aea961d0b15fca34b3bce6388d0f82d0c948610cab69a3dcaeb379c00483586295078e84563cd19414ff595ec7b98d4c471b350be28b38fa0b9539cf5ca2c23a9fd1406e818b49ae83c6e81fde4cd3536b351d369ec12ba566e6f9b57c58b14e70ec79ced4a546ac94dc5bf11b1ae1c6781cb445533997f249b3f53457f861af33cfa6d7f81f5b84ad3f585371cb5a6d009e4187b384efa0f0203010001";
                PIN_STARBUCKS_2 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b2d805ca1c742db5175639c54a520996e84bd80cf1689f9a422862c3a530537e5511825b037a0d2fe17904c9b496771981019459f9bcf77a9927822db783dd5a277fb2037a9c5325e9481f464fc89d29f8be7956f6f7fdd93a68da8b4b82334112c3c83cccd6967a84211a22040327178b1c6861930f0e5180331db4b5ceeb7ed062aceeb37b0174ef6935ebcad53da9ee9798ca8daa440e25994a1596a4ce6d02541f2a6a26e2063a6348acb44cd1759350ff132fd6dae1c618f59fc9255df3003ade264db42909cd0f3d236f164a8116fbf28310c3b8d6d855323df1bd0fbd8c52954a16977a522163752f16f9c466bef5b509d8ff2700cd447c6f4b3fb0f70203010001";
                PIN_STARBUCKS_3 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100c771f856c71ed9ccb5adf6b497a3fba1e60b505f50aa3ada0ffc3d292443c61029c1fc554072eebdeadf9fb641f4484bc86efe4f57128b5bfa92dd5ee8adf3f01bb17b4dfbcffdd1e5f8e3dce7f5737fdf0149cf8c56c1bd37e35bbeb54f8b8bf0da4fc7e3dd554769dff25b7b074f3de5ac21c1c81d7ae8e7f60fa1aaf56fdea8654f10899c03f3897aa55e017233eda9e95a1e79f387c8dfc8c5fc37c89a9ad7b876ccb03ee7fde654eadf5f5241785957adf112d67fbcd59f70d3056cfaa37d6758dd26621d31920c79791c8ecfca7bc166afa87448fb8e82c29e2c995c7b2d5d9bbc5b579e7c3a7a13adf2a3185b2b590fcd5c3aeb6833c6281d82d1508b0203010001";
                PIN_STARBUCKS_4 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100bb58c112012e97d87d18aac8c2e585e2176c602ec98d3105391a069856dd54d7118c595b3db154ae4b218532165f5486e6d9b1d860896b58be72daa0004276b127594ccde3bad45cd9a67fbb2b75d54644bdec405c59b7dd599ff16af706fcd62f198a9512ba9acad530d238fc193b5b153b36d0434dd165a1d48bc16041b3d67017cc39c09c0ca03db711224eced9a97ad22a629ca00b4e2ad7c3615a85dd5c10b9543d2d03f849f0bc92b7b79c31c7e9b8aa820b05b931cd085bbb220bf69c8e8a551c764376f0e26ef0dfa82975e7c8a4878b6af1bb08c9361865ee5043b85d72d52839e1533e252cda2b4fdd8a9e5050e06f9ac4d5192689017573099b3b0203010001";
            } else {
                URL_SERVER_ADTVO = "https://wcf.yaganaste.com:8031/ServicioYaGanasteAdtvo.svc";
                URL_SERVER_TRANS = "https://wcf.yaganaste.com:8032/ServicioYaGanasteTrans.svc";
                URL_SERVER_ADQ = "https://adqyaganaste.pagatodo.com/Middleware.svc";
                URL_SERVER_FB = "http://10.10.45.13:6075/NotificacionesYG.svc/";
                URL_STARBUCKS = "https://crt-rewards.starbucks.mx:17443/CatalogosStarbucks.svc";

                PUBLIC_KEY_RSA = "pIznw1pWFzzOVI+Shkg56ujssxRhQv1DTHeU5LMtgSNCOY3iw1TacI6+Db/YUQsexjvfEcjQsg9QOJp3Q1maI5hEMiWG84tsKBpgBckZoDKcaoN7JtGo3p2BIG/eCm1yLmxSrDpcnNZ6Z8GnUGaQPWxy75E8/U57XrpAyURFNTbbeq0uSxkcoB/5xmyjoECTKpWfD+M8PpAnisBLd0oSYTZ+tmBdgLPQJOe794ZyV+DoX5eU9G7hKx1onCHpuPb/xtx3rnMUIR2qB0sD0hKAjXsyvEiNrrihfxLa3IdcEnWn5CcxcsYiaSJqLlDKlkr+07ji/CqYm5hcvr2CbhhUfw==";
                PUBLIC_STARBUCKS_KEY_RSA = "xymBd9bjA9QcZDSMoqvDts/zvoOTt1xjgzXRWzSD2eHoVHrpVhPR8hJzXiJyjAQ/fzUSsFUoYzIs6irVtZpgbggvhbAs/ItkvbypUzFN4CPnWCmYCVNqf/hwnniVTOn1EJ8WhPXdc5r5PPgBX74GnEvy2GI4n8santnvQq3WvTE=";

                PIN_ADVO = "3f3add61acd8b7a3ad1536566669e731ea6e9cea";
                PIN_TRANS = "3f3add61acd8b7a3ad1536566669e731ea6e9cea";
                PIN_YA = "275a28946f92da9acab52475df6ec73a10a40811";
                PIN_STARBUCKS_1 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b187841fc20c45f5bcab2597a7ada23e9cbaf6c139b88bcac2ac56c6e5bb658e444f4dce6fed094ad4af4e109c688b2e957b899b13cae23434c1f35bf3497b6283488174d188786c0253f9bc7f4326575833833b330a17b0d04e9124ad867d6412dc744a34a11d0aea961d0b15fca34b3bce6388d0f82d0c948610cab69a3dcaeb379c00483586295078e84563cd19414ff595ec7b98d4c471b350be28b38fa0b9539cf5ca2c23a9fd1406e818b49ae83c6e81fde4cd3536b351d369ec12ba566e6f9b57c58b14e70ec79ced4a546ac94dc5bf11b1ae1c6781cb445533997f249b3f53457f861af33cfa6d7f81f5b84ad3f585371cb5a6d009e4187b384efa0f0203010001";
                PIN_STARBUCKS_2 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b2d805ca1c742db5175639c54a520996e84bd80cf1689f9a422862c3a530537e5511825b037a0d2fe17904c9b496771981019459f9bcf77a9927822db783dd5a277fb2037a9c5325e9481f464fc89d29f8be7956f6f7fdd93a68da8b4b82334112c3c83cccd6967a84211a22040327178b1c6861930f0e5180331db4b5ceeb7ed062aceeb37b0174ef6935ebcad53da9ee9798ca8daa440e25994a1596a4ce6d02541f2a6a26e2063a6348acb44cd1759350ff132fd6dae1c618f59fc9255df3003ade264db42909cd0f3d236f164a8116fbf28310c3b8d6d855323df1bd0fbd8c52954a16977a522163752f16f9c466bef5b509d8ff2700cd447c6f4b3fb0f70203010001";
                PIN_STARBUCKS_3 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100c771f856c71ed9ccb5adf6b497a3fba1e60b505f50aa3ada0ffc3d292443c61029c1fc554072eebdeadf9fb641f4484bc86efe4f57128b5bfa92dd5ee8adf3f01bb17b4dfbcffdd1e5f8e3dce7f5737fdf0149cf8c56c1bd37e35bbeb54f8b8bf0da4fc7e3dd554769dff25b7b074f3de5ac21c1c81d7ae8e7f60fa1aaf56fdea8654f10899c03f3897aa55e017233eda9e95a1e79f387c8dfc8c5fc37c89a9ad7b876ccb03ee7fde654eadf5f5241785957adf112d67fbcd59f70d3056cfaa37d6758dd26621d31920c79791c8ecfca7bc166afa87448fb8e82c29e2c995c7b2d5d9bbc5b579e7c3a7a13adf2a3185b2b590fcd5c3aeb6833c6281d82d1508b0203010001";
                PIN_STARBUCKS_4 = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100bb58c112012e97d87d18aac8c2e585e2176c602ec98d3105391a069856dd54d7118c595b3db154ae4b218532165f5486e6d9b1d860896b58be72daa0004276b127594ccde3bad45cd9a67fbb2b75d54644bdec405c59b7dd599ff16af706fcd62f198a9512ba9acad530d238fc193b5b153b36d0434dd165a1d48bc16041b3d67017cc39c09c0ca03db711224eced9a97ad22a629ca00b4e2ad7c3615a85dd5c10b9543d2d03f849f0bc92b7b79c31c7e9b8aa820b05b931cd085bbb220bf69c8e8a551c764376f0e26ef0dfa82975e7c8a4878b6af1bb08c9361865ee5043b85d72d52839e1533e252cda2b4fdd8a9e5050e06f9ac4d5192689017573099b3b0203010001";
            }
        }
    }

	/*Estatus Respuesta de Ws YaGanaste*/

    //MAPS
    public final static int ZoomMap = 16;
    public static final int CODE_OK = 0;
    public static final int CODE_SESSION_EXPIRED = 16;

    public static final String CODE_ADQ_OK = "00";
    public static final int CODE_ERROR_INFO_AGENTE = 91;
    public static final int CODE_OFFLINE = -1;
    public static final int INVALID_TOKEN = 3001;
    public static final int INCORRECT_FORMAT = -2;
    public static final int DEVICE_ALREADY_ASSIGNED = 224;

    /*WS Adq*/
    public static final String ADQ_CODE_OK = "00";
    public static final String ADQ_ACCES_DENIED = "3001";
    public static final int ADQ_TRANSACTION_APROVE = 0;
    public static final int ADQ_TRANSACTION_ERROR = 1;

    /**
     * Catálogos
     **/
    /*CRM – Adquiriente*/
    public static final int CRM_PENDIENTE = 1;
    public static final int CRM_DOCTO_APROBADO = 2;

    public static final int DOC_ID_FRONT = 5;
    public static final int DOC_ID_BACK = 6;
    public static final int DOC_DOM_FRONT = 7;
    public static final int DOC_DOM_BACK = 30;

    public static final int STATUS_DOCTO_RECHAZADO = 3;
    public static final int STATUS_DOCTO_APROBADO = 2;
    public static final int STATUS_DOCTO_PENDIENTE = 1;

    /*Id's de Preguntas  en Cuestionario Negocio*/ //TODO esto debería de ser un catálogo de ws
    public static final int PREGUNTA_RANGOS_MONTOS = 1;
    public static final int PREGUNTA_RANGO_OPERACIONES = 2;
    //public static final int PREGUNTA_GIRO = 3;
    public static final int PREGUNTA_FAMILIAR = 4;
    public static final int PREGUNTA_DOMICILIO = 5;
    //public static final int PREGUNTA_BENEFICIARIOS = 6;
    public static final int PREGUNTA_ERES_MEXICANO_NATURALIZADO = 10;
    public static final int PREGUNTA_COBROS = 1;
    public static final int PREGUNTA_MONTOS = 2;
    public static final int PREGUNTA_ORIGEN_RECURSOS = 11;
    public static final int PREGUNTA_DESTINO_RECURSOS = 12;

    //Constantes
    public static final String CARD_PAY = "PAGO CON TARJETA";

	/* Codigos de Acciones*/

    public static final int NO_ACTION = -2;
    public static final int GO_LOGIN = -3;

    /*Adq / Dongle*/
    public static final int ENCENDIENDO = 100;
    public static final int ENCENDIDO = 110;
    public static final int APAGANDO = 120;
    public static final int APAGADO = 130;
    public static final int DESCONECTADO = 140;
    public static final int LEYENDO = 150;
    public static final int SW_ERROR = 160;
    public static final int LECTURA_OK = 170;
    public static final int SW_TIMEOUT = 180;
    public static final int ENCENDIDO_TIMEOUT = 190;
    public static final int BATTERY_LOW = 200;
    public static final int READ_KSN = 210;
    public static final int READ_KSN_ERROR = 220;
    public static final int ERROR_LECTOR = 230;
    public static final int CONFIG_READER_OK = 240;
    public static final int CONFIG_READER_OK_ERROR = 250;
    public static final int READ_BATTERY_LEVEL = 260;
    public static final int EMV_DETECTED = 270;
    public static final int REQUEST_AMOUNT = 280;
    public static final int REQUEST_TIME = 290;
    public static final int REQUEST_IS_SERVER_CONNECTED = 300;
    public static final int REQUEST_FINAL_CONFIRM = 310;
    public static final int REQUEST_PIN = 320;
    public static final int REQUEST_SELECT_APP = 330;

    public static final String TRANSACTION_SEQUENCE = "TRANSACTION_SEQUENCE";
    public static final String IPOS_READER_STATES = "EMVSWIPE_STATES";
    public static final int READER_TRANSACTION = 0x31;

    public static final String MSJ = "MSJ";
    public static final String ESTADO = "ESTADO";
    public static final String VALOR = "VALOR";
    public static final String TRANSACTION = "TRANSACTION";
    public static final String APP_LIST = "APP_LIST";
    public static final String ERROR = "ERROR";
    public static final String IDERROR = "IDERROR";
    public static final String BATTERY_LEVEL = "BATTERY_LEVEL";
    public static final String READER_IS_CHARGING = "READER_IS_CHARGING";
    public static final String BATTERY_PORCENTAGE = "BATTERY_PORCENTAGE";
    public static final String KSN_LECTOR = "KSN_LECTOR";
    /*Url Legales*/
    public static final String URL_LEGALES_TERMINOS = "http://dev.yaganaste.com/terminos-y-condiciones.html";
    public static final String URL_LEGALES_PRIVACIDAD = "http://dev.yaganaste.com/aviso-de-privacidad.html";

    public static final String URL_LEGALES_TERMINOS_LINEAC = "http://dev.yaganaste.com/terminos-y-condiciones-lc.html";
    public static final String URL_LEGALES_PRIVACIDAD_LINEAC = "http://dev.yaganaste.com/aviso-de-privacidad-lc.html";

    public static final String DEFAULT_CARD = "5389 84";

    /*
    Var para controlar los mensajes de session del servicio, se tiene una variacion porque tenemos un error de ortografia
     */
    public static final String MESSAGE_OPEN_SESSION = "Por Favor Inicia Sesión";
    public static final String MESSAGE_CHANGE_PASS = "Tu Contraseña ha Sido Cambiada Correctamente";
    public static final String MESSAGE_OPEN_SESSION2 = "Por favor inicia sesión";

    /**
     * EstatusCuenta
     */
    public static final String ESTATUS_DE_NO_BLOQUEADA = "1";
    public static final String ESTATUS_CUENTA_DESBLOQUEADA = "1";
    public static final String ESTATUS_CUENTA_BLOQUEADA = "2";
    public static final int ESTATUS_CUENTA_CANCELADA = 3;
    public static final String FLAG_BLOQUEAR_CUENTA = "1";
    public static final String FLAG_DESBLOQUEAR_CUENTA = "2";

    public static final String CURRENT_TAB = "current_tab";

    /**
     * Estatus de movimiento
     */
    public static final String ESTATUS_CANCELADO = "1";
    public static final String ESTATUS_POR_REMBOLSAR = "2";
    public static final String ESTATUS_REMBOLSADO = "3";

    /*Preferencias de Sesion*/
    public static final String CODE_ACTIVATION_FREJA = "CODEACTIVATIONFREJA";
    public static String COUCHMARK_EMISOR = "COUCHMARKEMISOR";
    public static String COUCHMARK_ADQ = "COUCHMARKADQ";
    public static String COUCHMARK_EDIT_FAV = "COUCHMARKEF";

    public static String CO_QUICK_EM = "CO_QUICK_EM";
    public static String CO_QUICK_ADQ = "CO_QUICK_ADQ";

    public static String SHA_256_FREJA = "SHA_256_FREJA";
    public static String SHA_256_STARBUCKS = "SHA_256_STARBUCKS";
    public static String GENERO = "GENERO";
    public static String HUELLACADENA = "HUELLACADENA";
    public static String HUELLA_FAIL = "HUELLA_FAIL";
    public static String VERSION_APP = "VERSION_APP";
    public static String FIREBASE_KEY = "FIREBASE_KEY";
    public static String NOTIF_COUNT = "NOTIF_COUNT";

    public static String SEND_DOCUMENTS = "FLAG_DOCUMENTS";
    public static final String ADQ_PROCESS = "FLAG_PROCESS";
    public static final String CUPO_PROCESS = "FLAG_PROCESS_CUPO";
    public static String CUPO_COMPLETE = "CUPO_COMPLETE";

    // Preferencias para Foto de Usuario. URL
    public static final String URL_PHOTO_USER = "URL_PHOTO_USER";

    // Preferencias Uso de Huella Digital
    public static final String USE_FINGERPRINT = "USE_FINGERPRINT";
    public static final String FINGERPRINT_KEY = "YAGANASTEFINGERK";

    public static final String SPACE = " ";
    public static final String HAS_SESSION = "HAS_SESSION";
    public static final String PASSWORD_CHANGE = "PASSWORD_CHANGE";

    public static final String IS_CUPO = "IS_CUPO";
    public static final String ESTADO_RECHAZADO = "ESTADO_RECHAZADO";
    public static final String NAME_USER = "NAME_USER";
    public static final String FULL_NAME_USER = "FULL_NAME_USER";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String CARD_NUMBER = "CARD_NUMBER";
    public static final String USER_BALANCE = "USER_BALANCE";
    public static final String ADQUIRENTE_BALANCE = "ADQUIRENTE_BALANCE";
    public static final String STARBUCKS_BALANCE = "STABUCKS_BALANCE";
    public static final String CUPO_BALANCE = "CUPO_BALANCE";
    public static final String UPDATE_DATE = "UPDATE_DATE";
    public static final String UPDATE_DATE_BALANCE_ADQ = "UPDATE_DATE_BALANCE_ADQ";
    public static final String UPDATE_DATE_BALANCE_CUPO = "UPDATE_DATE_BALANCE_CUPO";
    public static final String ID_CUENTA = "ID_CUENTA";
    public static final String ADQUIRENTE_APPROVED = "ADQUIRENTE_APPROVED";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CLABE_NUMBER = "CLABE_NUMBER";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String CATALOG_VERSION = "CATALOG_VERSION";
    public static final String SIMPLE_NAME = "SIMPLE_NAME";
    public static final String PSW_CPR = "PSW_CPR";

    public static final String HAS_PROVISIONING = "HAS_PROVISIONING";
    public static final String USER_PROVISIONED = "USER_PROVISIONED";
    public static final String HAS_PUSH = "HAS_PUSH";

    public static final String OLD_NIP = "OLD_NIP";
    public static final String HAS_TOKEN_ONLINE = "HAS_TOKEN_ONLINE";
    public static final String TOKEN_FIREBASE = "TOKEN_FIREBASE";
    public static final String TOKEN_FIREBASE_STATUS = "TOKEN_FIREBASE_STATUS";
    public static final String TOKEN_FIREBASE_SUCCESS = "TOKEN_FIREBASE_SUCCESS";
    public static final String TOKEN_FIREBASE_FAIL = "TOKEN_FIREBASE_FAIL";

    public static final String ES_AGENTE = "ES_AGENTE"; // boolean
    public static final String ES_AGENTE_RECHAZADO = "ES_AGENTE_RECHAZADO"; //boolean
    public static final String ESTATUS_AGENTE = "ESTATUS_AGENTE"; // int
    public static final String ESTATUS_DOCUMENTACION = "ESTATUS_DOCUMENTACION"; // int
    public static final String ID_USUARIO_ADQUIRIENTE = "ID_USUARIO_ADQUIRIENTE"; // string
    public static final String TIPO_AGENTE = "TOKEN_SESION_ADQUIRIENTE"; // string
    public static final String ID_ESTATUS = "ID_ESTATUS"; // int

    public static final String HAS_STARBUCKS = "HAS_STARBUCKS";  //boolean
}