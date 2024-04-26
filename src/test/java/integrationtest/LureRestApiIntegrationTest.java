package integrationtest;

import com.example.lures.Lure;
import com.example.lures.LuresApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = LuresApplication.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LureRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void すべてのルアーデータが取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lures"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                             {
                                 "id": 1,
                                 "product": "Balaam300",
                                 "company": "Madness",
                                 "size": 300.0,
                                 "weight": 168.0
                             },
                             {
                                 "id": 2,
                                 "product": "JointedCRAW178",
                                 "company": "GANCRAFT",
                                 "size": 178.0,
                                 "weight": 56.0
                             },
                             {
                                 "id": 3,
                                 "product": "SILENTKILLER175",
                                 "company": "deps",
                                 "size": 175.0,
                                 "weight": 70.0
                             },
                             {
                                 "id": 4,
                                 "product": "BULLSHOOTER160",
                                 "company": "deps",
                                 "size": 160.0,
                                 "weight": 98.0
                             }
                        ]
                        """));

    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 引数に指定した文字列を含むルアーデータが返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lures?contains=Join"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                             {
                                 "id": 2,
                                 "product": "JointedCRAW178",
                                 "company": "GANCRAFT",
                                 "size": 178.0,
                                 "weight": 56.0
                             }
                        ]
                        """));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 引数に指定した文字が空の場合に全てのルアーデータが返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lures?contains="))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                             {
                                 "id": 1,
                                 "product": "Balaam300",
                                 "company": "Madness",
                                 "size": 300.0,
                                 "weight": 168.0
                             },
                             {
                                 "id": 2,
                                 "product": "JointedCRAW178",
                                 "company": "GANCRAFT",
                                 "size": 178.0,
                                 "weight": 56.0
                             },
                             {
                                 "id": 3,
                                 "product": "SILENTKILLER175",
                                 "company": "deps",
                                 "size": 175.0,
                                 "weight": 70.0
                             },
                             {
                                 "id": 4,
                                 "product": "BULLSHOOTER160",
                                 "company": "deps",
                                 "size": 160.0,
                                 "weight": 98.0
                             }
                        ]
                        """));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 引数に指定した文字列から始まる製品名がない場合に空配列が返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lures?contains=p"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        []
                        """));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 存在するIDを指定した場合に該当するルアーデータが返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lures/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                             "id": 1,
                             "product": "Balaam300",
                             "company": "Madness",
                             "size": 300.0,
                             "weight": 168.0
                        }
                        """));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 存在しないIDを指定した場合に例外が返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lures/5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("lure not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/lures/5"));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @ExpectedDataSet(value = "datasets/insertLures.yml", ignoreCols = "id")
    @Transactional
    void 新しいルアーデータが登録できること() throws Exception {
        var lure = new Lure("Royal flash", "EVERGREEN", 160, 60);
        var objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/lures")
                        .content(objectMapper.writeValueAsString(lure))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                             "message": "created"
                        }
                        """));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 新しいルアーデータを登録する時に製品名が重複している場合は例外が返されること() throws Exception {
        var lure = new Lure("Balaam300", "Madness", 300.0, 168.0);
        var objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/lures")
                        .content(objectMapper.writeValueAsString(lure))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("409"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("There is duplicated data!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/lures"));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @ExpectedDataSet(value = "datasets/updatedLures.yml", ignoreCols = "id")
    @Transactional
    void 既存のルアーデータを更新できること() throws Exception {
        var lure = new Lure("SLIDESWIMMER175", "deps", 175.0, 72.8);
        var objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.patch("/lures/3")
                        .content(objectMapper.writeValueAsString(lure))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                              "message": "Lure updated"
                        }
                        """));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 既存のルアーデータを更新する時に指定したIDが存在しない場合に例外が返されること() throws Exception {
        var lure = new Lure("SLIDESWIMMER175", "deps", 175.0, 72.8);
        var objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.patch("/lures/5")
                        .content(objectMapper.writeValueAsString(lure))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Lure not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/lures/5"));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @Transactional
    void 既存のルアーデータを更新する時に製品名の重複がある場合に例外を返されること() throws Exception {
        var lure = new Lure("Balaam300", "Madness", 300.0, 168.0);
        var objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.patch("/lures/2")
                        .content(objectMapper.writeValueAsString(lure))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("409"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("There is duplicated data!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/lures/2"));
    }

    @Test
    @DataSet(value = "datasets/lures.yml")
    @ExpectedDataSet(value = "datasets/deletedLures.yml", ignoreCols = "id")
    @Transactional
    void ルアーデータを削除する場合() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/lures/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                              "message": "lure delete"
                        }
                        """));
    }
}
