package ir.mhp.domain.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
data class People(
    val birthYear: String?,
    val created: Date?,
    val edited: Date?,
    val eyeColor: String?,
    val films: List<Int>?,
    val gender: String?,
    val hairColor: String?,
    val height: String?,
    val homeWorldId: Int?,
    val mass: String?,
    val name: String?,
    val skinColor: String?,
    val species: List<Int>?,
    val starships: List<String?>?,
    val id: Int?,
    val vehicles: List<String?>?
) : Parcelable