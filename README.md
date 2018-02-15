# Taking-Photo-Camera-Android
Taking picture using intent
Take picture using android camera on android latest devices.
Using glide library to display image
Hanlding run time permissions

# Camerakotlin module -
# Code explanation-
```
/**
 * : and () - invokes constructor invocation referes to extends
 */
class CameraIntentActivity : AppCompatActivity() {
```

```
//NOTE no need to define the class level variable and findviewbyid for initialization
    // image view
//    private var imageView: ImageView? = null
```
```
/**
     * fun is used for defining the function
     * ? refers - savedInstanceState can be null
     * savedInstanceState ->this is the variable name: Bundle -> is the data type it could be any thing like Int, Double etc.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
    ```
```
//NOTE NO NEED TO DO THIS
//        imageView = findViewById(R.id.imageView)
```
```
//NOTE - i can directly refer the id of XML file imageView
        imageView.setOnClickListener {
            //checking for write permission
            if (ContextCompat.checkSelfPermission(this@CameraIntentActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
```

```
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
```
```
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
```

