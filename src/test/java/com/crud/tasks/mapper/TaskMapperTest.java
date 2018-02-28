package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Service
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDtoExpectedToMap = new TaskDto((long) 1, "task_Dto", "description_Dto");
        Task taskExpectedToGet = new Task((long) 1, "task_Dto", "description_Dto");
        //When
        Task acctualTask = taskMapper.mapToTask(taskDtoExpectedToMap);
        //Then
        assertEquals(taskExpectedToGet, acctualTask);
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task taskExpectedToMap = new Task((long) 1, "task_test", "testing_task");
        TaskDto taskDtoExpectedToGet = new TaskDto((long) 1, "task_test", "testing_task");
        //When
        TaskDto acctualTaskDto = taskMapper.mapToTaskDto(taskExpectedToMap);
        //Then
        assertEquals(taskDtoExpectedToGet, acctualTaskDto);
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        List<Task> taskList = Arrays.asList(new Task((long) 1, "task_test", "testing_task"));
        List<TaskDto> taskDtoListExpectedToGet = Arrays.asList(new TaskDto((long) 1, "task_test", "testing_task"));
        //When
        List<TaskDto> acctualListTaskDto = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(taskDtoListExpectedToGet, acctualListTaskDto);
    }
}
