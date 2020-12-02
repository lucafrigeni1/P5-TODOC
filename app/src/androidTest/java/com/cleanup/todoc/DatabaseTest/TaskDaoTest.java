package com.cleanup.todoc.DatabaseTest;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;

import com.cleanup.todoc.DatabaseTest.LiveDataTestUtils;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static com.cleanup.todoc.database.TodocDatabase.prepopulateDatabase;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    private static Task TASK_TEST = new Task(1L, "test", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .addCallback(prepopulateDatabase())
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void getTaskWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtils.getValue(this.database.taskDao().getTasks());
        assertEquals(0, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        this.database.taskDao().insertTask(TASK_TEST);
        List<Task> tasks = LiveDataTestUtils.getValue(this.database.taskDao().getTasks());
        assertEquals(1, tasks.size());
        this.database.taskDao().deleteTask(tasks.get(0));
        tasks = LiveDataTestUtils.getValue(this.database.taskDao().getTasks());
        assertEquals(0, tasks.size());
    }
}
