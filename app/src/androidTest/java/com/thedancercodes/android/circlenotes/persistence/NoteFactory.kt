package com.thedancercodes.android.circlenotes.persistence

import com.thedancercodes.android.circlenotes.Note
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * One way to abstract this work and make your data random, is by using a Factory.
 *
 * A Factory object will create instances of your data class with random values for the properties.
 *
 * This will make your tests stronger and easier to write
 */
object NoteFactory {

    // Helper methods: In your Note you need two types of data: String and Int
    private fun makeRandomString() = UUID.randomUUID().toString()

    private fun makeRandomInt() =
            ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    // To create a note
    fun makeNote(): Note {
        return Note(
                makeRandomString(),
                listOf(makeRandomString(), makeRandomString()),
                makeRandomInt()
        )
    }

}