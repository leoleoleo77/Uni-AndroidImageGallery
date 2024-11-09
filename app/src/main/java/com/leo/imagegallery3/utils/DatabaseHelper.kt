package com.leo.imagegallery3.utils
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.leo.imagegallery3.R

class GalleryDatabase(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    private val _context = context;

    companion object {
        private const val DATABASE_NAME = "gallery.db.p20155"
        private const val DATABASE_VERSION = 1
        const val PAINTINGS_TABLE_NAME = "images"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_RESOURCE_ID = "image_path"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the images table
        db.execSQL(
            "CREATE TABLE $PAINTINGS_TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_RESOURCE_ID INTEGER, " +
                    "$COLUMN_DESCRIPTION TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // If the database version changes, drop the old table and create a new one
        db.execSQL("DROP TABLE IF EXISTS $PAINTINGS_TABLE_NAME")
        onCreate(db)
    }

    private fun insertImage(
        title: String,
        imageResourceId: Int,
        description: String
    ): Long {

        val db = try {
            this.writableDatabase
        } catch (e: SQLiteException) {
            e.printStackTrace()
            null
        }

        if (db == null) {
            // Handle null database case
            return -1
        }

        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_RESOURCE_ID, imageResourceId)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.insert(PAINTINGS_TABLE_NAME, null, values)
    }

    fun getAllPaintings(): List<Painting> {
        val paintingList = mutableListOf<Painting>()

        val db = try {
            this.readableDatabase
        } catch (e: SQLiteException) {
            e.printStackTrace()
            null
        }

        if (db == null) {
            // Handle null database case
            return emptyList()
        }

        val cursor: Cursor = try {
            // SELECT * FROM IMAGES
            db.query(PAINTINGS_TABLE_NAME, null, null, null, null, null, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            null
        } ?: return paintingList

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))
                val imageResourceId = getInt(getColumnIndexOrThrow(COLUMN_RESOURCE_ID))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                paintingList.add(Painting(id, title, imageResourceId, description))
            }
            close()
        }
        return paintingList
    }

    fun populate(): GalleryDatabase {
        if (getAllPaintings().isEmpty()) {
            insertImage(
                title = _context.getString(R.string.morning_dragon),
                imageResourceId = R.drawable.butterfly,
                description = _context.getString(R.string.morning_dragon_label),
            )
            insertImage(
                title = _context.getString(R.string.tales_from_oceans),
                imageResourceId = R.drawable.fishes,
                description = _context.getString(R.string.tales_from_oceans_label),
            )
            insertImage(
                title = _context.getString(R.string.shadowland),
                imageResourceId = R.drawable.morning,
                description = _context.getString(R.string.shadowland_label),
            )
            insertImage(
                title = _context.getString(R.string.yessongs_arrival),
                imageResourceId = R.drawable.mushroom,
                description = _context.getString(R.string.yessongs_arrival_label),
            )
            insertImage(
                title = _context.getString(R.string.yessongs_escape),
                imageResourceId = R.drawable.space,
                description = _context.getString(R.string.yessongs_escape_label),
            )
            insertImage(
                title = _context.getString(R.string.badger),
                imageResourceId = R.drawable.snow,
                description = _context.getString(R.string.badger_label),
            )
            insertImage(
                title = _context.getString(R.string.offshoot),
                imageResourceId = R.drawable.tree,
                description = _context.getString(R.string.offshoot_label),
            )
        }
        return this
    }

    fun deleteAllData(): GalleryDatabase {
        val db = try {
            this.writableDatabase
        } catch (e: SQLiteException) {
            e.printStackTrace()
            null
        }

        val deleteQuery = "DELETE FROM $PAINTINGS_TABLE_NAME"
        db?.execSQL(deleteQuery)
        return this
    }
}

data class Painting(
    val id: Int,
    val title: String,
    val imageResourceId: Int,
    val description: String
)
