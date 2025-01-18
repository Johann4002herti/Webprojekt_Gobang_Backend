package com.hszg.demo.data.api;

import com.hszg.demo.model.student.Student;
import com.hszg.demo.model.task.Task;


import java.util.Collection;
import java.util.List;

public interface TaskManager {

    List<Task> getAllTasks(String email);

    // all Todo
    void addTask(Task task, Student student);
    void deleteTask(String name, Student student);


}
