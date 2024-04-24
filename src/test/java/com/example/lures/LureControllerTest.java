package com.example.lures;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LureController.class)
class LureControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LureService lureService;

    @Test
    public void 存在するルアーデータを全件取得できること() throws Exception {

        List<Lure> expectedLure = List.of(
                new Lure(1, "Balaam300", "Madness", 300, 168),
                new Lure(2, "JointedCRAW178", "GANCRAFT", 178, 56));

        doReturn(expectedLure).when(lureService).findLure((String) null);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expectedLure);
        mockMvc.perform(get("/lures"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(lureService, times(1)).findLure((String) null);
    }

    @Test
    public void 引数に指定した文字列を含むルアーデータを取得できること() throws Exception {

        List<Lure> expectedLure = List.of(new Lure(2, "JointedCRAW178", "GANCRAFT", 178, 56));

        doReturn(expectedLure).when(lureService).findLure("join");

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expectedLure);
        mockMvc.perform(get("/lures?contains=join"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(lureService, times(1)).findLure("join");
    }

    @Test
    public void 既存のIDを指定した場合に該当のルアーデータを取得できること() throws Exception {

        Lure expectedLure = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(expectedLure).when(lureService).findLure(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(expectedLure);
        mockMvc.perform(get("/lures/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(lureService, times(1)).findLure(1);
    }

    @Test
    public void 新しいルアーデータを登録できること() throws Exception {

        Lure insertLure = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(insertLure).when(lureService).insert("Balaam300", "Madness", 300, 168);

        ObjectMapper objectMapper = new ObjectMapper();
        String lureJson = "{\"message\":\"created\"}";

        mockMvc.perform(post("/lures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insertLure)))
                .andExpect(status().isCreated())
                .andExpect(content().json(lureJson));
        verify(lureService, times(1)).insert("Balaam300", "Madness", 300, 168);
    }

    @Test
    public void ルアーデータを正常に更新できること() throws Exception {

        Lure updateMusic = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(updateMusic).when(lureService).update(1, "Balaam300", "Madness", 300, 168);

        ObjectMapper objectMapper = new ObjectMapper();
        String lureJson = "{\"message\":\"Lure updated\"}";

        mockMvc.perform(patch("/lures/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMusic)))
                .andExpect(status().isOk())
                .andExpect(content().json(lureJson));
        verify(lureService, times(1)).update(1, "Balaam300", "Madness", 300, 168);
    }

    @Test
    public void 指定したIDのルアーデータを削除できること() throws Exception {

        Lure deleteLure = new Lure(1, "Balaam300", "Madness", 300, 168);
        doReturn(deleteLure).when(lureService).delete(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String lureJson = "{\"message\":\"lure delete\"}";

        mockMvc.perform(delete("/lures/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteLure)))
                .andExpect(status().isOk())
                .andExpect(content().json(lureJson));
        verify(lureService, times(1)).delete(1);
    }
}
