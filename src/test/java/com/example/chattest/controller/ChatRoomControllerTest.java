package com.example.chattest.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ChatRoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DatabaseSetup(value = "classpath:chat-room/join-room/join-before.xml")
    @ExpectedDatabase(value = "classpath:chat-room/join-room/join-after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testJoinRoom() throws Exception{
        File responseFile = ResourceUtils.getFile("classpath:chat-room/join-room/response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));

        mockMvc.perform(post("/api/v1/room")
                        .content(responseJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
