package com.example.zornosa_62_exer3

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zornosa_62_exer3.data.Event
import com.example.zornosa_62_exer3.data.EventDao
import com.example.zornosa_62_exer3.data.EventDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EventDaoTest {
    private lateinit var eventDao: EventDao
    private lateinit var db: EventDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EventDatabase::class.java).build()
        eventDao = db.eventDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertAndGetEvents() = runBlocking {
        val event = Event(title = "Test Event", description = "Test Description", date = "2023-10-27")
        eventDao.insertEvent(event)
        val allEvents = eventDao.getAllEvents().first()
        assertEquals(allEvents[0].title, event.title)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateCompletionStatus() = runBlocking {
        val event = Event(id = 1, title = "Test Event", description = "Test Description", date = "2023-10-27", isCompleted = false)
        eventDao.insertEvent(event)
        eventDao.updateCompletionStatus(1, true)
        val allEvents = eventDao.getAllEvents().first()
        assertTrue(allEvents[0].isCompleted)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteEvent() = runBlocking {
        val event = Event(id = 1, title = "Test Event", description = "Test Description", date = "2023-10-27")
        eventDao.insertEvent(event)
        eventDao.deleteEvent(event)
        val allEvents = eventDao.getAllEvents().first()
        assertTrue(allEvents.isEmpty())
    }
}
