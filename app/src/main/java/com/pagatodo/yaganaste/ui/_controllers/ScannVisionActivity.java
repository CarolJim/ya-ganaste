package com.pagatodo.yaganaste.ui._controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.utils.barcode.BarcodeTracker;
import com.pagatodo.yaganaste.utils.barcode.BarcodeTrackerFactory;
import com.pagatodo.yaganaste.utils.camera.CameraSource;
import com.pagatodo.yaganaste.utils.camera.CameraSourcePreview;

import java.io.IOException;
import java.util.Objects;

import static com.pagatodo.yaganaste.modules.register.RegActivity.RESULT_CODE_KEYBOARD;



public class ScannVisionActivity extends AppCompatActivity implements View.OnClickListener,
        BarcodeTracker.BarcodeGraphicTrackerCallback {
    // Constants used to pass extra data in the intent
    public static final String BarcodeObject = "Barcode";
    public static final String QRObject = "Qrcode";
    // Intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // Permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static final String TAG = ScannVisionActivity.class.getSimpleName();

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private boolean isQrcode = false;
    private boolean isLight;
    private String title;

    static public Intent createIntent(Activity activity,boolean isActive, String msj) {
        Intent intent = new Intent(activity, ScannVisionActivity.class);
        intent.putExtra("KEYBOARD_BTN", isActive);
        intent.putExtra("TITLE", msj);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_barcode_scan);
        (findViewById(R.id.button_capture)).setOnClickListener(this);
        (findViewById(R.id.button_keyboard)).setOnClickListener(this);
        (findViewById(R.id.btn_back)).setOnClickListener(this);
        if (getIntent().getExtras() != null) {
            isQrcode = getIntent().getExtras().getBoolean(QRObject);
            if (!getIntent().getExtras().getBoolean("KEYBOARD_BTN")){
                (findViewById(R.id.button_keyboard)).setVisibility(View.VISIBLE);
            }
            if (getIntent().getExtras().getString("TITLE") != null){
                title = getIntent().getExtras().getString("TITLE");
                ((TextView) findViewById(R.id.title_scan)).setText(title);
            }

        }
        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }
        this.isLight = false;


    }

    @Override
    public void onDetectedQrCode(Barcode barcode) {
        // Read only numeric barcode
        if (!isQrcode && barcode != null && barcode.format != Barcode.QR_CODE) {
            Intent intent = new Intent();
            intent.putExtra(BarcodeObject, barcode);
            setResult(CommonStatusCodes.SUCCESS, intent);
            finish();
            // Read only QR code
        } else
            if (isQrcode && barcode != null && barcode.format == Barcode.QR_CODE) {
            Intent intent = new Intent();
            intent.putExtra(BarcodeObject, barcode);
            setResult(CommonStatusCodes.SUCCESS, intent);
            finish();
        }
    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
        }
    }

    /**
     * Creates and starts the camera.
     * <p>
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource() {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(this);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error,
                        Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    true ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder
                .setFlashMode(false ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }


    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /*@Override
    public boolean requiresTimer() {
        return true;
    }
*/
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = (dialog, id) -> finish();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_capture:
                this.isLight = mCameraSource.turnOnAndOffLight(this.isLight);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.button_keyboard:
                setResult(RESULT_CODE_KEYBOARD);
                finish();
                break;
        }
    }
}
