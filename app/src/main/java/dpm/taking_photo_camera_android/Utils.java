package dpm.taking_photo_camera_android;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Utils {

	private static final String TMP_FILE_NAME = "ImageFile";
	private static String fileFormate = ".JPEG";

	public static File getTempFilePath(Context context) {
		File file = null;
		file = new File(getExternalFolder(context), TMP_FILE_NAME + fileFormate);
		if (file.exists()) {
			file.delete();
		}
		file = new File(getExternalFolder(context), TMP_FILE_NAME + fileFormate);
		return file;
	}

	public static File getExternalFolder(Context context) {
		try {
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				File file = new File(Environment.getExternalStorageDirectory(),
						context.getString(R.string.app_name));
				file.mkdir();
				return file;
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				Toast.makeText(context, "Can not write on external storage.",
						Toast.LENGTH_LONG).show();
				return null;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
