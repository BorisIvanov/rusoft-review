package rusoft.review.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import rusoft.review.dto.ClientAddRequest;
import rusoft.review.dto.ClientRemoveRequest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientControllerTest {

    private final static String NAME = "name";
    private final static String MODEL = "model";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void add() throws Exception {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        mvc.perform(MockMvcRequestBuilders
                .post("/add")
                .content(objectMapper.writeValueAsBytes(clientAddRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void remove() throws Exception {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        mvc.perform(MockMvcRequestBuilders
                .post("/add")
                .content(objectMapper.writeValueAsBytes(clientAddRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ClientRemoveRequest clientRemoveRequest = new ClientRemoveRequest();
        clientRemoveRequest.setName(NAME);
        clientRemoveRequest.setModel(MODEL);
        mvc.perform(MockMvcRequestBuilders
                .post("/remove")
                .content(objectMapper.writeValueAsBytes(clientRemoveRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void addFail() throws Exception {
        ClientAddRequest clientAddRequest = new ClientAddRequest();
        clientAddRequest.setProdDate(LocalDate.now());
        clientAddRequest.setBirthDate(LocalDate.now());
        clientAddRequest.setName(NAME);
        clientAddRequest.setModel(MODEL);
        mvc.perform(MockMvcRequestBuilders
                .post("/add")
                .content(objectMapper.writeValueAsBytes(clientAddRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/add")
                .content(objectMapper.writeValueAsBytes(clientAddRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        /* Ответ сервера в случае ошибки - 409 c причиной */
        System.out.println(result.getResponse().getErrorMessage());
    }

}
