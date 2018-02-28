package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Service
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDtoExpected = new TaskDto((long) 1, "task_Dto", "description_Dto");
        //When
        Task acctualTask = taskMapper.mapToTask(taskDtoExpected);
        //Then
        assertThat(taskDtoExpected.getContent(), is(acctualTask.getContent()));
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task taskExpected = new Task((long) 1, "task_test", "testing_task");
        //When
        TaskDto acctualTaskDto = taskMapper.mapToTaskDto(taskExpected);
        //Then
        assertThat(taskExpected.getContent(), is(acctualTaskDto.getContent()));
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        Task taskExpected = new Task((long) 1, "task_test", "testing_task");
        List<Task> taskList = Arrays.asList(taskExpected);
        //When
        List<TaskDto> acctualListTaskDto = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertThat(taskList.get(0).getContent(), is(acctualListTaskDto.get(0).getContent()));
    }
}
