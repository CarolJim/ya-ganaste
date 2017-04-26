package com.pagatodo.yaganaste.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.GiroComercio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;


@SuppressLint("SimpleDateFormat")
public class Utils {

    private static int numberIntents;
    private static final String PATERN_MONTO = "\\d*\\.\\d*";

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {/**/
        }
    }

    public static String stringtoCurrency(String amount) {

        String ans = "";
        try {
            NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);
            ans = usFormat.format(Double.valueOf(amount));
        } catch (Exception e) {
            ans = "$0.00";
        }

        return ans;

    }

    public static String stringtoCurrencyNoFraction(String amount) {

        String ans = "";
        String signo = "";
        try {
            NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);
            usFormat.setMaximumFractionDigits(0);
            if (Double.parseDouble(amount) < 0) {
                signo = "-";
                amount = amount.replace(signo, "");
            }
            ans = usFormat.format(Double.valueOf(amount));
        } catch (Exception e) {
            ans = "$0";
        }
        ans = signo + ans;
        return ans;
    }

    public static String formatNumberToMoney(String number) {
        return new String("$" + number);
    }

    public static String formatNumberToMoney(Double number) {
        return formatNumberToMoney(number, "$0.00");
    }

    public static String formatNumberToMoney(Double number, String pattern) {
        DecimalFormat f = (DecimalFormat) NumberFormat.getInstance();
        f.setDecimalSeparatorAlwaysShown(true);
        f.applyPattern(pattern);

        return f.format(number);
    }

    private final static Pattern rfc2822 = Pattern
            .compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    public static boolean isValidEmailAddress(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String parseNumberToPhone(String phone) {
        String res = null;
        String aux = null;
        boolean city = false;
        if (phone != null)
            aux = phone;
        if (aux.length() > 2) {
            for (int i = 2; i < aux.length(); i++) {
                if (i == 2) {
                    if (isCity(aux.subSequence(0, 2).toString())) {
                        res = new String("(" + aux.subSequence(0, 2) + ")");
                        city = true;
                    } else
                        res = aux.substring(0, 2);
                }
                if (i == 3) {
                    if (city == false)
                        res = new String("(" + aux.subSequence(0, 3) + ")");
                }
                if (city == true) {
                    if (i % 2 == 0 && i != 4 && i != 8)
                        res = res.concat("-" + aux.charAt(i));
                    else
                        res = res.concat("" + aux.charAt(i));
                } else {
                    if (i % 3 == 0 && i != 9)
                        res = res.concat("-" + aux.charAt(i));
                    else
                        res = res.concat("" + aux.charAt(i));
                }
            }
        } else
            res = new String(aux);
        return res;
    }

    public static String[] parseNumberToPhoneArray(String phone) {
        String[] res = new String[2];
        res[0] = "";
        res[1] = "";
        //res[2] = "";
        String aux = "";
        if (phone != null)
            aux = phone;
        if (aux.length() > 1) {
            if (isCity(aux.subSequence(0, 2).toString())) {
                res[0] = aux.subSequence(0, 2).toString();
                res[1] = aux.length() > 2 ? aux.subSequence(2, 3).toString()
                        : "";
            } else {
                res[0] = aux.length() > 2 ? aux.subSequence(0, 3).toString()
                        : aux.subSequence(0, 2).toString();
            }
            if (aux.length() > 3) {
                if (aux.length() < 7)
                    res[1] = res[1].concat(aux.subSequence(3, aux.length())
                            .toString());
                else {
                    res[1] = res[1].concat(aux.subSequence(3, 6).toString());
                    res[2] = aux.subSequence(6, aux.length()).toString();
                }
            }
        } else {
            res[0] = new String(aux);
        }
        return res;
    }

    //TAG: TELEFONO
    public static boolean parserTelefono(String phone) {
        boolean result = false;


        return result;
    }

    public static boolean isCity(String lada) {
        // df 55
        // mry 81
        // gdl 33
        // tol 72
        if (lada.equals("55") || lada.equals("81") || lada.equals("33")
                || lada.equals("72"))
            return true;
        else
            return false;
    }

    private final static Pattern amopuntRfc2822 = Pattern
            .compile("^(\\d{1,4})?(\\.\\d{1,2})?$");

    public static boolean isValidAmount(String amount) {
        if (amount == null)
            return false;
        if (amopuntRfc2822.matcher(amount).matches())
            return true;
        else
            return false;
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String getMD5(String msg) {
        String ans = null;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            ans = Utils.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            ans = String.valueOf(msg.hashCode());
        }
        return ans;
    }

    public static String getSHA1(String msg) {
        String ans = null;

        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA1");
            md.update(msg.getBytes());
            ans = Utils.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            ans = String.valueOf(msg.hashCode());
        }

        return ans;
    }

    public static String getSHA256(String msg) {
        String ans = null;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA256");
            md.update(msg.getBytes());
            ans = Utils.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            ans = String.valueOf(msg.hashCode());
        }

        return ans;
    }

    public static boolean paro(char car) {
        boolean eq = true;
        if (car == ',' || car == ';' || car > 64 && car < 91 || car > 96
                && car < 123 || car == '/')
            eq = false;
        return eq;
    }

    public static boolean isNumber(char car) {
        boolean res = false;
        if (car > 47 && car < 58)
            res = true;
        return res;
    }

    public static String parsePhoneNumber(String tel) {
        String res = null;
        int count = 0, index = 0;
        char[] numeros = new char[10];
        char[] aux = new char[10];
        while (count < 10 && paro(tel.charAt(index)) && index < tel.length()) {
            if (isNumber(tel.charAt(index))) {
                numeros[count] = tel.charAt(index);
                count++;
            }
            index++;
        }
        if (count < 10) {
            if (count > 7) {
                int j = 0;
                if (count == 8) {
                    aux[0] = '5';
                    aux[1] = '5';
                    j = 2;
                }
                if (count == 9) {
                    aux[0] = '5';
                    j = 1;
                }
                for (int i = 0; i < 8; i++) {
                    aux[i + j] = numeros[i];
                }
                res = new String(String.copyValueOf(aux));
            }
        } else {
            res = new String(String.copyValueOf(numeros));
        }
        return res;
    }

    public static String objectToString(Serializable obj) {

        if (obj == null)
            return "";

        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream;
            objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return Codec.asHexStr(serialObj.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public static Serializable stringToObject(String str) {

        if (str == null || str.length() == 0)
            return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(
                    Codec.asBytes(str));
            ObjectInputStream objStream;
            objStream = new ObjectInputStream(serialObj);
            return (Serializable) objStream.readObject();
        } catch (Exception e) {
            return null;
        }

    }

    @SuppressLint("DefaultLocale")
    public static String Bytes2HexString(byte[] b, int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static String getUdid(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public static void setDefaultPreferences() {
        /*
        Preferencias prefs = App.getInstance().getPrefs();
        if (prefs.loadData("INICIALIZED") == null) {
            prefs.saveData("INICIALIZED", "ACTUALIZACION2.0");
            prefs.saveData("URL_SERVER", Recursos.URL_SERVER);
            prefs.saveData("URL_NOTIFICACIONES", Recursos.URL_NOTIFICACIONES);
            prefs.saveData("URL_SWITCH", Recursos.URL_SWITCH_SERVER);
            prefs.saveData("PIN_PT", Recursos.PIN_PT);
            prefs.saveData("SESION", Recursos.SESION);
            prefs.saveData("VERSION", Recursos.VERSION_NAME);
            prefs.saveData("REG_AUT", Recursos.REG_AUT);
            prefs.saveData("TRACKER_ID", Recursos.TRACKER_ID_PRODUCCION);
            prefs.saveData("URL_TERMINOS_CONDICIONES", Recursos.URL_TERMINOS_Y_CONDICIONES);
            prefs.saveData(Recursos.TRANSACTION_SEQUENCE, "0");
            prefs.saveData("IS_CVV_REQUIRED", Boolean.toString(Recursos.IS_CVV_REQUIRED));
            prefs.saveData("REEMBOLSO", Boolean.toString(Recursos.IS_REEMBOLSO_VISIBLE));
            prefs.saveData("APLICACION_BANCARIA", Boolean.toString(Recursos.IS_APLICACION_BANCARIA));
            prefs.saveData("VISIBLE_DAYS", Recursos.VISIBLE_DAYSVAL);
            prefs.saveData("DEVOLUCION", Boolean.toString(Recursos.IS_DEVOLUCION_VISIBLE));
            prefs.saveData("PROPINA", Boolean.toString(Recursos.IS_PROPINA_ACTIVA));
            numberIntents = 0;
        } else {
            if (!prefs.loadData("INICIALIZED").equals("ACTUALIZACION2.0")) {
                prefs.saveData("URL_SWITCH", Recursos.URL_SWITCH_SERVER);
                prefs.saveData(Recursos.TRANSACTION_SEQUENCE, "0");
                prefs.saveData("IS_CVV_REQUIRED", Boolean.toString(Recursos.IS_CVV_REQUIRED));
                prefs.saveData("APLICACION_BANCARIA", Boolean.toString(Recursos.IS_APLICACION_BANCARIA));
                prefs.saveData("INICIALIZED", "ACTUALIZACION2.0");
                prefs.saveData("VISIBLE_DAYS", Recursos.VISIBLE_DAYSVAL);
                prefs.saveData("REEMBOLSO", Boolean.toString(Recursos.IS_REEMBOLSO_VISIBLE));
                prefs.saveData("PROPINA", Boolean.toString(Recursos.IS_PROPINA_ACTIVA));
                prefs.saveData("DEVOLUCION", Boolean.toString(Recursos.IS_DEVOLUCION_VISIBLE));
            }
        }*/
    }

    public static void initPreferences() {
       /* Preferencias prefs = App.getInstance().getPrefs();
        if (!prefs.compare(Recursos.VERSION, Recursos.VERSION_NAME)) {
            prefs.saveData("URL_SERVER", Recursos.URL_SERVER);
            prefs.saveData("URL_NOTIFICACIONES", Recursos.URL_NOTIFICACIONES);
            prefs.saveData("URL_SWITCH", Recursos.URL_SWITCH_SERVER);
            prefs.saveData("PIN_PT", Recursos.PIN_PT);
            prefs.saveData("SESION", Recursos.SESION);
            prefs.saveData("VERSION", Recursos.VERSION_NAME);
            prefs.saveData("TRACKER_ID", Recursos.TRACKER_ID_PRODUCCION);
            prefs.saveData("URL_TERMINOS_CONDICIONES", Recursos.URL_TERMINOS_Y_CONDICIONES);
            prefs.saveData(Recursos.TRANSACTION_SEQUENCE, "0");
            prefs.saveData("IS_CVV_REQUIRED", Boolean.toString(Recursos.IS_CVV_REQUIRED));
            prefs.saveData("REEMBOLSO", Boolean.toString(Recursos.IS_REEMBOLSO_VISIBLE));
            prefs.saveData("APLICACION_BANCARIA", Boolean.toString(Recursos.IS_APLICACION_BANCARIA));
            prefs.saveData("DEVOLUCION", Boolean.toString(Recursos.IS_DEVOLUCION_VISIBLE));
            prefs.saveData("PROPINA", Boolean.toString(Recursos.IS_PROPINA_ACTIVA));
            numberIntents = 0;
        }*/
    }


    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

    // private final static Pattern RFC2822_PATTERN =
    // Pattern.compile("^\\$?(\\d{0,1})?(\\.(\\d{0,2})?)?$");
    private final static Pattern RFC2822_PATTERN = Pattern
            .compile("^[0]\\$?(\\d{0,5})?(\\.(\\d{0,2})?)?$");

    public static boolean validateAmount(String toCheck) {
        if (RFC2822_PATTERN.matcher(toCheck).matches())
            return true;
        else
            return false;
    }


    @SuppressLint("DefaultLocale")
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static final byte[] hexStringToByteArray(char[] src, int off, int len) {
        if ((len & 1) != 0)
            throw new IllegalArgumentException(
                    "The argument 'len' can not be odd value");

        final byte[] buffer = new byte[len / 2];

        for (int i = 0; i < len; i++) {
            int nib = src[off + i];

            if ('0' <= nib && nib <= '9') {
                nib = nib - '0';
            } else if ('A' <= nib && nib <= 'F') {
                nib = nib - 'A' + 10;
            } else if ('a' <= nib && nib <= 'f') {
                nib = nib - 'a' + 10;
            } else {
                throw new IllegalArgumentException(
                        "The argument 'src' can contains only HEX characters");
            }

            if ((i & 1) != 0) {
                buffer[i / 2] += nib;
            } else {
                buffer[i / 2] = (byte) (nib << 4);
            }
        }

        return buffer;
    }

    public static final byte[] hexStringToByteArray(char[] src) {
        return hexStringToByteArray(src, 0, src.length);
    }

    public static final byte[] hexStringToByteArray(String s) {
        char[] src = s.toCharArray();
        return hexStringToByteArray(src);
    }

    public static final byte[] hexStringToByteArray(String s, char delimiter) {
        char src[] = s.toCharArray();
        int srcLen = 0;

        for (char c : src) {
            if (c != delimiter)
                src[srcLen++] = c;
        }

        return hexStringToByteArray(src, 0, srcLen);
    }

    @SuppressLint("DefaultLocale")
    public static String byteToArray(byte byteElement) {
        String cad = null;
        cad = Integer.toHexString(byteElement & 0xFF);
        if (cad.length() == 1) {
            cad = '0' + cad;
        }
        cad = cad.toUpperCase();
        return cad;
    }

    public static final String byteArrayToHexString(byte[] scr, int off, int len) {
        final char[] buf = new char[len * 3];

        for (int i = 0, j = 0; i < len; i++) {
            buf[j++] = HEX[((scr[off + i] >> 4) & 0xf)];
            buf[j++] = HEX[((scr[off + i]) & 0xf)];
            buf[j++] = ' ';
        }

        return new String(buf);
    }

    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static final String byteArrayToHexString(byte[] src) {
        return byteArrayToHexString(src, 0, src.length);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timeStamp = sdf.format(date);
        return timeStamp;
    }

    public static String getCurrentDay() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        String timeStamp = sdf.format(date);
        char[] chars = timeStamp.toCharArray();
        chars[3] = Character.toUpperCase(chars[3]);
        timeStamp = new String(chars);
        return timeStamp;
    }

    public static String getDay() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String timeStamp = sdf.format(date);
        return timeStamp;
    }

    public static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*
                                                                     * YOUR_CHARSET?
																	 */)));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHourPhone() {
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formatteHour = df.format(dt.getTime());
        return formatteHour;
    }


    public static String segmentofinal(String segmentofinal, String operacion) {
        String cadena = "";
        String res;
        int tamanio = 0;
        int segmentoCadena = 0;
        if (operacion == "tae")
            cadena = Utils.parseNumberToPhone(segmentofinal);
        else
            cadena = (segmentofinal);
        tamanio = cadena.length();
        if (tamanio <= 4)
            res = segmentofinal;
        else {
            segmentoCadena = tamanio - 4;
            res = cadena.substring(segmentoCadena);
        }
        return res;
    }

    public static String formatIave(String iave) {
        String res = null;
        String aux = null;
        boolean espacio = false;
        if (iave != null) {
            aux = iave;
            if (aux.length() > 8) {
                for (int i = 8; i < aux.length(); i++) {
                    if (i == 8) {
                        res = new String(aux.substring(0, 8) + " ");
                        espacio = true;
                    }
                    if (espacio) {
                        if (i == 10)
                            res = new String(aux.substring(0, 8) + " "
                                    + aux.substring(8, 10));
                        else
                            res = res.concat(aux.charAt(i) + "");
                    }
                }
            } else
                res = new String(aux);
        }
        return res;
    }

    public static String formatCfe(String cfe) {
        String res = null;
        String aux = null;
        boolean espacio = false;
        if (cfe != null) {
            aux = cfe;
            if (aux.length() > 2) {
                for (int i = 2; i < aux.length(); i++) {
                    if (i == 2) {
                        res = new String(aux.substring(0, 2) + " ");
                        espacio = true;
                    }
                    if (espacio) {
                        if (i == 13)
                            res = new String(aux.substring(0, 2) + " "
                                    + aux.substring(2, 14) + " ");
                        else if (i == 19)
                            res = new String(aux.substring(0, 2) + " "
                                    + aux.substring(2, 14) + " "
                                    + aux.substring(14, 20) + " ");
                        else if (i == 28)
                            res = new String(aux.substring(0, 2) + " "
                                    + aux.substring(2, 14) + " "
                                    + aux.substring(14, 20) + " "
                                    + aux.substring(20, 29) + " ");
                        else if (i == 29)
                            res = new String(aux.substring(0, 2) + " "
                                    + aux.substring(2, 14) + " "
                                    + aux.substring(14, 20) + " "
                                    + aux.substring(20, 29) + " "
                                    + aux.substring(29, 30));
                        else
                            res = res.concat(aux.charAt(i) + "");

                    }
                }
            } else
                res = new String(aux);
        }
        return res;

    }

    public static String formatAvon(String avon) {
        String res = null;
        String aux = null;
        boolean espacio = false;
        if (avon != null) {
            aux = avon;
            if (aux.length() > 4) {
                for (int i = 4; i < aux.length(); i++) {
                    if (i == 4) {
                        res = new String(aux.substring(0, 4) + " ");
                        espacio = true;
                    }
                    if (espacio) {
                        if (i == 7)
                            res = new String(aux.substring(0, 4) + " "
                                    + aux.substring(4, 8) + " ");
                        else if (i == 11)
                            res = new String(aux.substring(0, 4) + " "
                                    + aux.substring(4, 8) + " "
                                    + aux.substring(8, 12) + " ");
                        else if (i == 13)
                            res = new String(aux.substring(0, 4) + " "
                                    + aux.substring(4, 8) + " "
                                    + aux.substring(8, 12) + " "
                                    + aux.substring(12, 14));
                        else
                            res = res.concat(aux.charAt(i) + "");

                    }
                }
            } else
                res = new String(aux);
        }
        return res;
    }


    public static String formatFecha(Activity context, String fecha) {
        String[] meses = context.getResources().getStringArray(
                R.array.meses_array);
        String[] partes = TextUtils.split(fecha, Pattern.quote("/"));
        String fechaFormateada = "";
        if (partes.length == 3)
            fechaFormateada = String.format(
                    context.getString(R.string.fecha_formater), partes[0],
                    meses[Integer.parseInt(partes[1]) - 1], partes[2]);
        return fechaFormateada;
    }

    public static String getVersionName(Activity context) {
        PackageInfo pInfo = null;
        String versionName = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "1.0.0";
        }
        return versionName;
    }

    @SuppressLint("SimpleDateFormat")
    public static String cambiarFormatoFecha(String fecha) {
        DateFormatSymbols mesesFormato = cambioFormatoMeses();
        String input = "yyyy/MM/dd kk:mm:ss";
        String output = "dd/MMM/yy kk:mm:ss";
        SimpleDateFormat formatoEntrada = new SimpleDateFormat(input);
        SimpleDateFormat formatoSalida = new SimpleDateFormat(output, mesesFormato);

        Date date = null;
        String fechaFormateada = null;

        try {
            date = formatoEntrada.parse(fecha);
            fechaFormateada = formatoSalida.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaFormateada;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date cambiarFormatoFechaDate(String fecha) {
        DateFormatSymbols mesesFormato = cambioFormatoMeses();
        String input = "yyyy/MM/dd kk:mm:ss";
        String output = "dd/MMM/yy kk:mm";
        SimpleDateFormat formatoEntrada = new SimpleDateFormat(input);
        SimpleDateFormat formatoSalida = new SimpleDateFormat(output, mesesFormato);

        Date date = null;
        String fechaFormateada = null;

        try {
            date = formatoEntrada.parse(fecha);
            fechaFormateada = formatoSalida.format(date);
            return formatoSalida.parse(fechaFormateada);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DateFormatSymbols cambioFormatoMeses() {
        DateFormatSymbols fechasActual = new DateFormatSymbols(new Locale("es", "ES"));
        String[] anteriorMeses = fechasActual.getShortMonths();
        String[] actualMeses = new String[anteriorMeses.length];
        for (int i = 0; i < 12; i++) {
            if (anteriorMeses.length > 0) {
                actualMeses[i] = Character.toUpperCase(anteriorMeses[i].charAt(0)) + anteriorMeses[i].substring(1).replace(".", "");
            } else {
                actualMeses[i] = "";
            }
        }
        fechasActual.setShortMonths(actualMeses);
        return fechasActual;
    }

    public static String FormatAmount(String monto) {
        String tmp = null;
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(dfs);
        tmp = formatter.format(Double.parseDouble(monto));
        return tmp;
    }

    public static boolean isValidMail(String email) {
        int index = email.indexOf("@");
        return (!email.isEmpty()) && (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                && (!email.substring(index - 1, index).equals("."));
    }

    public static boolean isValidFormatPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static InputFilter AlphaNumericFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals("")) {
                return source;
            }
            if (source.toString().matches("^([a-zA-ZÀ-ú0-9ÜüÑñ @#&.,_-]+)$")) {
                return source;
            }
            return "";
        }
    };

    public static InputFilter NumericFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals("")) {
                return source;
            }
            if (source.toString().matches("^[0-9]+$")) {
                return source;
            }
            return "";
        }
    };

    public static InputFilter DecimalNumericFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals("")) {
                return source;
            }
            if (source.toString().matches("^[0-9]*\\.?[0-9]+$")) {
                return source;
            }
            return "";
        }
    };

    public static boolean isCadenaDeEspacios(String cadena) {
        String cadenaValidacion = cadena.trim();
        if (cadenaValidacion.length() == 0)
            return true;
        else
            return false;
    }

    public static InputFilter LetrasNumbersSpaceFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (source.equals("")) {
                return source;
            }
            if (source.toString().matches("[a-zA-ZñÑáéíñóúüÁÉÍÑÓÚÜ0-9 ]+")) {
                return source;
            }
            return "";
        }
    };

    public static boolean fileExists(String resourceURL, Activity context) {
        String cleanedResourceURL = resourceURL.replace("\\", "/");
        String path = context.getFilesDir().toString();
        String fileName = Uri.parse(cleanedResourceURL).getLastPathSegment();
        path = path + "/" + cleanedResourceURL.replace(fileName, "");
        File file = new File(path, fileName);

        return file.exists();
    }

    public static boolean writeResourceToInternalStorage(Bitmap bitmap, String filedir, String filename, Activity context) {
        if (bitmap == null) {
            return false;
        }
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(storageDir.getAbsolutePath() + filedir);
        dir.mkdirs();
        try {
            File createdFile = new File(dir, filename);
            FileOutputStream fileOutputStream = new FileOutputStream(createdFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();
            //bitmap.recycle();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File createImage(Bitmap bitmap, String filedir, String filename, Activity context) throws FileNotFoundException, IOException {
        File createdFile = null;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(storageDir.getAbsolutePath() + filedir);
        dir.mkdirs();
        createdFile = new File(dir, filename);
        FileOutputStream fileOutputStream = new FileOutputStream(createdFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();
        bitmap.recycle();
        return createdFile;
    }


    public static int convertDpToPixels(int dp) {
        DisplayMetrics displayMetrics = App.getInstance().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int convertPixelsToDp(int px) {
        DisplayMetrics displayMetrics = App.getInstance().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }


    public static String getFechaActual() {
        Calendar cal = Calendar.getInstance();
        String fechayhora, fechahoranew;
        fechayhora = DateFormat.format("yyyy/MM/dd kk:mm:ss", cal.getTime()).toString();
        fechahoranew = Utils.cambiarFormatoFecha(fechayhora);
        return fechahoranew;
    }

    public static String getLastWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Configuramos la fecha que se recibe
        calendar.add(Calendar.WEEK_OF_MONTH, -1);

        DateFormatSymbols mesesFormato = cambioFormatoMeses();
        String output = "dd/MMM/yy";
        SimpleDateFormat formatoSalida = new SimpleDateFormat(output, mesesFormato);
        try {
            return formatoSalida.format(calendar.getTime());
            //return formatoSalida.parse(fechaFormateada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentWeekFormmated() {
        DateFormatSymbols mesesFormato = cambioFormatoMeses();
        String output = "dd/MMM/yy";
        SimpleDateFormat formatoSalida = new SimpleDateFormat(output, mesesFormato);
        try {
            return formatoSalida.format(Calendar.getInstance().getTime());
            //return formatoSalida.parse(fechaFormateada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Calendar getCalendarOfLastWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Configuramos la fecha que se recibe
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        return calendar;
    }

    /**
     * Formatear la fecha con la hora
     *
     * @param date dateFormat "dd/MMM/yy"
     * @return date output "dd/MMM/yy kk:mm"
     */
    public static Date reFormmatingDate(String date) {
        try {
            date += " 23:59";
            return new SimpleDateFormat("dd/MMM/yy kk:mm", cambioFormatoMeses()).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getCurrencyValue(double value) {
        Locale mx = Locale.getDefault();
        Locale.setDefault(Locale.US);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        formatter.setNegativePrefix("-$");
        formatter.setNegativeSuffix("");
        formatter.setPositivePrefix("$");
        formatter.setPositiveSuffix("");
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumIntegerDigits(1);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        Locale.setDefault(mx);
        return formatter.format(value);
    }

    public static String getCurrencyValue(String value) {
        return getCurrencyValue(getDoubleValue(value));
    }

    public static double getDoubleValue(String value) {
        try {
            value = value.replace("$", "").replace(",", "").replace("%", "")
                    .replace("&nbsp;", "");
            if (value.isEmpty()) {
                return 0;
            }
            return Double.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDoubleValue(TextView textView) {
        return getDoubleValue(textView.getText().toString());
    }

    public static double getRoundValue(double value) {
        Locale mx = Locale.getDefault();
        Locale.setDefault(Locale.US);
        NumberFormat nf = new DecimalFormat("0.00");
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        value = Double.valueOf(nf.format(value).replace(",", ""));
        Locale.setDefault(mx);
        return value;
    }


    public static String getCurriencyValueNoDecimals(double value) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return defaultFormat.format(Math.round(getRoundValue(value)));
    }

    public static String getCurriencyValueNoDecimals(String value) {
        return getCurriencyValueNoDecimals(getDoubleValue(value));
    }

    public static String getCleanValue(double value) {
        return clean(getCurrencyValue(getRoundValue(value)));
    }

    public static String getCleanValue(String value) {
        return getCleanValue(getRoundValue(getDoubleValue(value)));
    }

    private static String clean(String value) {
        return value.replace("$", "").replace(",", "");
    }

    public static boolean ValidarMonto(String monto) {
        Pattern pattern = Pattern.compile(PATERN_MONTO);
        Matcher matcher = pattern.matcher(monto);
        return matcher.matches();
    }

    /*No sirve*/
    @Deprecated
    public static String FormatMonto(String monto) {
        String value = new String();
        if (monto != null && ValidarMonto(monto)) {

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat df = (DecimalFormat) nf;
            df.applyPattern("$###,###.00");
            value = df.format(Double.valueOf(monto));
        }

        return value;
    }


    public static String formatDatePickerDialog(int dayOfMonth, int monthOfYear, int year) {

        String day = "";
        String month = "";
        int iMonth = ++monthOfYear;
        if (dayOfMonth < 10) {
            day = String.format("0%s", dayOfMonth);
        } else {
            day = String.format("%s", dayOfMonth);
        }

        if (iMonth < 10) {
            month = String.format("0%s", iMonth);
        } else {
            month = String.format("%s", iMonth);
        }

        String date = String.format("%s/%s/%s", day, month, year);

        return date;

    }

    public static List<GiroComercio> getGirosArray(Context context) {
        List<GiroComercio> giroComercioList = null;
        Gson gson = new Gson();
        try {
            InputStream inputStream = context.getAssets().open("files/giros_comercio.json");
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            Type listType = new TypeToken<List<GiroComercio>>() {
            }.getType();
            giroComercioList = gson.fromJson(new String(buffer, "UTF-8"), listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return giroComercioList;
    }

    public static String getJSONStringFromAssets(Context context, String uri) {
        String response = "";
        try {
            InputStream inputStream = context.getAssets().open(uri);
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            response = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return response;
    }

    public static String getTokenDevice(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }

    public static byte[] getHash(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }

    public static String getScreenShotPath(View v1) {
        Date now = new Date();
        DateFormat.format(DateUtil.screenShotDateFormat, now);
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

        try {

            // View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            v1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v1.layout(0, 0, v1.getMeasuredWidth(), v1.getMeasuredHeight());

            v1.buildDrawingCache(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            mPath = "";
        }

        return mPath;
    }

    public static int getTransactionSequence() {
        Preferencias prefs = App.getInstance().getPrefs();
        int value = (Integer.parseInt(prefs.loadData(Recursos.TRANSACTION_SEQUENCE)) + 1) % 1000000;
        Log.i("IposListener: ","=====>>  transaction  "+value);
        prefs.saveData(Recursos.TRANSACTION_SEQUENCE, Integer.toString(value));
        return value;
    }

    public static String cipherRSA(String text){
        String result;
        try{
            byte[] expBytes = Base64.decode("AQAB".getBytes("UTF-8"), Base64.DEFAULT);
            byte[] modBytes = Base64.decode(PUBLIC_KEY_RSA.getBytes("UTF-8"), Base64.DEFAULT);

            BigInteger modules = new BigInteger(1, modBytes);
            BigInteger exponent = new BigInteger(1, expBytes);

            KeyFactory factory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");

            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modules, exponent);

            PublicKey pubKey = factory.generatePublic(pubSpec);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            result = Base64.encodeToString(encrypted, Base64.DEFAULT);
        }
        catch(Exception e){
            result = null;
        }
        return result;
    }
}
