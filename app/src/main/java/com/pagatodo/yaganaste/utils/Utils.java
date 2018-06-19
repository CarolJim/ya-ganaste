package com.pagatodo.yaganaste.utils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.GiroComercio;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

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
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.login.LoginException;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.FINGERPRINT_KEY;
import static com.pagatodo.yaganaste.utils.Recursos.GROUP_FORMAT;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;


@SuppressLint("SimpleDateFormat")
public class Utils {

    private static final String PATERN_MONTO = "\\d*\\.\\d*";
    private final static Pattern rfc2822 = Pattern
            .compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
    private final static Pattern amopuntRfc2822 = Pattern
            .compile("^(\\d{1,4})?(\\.\\d{1,2})?$");
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    // private final static Pattern RFC2822_PATTERN =
    // Pattern.compile("^\\$?(\\d{0,1})?(\\.(\\d{0,2})?)?$");
    private final static Pattern RFC2822_PATTERN = Pattern
            .compile("^[0]\\$?(\\d{0,5})?(\\.(\\d{0,2})?)?$");
    private static final char[] HEX = new char[]{'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
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
    private static int numberIntents;
    private static String uniqueID = null;

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
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

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

    public static int convertDpToPixels(int dp) {
        DisplayMetrics displayMetrics = App.getInstance().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
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


    public static int getTransactionSequence() {
        Preferencias prefs = App.getInstance().getPrefs();
        int value;
        String varTransaction = prefs.loadData(Recursos.TRANSACTION_SEQUENCE);

        if (varTransaction.isEmpty()) {
            varTransaction = "20";
        }
        value = (Integer.parseInt(varTransaction) + 1) % 1000000;
        prefs.saveData(Recursos.TRANSACTION_SEQUENCE, Integer.toString(value));

        return value;
    }

    public static String cipherRSA(String text, String rsaKey) {
        String result;
        try {
            byte[] expBytes = Base64.decode("AQAB".getBytes("UTF-8"), Base64.DEFAULT);
            byte[] modBytes = Base64.decode(rsaKey.getBytes("UTF-8"), Base64.DEFAULT);

            BigInteger modules = new BigInteger(1, modBytes);
            BigInteger exponent = new BigInteger(1, expBytes);

            KeyFactory factory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");

            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modules, exponent);

            PublicKey pubKey = factory.generatePublic(pubSpec);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            result = Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * Method used only to cipher string with password.
     * Implementation in {@link com.pagatodo.yaganaste.ui.account.AccountInteractorNew} line 672
     *
     * @value{mode} = (true)ENCRYPT_MODE / (false)DECRYPT_MODE
     */
    public static String cipherAES(String text, boolean mode) {
        String strData = "";
        try {
            SecretKeySpec skeyspec = new SecretKeySpec(FINGERPRINT_KEY.getBytes(), "AES/ECB/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            if (mode) {
                cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
                strData = new String(Base64.encode(cipher.doFinal(text.getBytes("UTF-8")), Base64.DEFAULT));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, skeyspec);
                strData = new String(cipher.doFinal(Base64.decode(text.getBytes("UTF-8"), Base64.DEFAULT)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strData;
    }

    /**
     * Se crea un string dummy para completar el número de tarjeta.
     */
    public static String getCardNumberRamdon() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String number = String.valueOf(timestamp.getTime());
        if (number.length() > 10) {
            number = number.substring(0, 10);
            String part1 = number.substring(0, 2);
            String part2 = number.substring(2, 6);
            String part3 = number.substring(6, 10); // Armamos el formato de la tarjeta
            number = String.format("%s %s %s", part1, part2, part3);
        }

        return number;
    }

    public static void sessionExpired() {
        Intent intent = new Intent(App.getContext(), MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        App.getContext().startActivity(intent);
    }

    public int convertPixelsToDp(int px) {
        DisplayMetrics displayMetrics = App.getInstance().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static boolean isDeviceOnline() {
        ConnectivityManager connManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTypeConnection() {
        ConnectivityManager connManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return networkInfo.getTypeName() + " " + networkInfo.getSubtypeName();
        } else {
            return "NO_CONNECTION";
        }
    }

    public static int calculateFilterLength(int rawLength) {
        return rawLength += (rawLength - 1) / GROUP_FORMAT;
    }

    public static void setDurationScale(float durationScale) {
        try {
            Field scale = ValueAnimator.class.getDeclaredField("sDurationScale");
            scale.setAccessible(true);
            scale.set(null, durationScale);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<CarouselItem> removeNullCarouselItem(ArrayList<CarouselItem> originalList) {
        ArrayList<CarouselItem> items = originalList;
        // Funcion para eliminar los nuos de nuestra lista. A futuro se cambiara por ComercioResponse
        for (int x = 0; x < items.size(); x++) {
            if (items.get(x).getComercio() == null) {
                items.remove(x);
            }
        }
        return items;
    }

    /* Procedimientos a realizar con la cadena:
     * 1.- Buscar el TAG Emv.
     * 2.- Buscar la longitud del contenido del TAG (Convertir Hexadecimal a Decimal)
     * 3.- Cortar la cadena hasta la longitud indicada
     * 4.- Volver a ejecutar el ciclo. */
    public static final String translateTlv(String tlv) {
        String[] b = new String[tlv.length() / 2];
        String translate = new String();
        boolean searchTag = true, tagHas2Bytes = false, searchLength = false, searchContent = false;
        int lengthBytesContent = 0, bytesRead = 1;
        String hexToBin, hexToDecimal, TAG = "", LENGTH = "", CONTENT = "";
        for (int i = 0; i < b.length; i++) {
            /* Obtención de bytes hexadecimales del String */
            int index = i * 2;
            String v = tlv.substring(index, index + 2);
            b[i] = v;
            /* Conversión hexadecimal a binario */
            hexToBin = new BigInteger(b[i], 16).toString(2);
            /* Buscamos el TAG Emv */
            if (searchTag) {
                LENGTH = "";
                CONTENT = "";
                /* Si el tag contiene un byte hexadecimal entonces se le asigna a la variable TAG,
                 * en caso de que contenga 2 bytes se deberá concatenar el segundo byte al TAG y se
                 * procede a buscar la longitud del contenido del tag */
                if (tagHas2Bytes) {
                    TAG += b[i];
                    translate += b[i];
                    tagHas2Bytes = false;
                    searchTag = false;
                    searchLength = true;
                } else {
                    TAG = b[i];
                    translate += TAG;
                    /* Si los ultimos 5 bits del hexadecimal convertido en binario están prendidos entonces
                     * significa que el tag contiene 2 bytes hexadecimales */
                    if (hexToBin.length() > 4 && hexToBin.substring(hexToBin.length() - 5, hexToBin.length()).equals("11111")) {
                        tagHas2Bytes = true;
                    } else {
                        tagHas2Bytes = false;
                        searchTag = false;
                        searchLength = true;
                    }
                }
                //Log.i("EMV", "TAG: " + TAG);
                /* Una vez encontrado el TAG procedemos a buscar la longitud del contenido del tag */
            } else if (searchLength) {
                /* Si el TAG es igual a 91 entonces necesitamos convertir el decimal que llega de
                 * servicio a hexadecimal como parte de la longitud del contenido del tag */
                if (TAG.equals("91")) {
                    LENGTH = Integer.toHexString(Integer.parseInt(b[i])).toUpperCase();
                    /* Si el hexadecimal contiene solo una letra o caracter, se le debe agregar un
                     * 0 antes para que la longitud sea válida para el chip */
                    if (LENGTH.length() == 1) {
                        LENGTH = "0" + LENGTH;
                    }
                    translate += LENGTH;
                    /* Se guarda el tamaño convertido a decimal ya que se empleará para realizar el
                     * corte de la cadena */
                    lengthBytesContent = Integer.parseInt(b[i]);
                } else {
                    LENGTH = b[i];
                    translate += b[i];
                    lengthBytesContent = Integer.parseInt(LENGTH, 16);
                }
                searchLength = false;
                searchContent = true;
                //Log.i("EMV", "LENGTH: " + LENGTH);
                /* Una vez que ya se enontró la longitud, se debe empezar a buscar el contenido del
                 * dependiendo la longitud indicada, así como la limpieza para el TAG 91 */
            } else if (searchContent) {
                /* Si el TAG es 91 y los bytes hexadecimales leídos son iguales al máximo de bytes
                 * que le corresponde al TAG (16 bytes max) entonces se procede a buscar el siguiente
                 * tag de la cadena original y se reinician las banderas para volver al primer paso
                 * del ciclo */
                if (TAG.equals("91") && bytesRead == 16) {
                    TAG = "";
                    bytesRead = 1;
                    lengthBytesContent = 0;
                    searchContent = false;
                    searchTag = true;
                    /* Si el numero de bytes leidos aún son menores a la longitud solicitada o el TAG
                     * es 91 y bytes leidos son iguales a la longitud maxima del tag (16 bytes), se
                     * procede a guardar el byte e incrementar el contador de bytes leidos */
                } else if (bytesRead < lengthBytesContent || (TAG.equals("91") && bytesRead == lengthBytesContent)) {
                    CONTENT += b[i];
                    translate += b[i];
                    bytesRead++;
                    /* IMPORTANTE:  Si el Tag es 91 y los bytes leídos se encuentran en el intervalo
                     * longitudIndicada < bytesLeidos > longitudMaximaDelTag (16), entonces
                     * solamente se incrementa el contador de bytes y no se guarda nada, debido a que
                     * lo demás que se encuentra como contenido se considera basura */
                } else if (TAG.equals("91") && bytesRead < 16 && bytesRead > lengthBytesContent) {
                    bytesRead++;
                    /* En caso de que no se cumplan las validaciones anteriores, significa que el TAG
                     * es diferente al 91 y que ya se encuentra leyendo el último byte por la longitud
                     * indicada.  Se guarda ese byte y se reinician las banderas para que continue
                     * leyendo TAG. */
                } else {
                    CONTENT += b[i];
                    translate += b[i];
                    bytesRead = 1;
                    lengthBytesContent = 0;
                    searchContent = false;
                    searchTag = true;
                }
                //Log.i("EMV", "CONTENT: " + CONTENT);
            }
        }
        Log.e("EMV", "ARPC ORIGINAL: " + tlv);
        Log.e("EMV", "ARPC TRADUCIDO: " + translate);
        return translate;
    }
}