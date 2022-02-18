package com.lib.net.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Describe:
 */
@JsonClass(generateAdapter = true)
@Entity
data class UserTestRoom(
    @Json(name = "image") var image: String,
    @Json(name = "firstName") var firstName: String,
    @Json(name = "lastName") var lastName: String,
    @Json(name = "age") var age: Int
) {
    @Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
