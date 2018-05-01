package com.sunshuai.photopickerdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(MainActivity.this)
                        .showCamera(true)
                        .single()
                        .start(MainActivity.this, 1);
            }
        });

        imageView1 = findViewById(R.id.iv1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

            Log.e(TAG, path.get(0)); //path       /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1524987651048.jpg
            Uri uri = Uri.fromFile(new File(path.get(0)));
            Log.e(TAG, String.valueOf(uri)); //Uri       file:///storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1524987651048.jpg
            Log.e(TAG, String.valueOf(Uri.parse(path.get(0))));  //Uri       /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1524987651048.jpg
            Log.e(TAG, uri.getPath()); //path      /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1524987651048.jpg



            UCrop.Options options = new UCrop.Options();
            options.setHideBottomControls(true);
            options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
            UCrop.of(Uri.fromFile(new File(path.get(0))), Uri.fromFile(new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg")))
                    .withAspectRatio(1, 1)
                    .withOptions(options)
                    .start(this);

        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Glide.with(this).load(resultUri).into(imageView1);
        }
    }
}


