package kotlincamera.demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_camera_intent.*
import kotlinx.android.synthetic.main.content_camera_intent.*

/**
 * : and () - invokes constructor invocation referes to extends
 */
class CameraIntentActivity : AppCompatActivity() {

    // this is the file URI where image is stored
    private var fileUri: Uri? = null

    //NOTE no need to define the class level variable and findviewbyid for initialization
    // image view
//    private var imageView: ImageView? = null

    private var imagePath: TextView? = null

    /**
     * fun is used for defining the function
     * ? refers - savedInstanceState can be null
     * savedInstanceState ->this is the variable name: Bundle -> is the data type it could be any thing like Int, Double etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_intent)
        //             android:id="@+id/toolbar" toolbar is directly
        setSupportActionBar(toolbar)
        //NOTE NO NEED TO DO THIS
//        imageView = findViewById(R.id.imageView)
        imagePath = findViewById(R.id.path)

        //NOTE - i can directly refer the id of XML file imageView
        imageView.setOnClickListener {
            //checking for write permission
            if (ContextCompat.checkSelfPermission(this@CameraIntentActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@CameraIntentActivity,
                        Manifest.permission.READ_CONTACTS)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this@CameraIntentActivity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_OPEN_CAMERA)

                    // MY_PERMISSIONS_REQUEST_OPEN_CAMERA is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                openCameraTakePicture()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        // works like switch statements
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_OPEN_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openCameraTakePicture()
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request.
    }

    /**
     * after taking picture using camera this method is invoked in activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.i(TAG, "onActivityResult  requestCode$requestCode resultCode $resultCode data $data")
        // NOTE -  above log statement can be printed as below print statement
        /** println works as system.out as defined in sdk
         * @kotlin.internal.InlineOnly
        public inline fun println(message: Any?) {
        System.out.println(message)
        }
         */
        println("onActivityResult  requestCode$requestCode resultCode $resultCode data $data");
        try {

            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(applicationContext, "Canceled",
                        Toast.LENGTH_SHORT).show()
                return
            }
            Log.i(TAG, "FILE URI>>>>>>>> $fileUri get data ")
            when (requestCode) {
                REQ_RESULT_CODE_CAMERA -> {
                    /*
                    setting image on image view using google glide library
                    this library is recommended by google
                    https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en
                    image can be set by decoding from file as well
                     */
                    Glide.with(this@CameraIntentActivity).load(fileUri)
                            .thumbnail(1f)
                            .into(imageView)

//                    https@ //kotlinlang.org/docs/reference/null-safety.html
                    /**
                     * he !! Operator
                    The third option is for NPE-lovers: the not-null assertion operator (!!) converts any value to a non-null type
                    and throws an exception if the value is null. We can write b!!,
                    and this will return a non-null value of b (e.g., a String in our example) or throw an NPE if b is null:

                    val l = b!!.length
                     */
                    imagePath!!.text = fileUri!!.toString()
                }
                else -> {
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * custom method to open camera using intent
     */
    private fun openCameraTakePicture() {

        //NOTE - i can directly access java code in kotline file
        // here Utils is java file and this activity is written in kotline language
        val photo = Utils
                .getTempFilePath(this)
        Log.i(TAG, " openCameraTakePicture photo " + photo)
        fileUri = FileProvider.getUriForFile(this, applicationContext.packageName + ".my.package.name.provider", photo)

        Log.i(TAG, " openCameraTakePicture fileUri " + fileUri!!)

        //NOTE
        /**
         * Intent iSelectCamera = new Intent(
        MediaStore.ACTION_IMAGE_CAPTURE); ->>>>>IN JAVA WE USE TO USE NEW KEY WORD BUT IN KITLIN NO NEED TO USE NEW KEYWORD FOR OBJECT
        AS SHOWN BELOW
         */
        val iSelectCamera = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE)
        iSelectCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                fileUri)
        startActivityForResult(iSelectCamera,
                REQ_RESULT_CODE_CAMERA)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_camera_intent, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    //note
    /**
     * https://kotlinlang.org/docs/reference/object-declarations.html
     * Companion Objects
    An object declaration inside a class can be marked with the companion keyword:

    class MyClass {
    companion object Factory {
    fun create(): MyClass = MyClass()
    }
    }
    Members of the companion object can be called by using simply the class name as the qualifier:
     */
    companion object {
        private val TAG = CameraIntentActivity::class.java.simpleName
        private val MY_PERMISSIONS_REQUEST_OPEN_CAMERA = 10
        private val REQ_RESULT_CODE_CAMERA = 2
    }
}
