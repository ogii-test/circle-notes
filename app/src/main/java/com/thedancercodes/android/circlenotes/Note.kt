package com.thedancercodes.android.circlenotes

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Note: All over while testing you'll be performing verifications that rely heavily on the
 * equals() method to perform comparisons, containsAll() being one of them.
 *
 * Much of the time in these cases you are looking for data equality (the properties of both objects
 * are exactly the same) rather than object equality (they reference the same object in memory).
 *
 * Because of this, you want to make sure your equals() performs the way you expect.
 *
 * In Kotlin, this is often as simple as making your class a data class.
 * This overrides equals() and hashcode() for you to compare the properties.
 *
 * Take caution for the times where this isn't enough! For example, Kotlin may not compare Lists
 * the way you expect.
 *
 * For that reason equals() and hashcode() are overridden for Notes in this app.
 */
@Entity
data class Note(val receiver: String,
                val notes: List<String>,
                @PrimaryKey(autoGenerate = true)
                    var id: Int = 0) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Note

    if (receiver != other.receiver) return false
    if (!notes.containsAll(other.notes)) return false
    if (!other.notes.containsAll(notes)) return false
    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    var result = receiver.hashCode()
    result = 31 * result + id
    return result
  }
}