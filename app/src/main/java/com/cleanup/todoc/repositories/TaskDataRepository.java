package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.mTaskDao = taskDao;
    }

    public Project[] getProjects(){
        return Project.getAllProjects();
    }

    public LiveData<List<Task>> getTasks(){
        return this.mTaskDao.getTasks();
    }

    public void createTask(Task task){ mTaskDao.insertTask(task); }

    public void deleteTask(Task task){
        mTaskDao.deleteTask(task);
    }
}
