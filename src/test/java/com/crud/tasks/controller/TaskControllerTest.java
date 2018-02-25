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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
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
    public void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> listTaskDto = new ArrayList<>();
        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(listTaskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) //or isOK()
                .andExpect(jsonPath("$", hasSize(0)));
    }

//    @Test
//    public void shouldFetchTask() throws Exception {
//        //Given
//        TaskDto taskDto = new TaskDto();
//        when(taskMapper.mapToTaskDto(dbService.getTask(ArgumentMatchers.anyLong()).orElseThrow(TaskNotFoundException::new))).thenReturn(taskDto);
//        //When & Then
//
//        mockMvc.perform(get("/v1/task/getTask?taskId=0").contentType(MediaType.APPLICATION_JSON).param("taskId", "0"))
//                .andExpect(status().is(200)) //or isOK()
//                .andExpect(jsonPath("$", hasSize(0)));
//    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=0").contentType(MediaType.APPLICATION_JSON).param("taskId", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)); //or isOK()
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Integer x = 1;
        Long taskId = x.longValue();
        TaskDto taskDto = new TaskDto(taskId, "test_task", "testing");
        Task task = new Task(taskId, "test_task", "testing");
        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200)) //or isOK()
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_task")))
                .andExpect(jsonPath("$.content", is("testing")));
    }

//    @Test
//    public void shouldCreateTask() throws Exception {
//        //Given
//        Integer x = 1;
//        Long taskId = x.longValue();
//        TaskDto taskDto = new TaskDto(taskId, "test_taskDto", "testing_Dto");
//        Task task = new Task(taskId, "test_task", "testing");
//        when(dbService.saveTask(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class)))).thenReturn(task);
//
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(taskDto);
//
//        //When & Then
//        this.mockMvc.perform(post("/v1/task/createTask")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(status().is(200)) //or isOK()
//                .andExpect(jsonPath("$.id").doesNotExist())
//                .andExpect(jsonPath("$.title", is("test_task")))
//                .andExpect(jsonPath("$.content", is("testing")));
//    }
}
