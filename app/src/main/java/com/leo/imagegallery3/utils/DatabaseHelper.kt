package com.leo.imagegallery3.utils
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class GalleryDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "gallery.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_IMAGES = "images"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE_PATH = "image_path"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the images table
        db.execSQL(
            "CREATE TABLE $TABLE_IMAGES (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_IMAGE_PATH TEXT, " +
                    "$COLUMN_DESCRIPTION TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // If the database version changes, drop the old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        onCreate(db)
    }

    fun insertImage(title: String, imagePath: String, description: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_IMAGE_PATH, imagePath)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.insert(TABLE_IMAGES, null, values)
    }

    fun getAllImages(): List<Image> {
        val images = mutableListOf<Image>()
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_IMAGES,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE_PATH, COLUMN_DESCRIPTION),
            null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))
                val imagePath = getString(getColumnIndexOrThrow(COLUMN_IMAGE_PATH))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                images.add(Image(id, title, imagePath, description))
            }
            close()
        }
        return images
    }

    fun populateDatabaseIfEmpty() {
        if (getAllImages().isEmpty()) {
            insertImage("Mona Lisa", "path/to/mona_lisa.jpg", "A portrait by Leonardo da Vinci.")
            insertImage("Starry Night", "path/to/starry_night.jpg", "A painting by Vincent van Gogh.")
            // Add more images as needed
        }
    }
}

data class Image(
    val id: Int,
    val title: String,
    val imagePath: String,
    val description: String
)
