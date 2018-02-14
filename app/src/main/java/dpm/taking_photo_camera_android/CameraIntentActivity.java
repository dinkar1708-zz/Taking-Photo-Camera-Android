package dpm.taking_photo_camera_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;

public class CameraIntentActivity extends AppCompatActivity {
    private static final String TAG = CameraIntentActivity.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_OPEN_CAMERA = 10;
    private static final int REQ_RESULT_CODE_CAMERA = 2;

    private Uri fileUri;
    private ImageView imageView;

    private TextView imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);
        imagePath = findViewById(R.id.path);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CameraIntentActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CameraIntentActivity.this,
                            Manifest.permission.READ_CONTACTS)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(CameraIntentActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_OPEN_CAMERA);

                        // MY_PERMISSIONS_REQUEST_OPEN_CAMERA is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    openCameraTakePicture();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_OPEN_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openCameraTakePicture();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult  requestCode" + requestCode + " resultCode " + resultCode + " data " + data);
        try {

            if (resultCode != RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Canceled",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Log.i(TAG, "FILE URI>>>>>>>> " + fileUri + " get data ");
            switch (requestCode) {
                case REQ_RESULT_CODE_CAMERA:

                    Glide.with(CameraIntentActivity.this).load(fileUri)
                            .thumbnail(1f)
                            .into(imageView);
                    imagePath.setText(fileUri.toString());

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCameraTakePicture() {

        File photo = Utils
                .getTempFilePath(this);
        Log.i(TAG, " openCameraTakePicture photo " + photo);
        fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".my.package.name.provider", photo);

        Log.i(TAG, " openCameraTakePicture fileUri " + fileUri);
        Intent iSelectCamera = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        iSelectCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                fileUri);
        startActivityForResult(iSelectCamera,
                REQ_RESULT_CODE_CAMERA);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_intent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
