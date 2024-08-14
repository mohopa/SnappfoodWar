package ir.example.data.repository.character.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CharactersResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<CharacterDTO?>?
)