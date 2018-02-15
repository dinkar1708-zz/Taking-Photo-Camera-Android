package kotlincamera.demo

import android.support.v4.content.FileProvider

// https://developer.android.com/training/camera/photobasics.html
// We are using getUriForFile(Context,String,File)which returns a content:// URI. For more recent apps targeting
// Android 7.0 (API level 24) and higher, passing a file:// URI across a package boundary causes a FileUriExposedException.
// Therefore, we now present a more generic way of storing images using a FileProvider.

class GenericFileProvider : FileProvider()
