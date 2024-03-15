package uk.ac.aber.dcs.cs31620.healthypaws.model.util

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val HEALTHYPAWS_TAG = "HEALTHYPAWS"

object ResourceUtil {

    fun getPhotoFileUri(context: Context, fileName: String): File {
        val mediaStorageDir: File =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), HEALTHYPAWS_TAG)

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(HEALTHYPAWS_TAG, "failed to create directory")
        }

        return File(mediaStorageDir.path + File.separator + fileName)
    }

    fun createImageFile(context: Context): File {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }

}