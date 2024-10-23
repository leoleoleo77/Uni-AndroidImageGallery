import android.content.Context
import android.content.SharedPreferences
import com.leo.imagegallery3.R

class PaintingPreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("painting_prefs", Context.MODE_PRIVATE)

    // Function to save paintings data
    fun savePaintingsData(paintings: List<Painting>) {
        val editor = prefs.edit()
        for (i in paintings.indices) {
            editor.putInt("painting_image_$i", paintings[i].image)
            editor.putInt("painting_description_$i", paintings[i].description)
        }
        editor.apply()
    }

    // Function to retrieve paintings data
    fun getPaintingsData(): List<Painting> {
        val paintings = listOf(
            Painting(
                image = R.drawable.butterfly,
                description =  R.string.morning_dragon
            ),
            Painting(
                image = R.drawable.fishes,
                description =  R.string.tales_from_oceans
            ),
            Painting(
                image = R.drawable.morning,
                description =  R.string.shadowland
            ),
            Painting(
                image = R.drawable.mushroom,
                description =  R.string.yessongs_arrival
            ),
            Painting(
                image = R.drawable.space,
                description =  R.string.yessongs_escape
            ),
            Painting(
                image = R.drawable.snow,
                description =  R.string.badger
            ),
            Painting(
                image = R.drawable.tree,
                description =  R.string.offshoot
            ),
        )
        return paintings
    }
}

data class Painting(val image: Int, val description: Int)
