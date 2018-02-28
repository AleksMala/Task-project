package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTasks() throws Exception {
        //Given
        List<TaskDto> emptyListTaskDto = new ArrayList<>();
        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(emptyListTaskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) //or isOK()
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> listOfTasks = Arrays.asList(new TaskDto((long) 1, "first_list", "testing_list"));
        List<Task> listOfTask = Arrays.asList(new Task((long) 1, "get_tasks", "test_task"));
        //When & Then
        when(dbService.getAllTasks()).thenReturn(listOfTask);
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(listOfTasks);
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("first_list")))
                .andExpect(jsonPath("$[0].content", is("testing_list")));
    }

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "test_taskDto", "testingDto_task");
        Task task = new Task((long) 1, "test_task", "testing_task");

        when(dbService.getTask(ArgumentMatchers.anyLong())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask").contentType(MediaType.APPLICATION_JSON).param("taskId", "0"))
                .andExpect(status().is(200)) //or isOK()
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_taskDto")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_taskDto", "testingDto_task");
        Task task = new Task(1L, "test_task", "testing_task");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        dbService.deleteTask(1L);

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask").contentType(MediaType.APPLICATION_JSON).param("taskId", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)); //or isOK()
        verify(dbService, times(1)).deleteTask(1L);
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_taskDto", "testing");
        Task task = new Task(1L, "test_task", "testing");

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(dbService.saveTask(ArgumentMatchers.any(Task.class)))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200)) //or isOK()
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_taskDto")))
                .andExpect(jsonPath("$.content", is("testing")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_taskDto", "testing_Dto");
        Task task = new Task(1L, "test_task", "testing");
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        dbService.saveTask(ArgumentMatchers.any(Task.class));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        this.mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200)); //or isOK()
        verify(dbService).saveTask(task);
    }
}
