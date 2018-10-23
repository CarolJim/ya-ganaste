package com.pagatodo.yaganaste.ui._controllers;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.utils.camera.CameraManager;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.File;
import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.utils.camera.CameraManager.CROP_RESULT;


public class CropActivity extends SupportFragmentActivity {

    private static final String EXTRA_URI = "PICTURE";
    private static final String EXTRA_CAMERA = "CAM";

    @BindView(R.id.crop_view)
    CropIwaView cropIwaView;
    @BindView(R.id.toolbar_crop)
    Toolbar toolbar;
    Uri imageUri;
    private ICropper iCropper;
    private CropIwaSaveConfig saveConfig;
    private Context context;

    public static Intent callingIntent(Context context, Uri imageUri) {

        Intent intent = new Intent(context, CropActivity.class);
        intent.putExtra(EXTRA_URI, imageUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        //toolbar.setTitle(R.string.title_crop_gallery);
        setSupportActionBar(toolbar);
        imageUri = getIntent().getParcelableExtra(EXTRA_URI);
        cropIwaView.setImageUri(imageUri);

    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    public static Uri createNewEmptyFile() {
        return Uri.fromFile(new File(
                App.getInstance().getFilesDir(),
                System.currentTimeMillis() + ".png"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done) {
            this.saveConfig = new CropIwaSaveConfig(createNewEmptyFile());
            cropIwaView.crop(saveConfig);
            /*File file = new File(imageUri.getPath());
            if(file.exists()){
                if (file.delete()){
                    Log.d("FILE IMAGE", "BORRADO");
                }
            }*/
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        setResult(CROP_RESULT);
        finish();
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        setResult(CROP_RESULT);
        super.onBackPressed();

    }


}
