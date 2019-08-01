package com.ovlesser.message.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ovlesser.message.LiveDataTestUtil;
import com.ovlesser.message.model.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.ovlesser.message.db.TestData.MESSAGE;
import static com.ovlesser.message.db.TestData.MESSAGES;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link MessageDao}
 */
@RunWith(AndroidJUnit4.class)
public class MessageDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private MessageDao messageDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        messageDao = mDatabase.messageDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getMessageWhenNoMessageInserted() throws InterruptedException {
        List<Message> messages = LiveDataTestUtil.getValue(messageDao.loadAllMessage("1111"));

        assertTrue(messages.isEmpty());
    }

    @Test
    public void getMessageAfterInserted() throws InterruptedException {
        messageDao.insertAll(MESSAGES);

        List<Message> messages = LiveDataTestUtil.getValue(messageDao.loadAllMessage("1111"));

        assertThat(messages.size(), is(MESSAGES.size()));
    }

    @Test
    public void getMessageById() throws InterruptedException {
        messageDao.insertAll(MESSAGES);

        List<Message> messages = LiveDataTestUtil.getValue(messageDao.loadAllMessage
                (MESSAGE.getText()));
    }

}
