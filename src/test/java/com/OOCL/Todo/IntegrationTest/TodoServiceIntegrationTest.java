package com.OOCL.Todo.IntegrationTest;

import com.OOCL.Todo.model.Todo;
import com.OOCL.Todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoServiceIntegrationTest {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void afterEach() {
        todoRepository.deleteAll();
    }

    @Test
    void should_return_todos_when_call_getAll_api() throws Exception {
        //given
        Todo todo1 = new Todo("todo1", "UNDONE");
        Todo todo2 = new Todo("todo2", "UNDONE");
        todoRepository.saveAll(asList(todo1, todo2));

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].text").value(todo1.getText()))
                .andExpect(jsonPath("$[0].status").value(todo1.getStatus()))
                .andExpect(jsonPath("$[1].text").value(todo2.getText()))
                .andExpect(jsonPath("$[1].status").value(todo2.getStatus()));
    }

    @Test
    void should_return_todo_when_call_insertTodo_api() throws Exception {
        //given
        String todo1Json = "{\n" +
                "    \"text\": \"sleep\",\n" +
                "    \"status\": \"UNDONE\"\n" +
                "}";

        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todo1Json))
                .andExpect(jsonPath("$.text").value("sleep"))
                .andExpect(jsonPath("$.status").value("UNDONE"))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void should_update_and_return_todo_when_call_updateTodo_api() throws Exception {
        //given
        Todo todo1 = new Todo("todo1", "UNDONE");
        todo1 = todoRepository.save(todo1);
        String todo1Json = "{\n" +
                "    \"id\": " + todo1.getId() + ",\n" +
                "    \"text\": \"sleep\",\n" +
                "    \"status\": \"UNDONE\"\n" +
                "}";

        mockMvc.perform(put("/todos/" + todo1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(todo1Json))
                .andExpect(jsonPath("$.text").value("sleep"))
                .andExpect(jsonPath("$.status").value("UNDONE"))
                .andExpect(jsonPath("$.id").value(todo1.getId()));
    }

    @Test
    void should_return_true_when_call_deleteTodo_api() throws Exception {
        //given
        Todo todo1 = new Todo("todo1", "UNDONE");
        todo1 = todoRepository.save(todo1);

        mockMvc.perform(delete("/todos/" + todo1.getId()))
                .andExpect(jsonPath("$").value(true));
    }
}
