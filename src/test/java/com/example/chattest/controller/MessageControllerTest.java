package com.example.chattest.controller;

import com.example.chattest.clock.Clock;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class MessageControllerTest {
    private static final LocalDateTime currentDate = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Autowired
    private Clock clock;

    @Test
    @DatabaseSetup(value = "classpath:message/create/create-before.xml")
    @ExpectedDatabase(value = "classpath:message/create/create-after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCreateMessage() throws Exception {
        File requestFile = ResourceUtils.getFile("classpath:message/create/create-request.json");
        String requestJson = new String(Files.readAllBytes(requestFile.toPath()));

        File responseFile = ResourceUtils.getFile("classpath:message/create/create-response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));

        mockMvc.perform(post("/api/v1/message")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));;
    }

    @Test
    @DatabaseSetup(value = "classpath:message/create-with-fetch/create-before.xml")
    @ExpectedDatabase(value = "classpath:message/create-with-fetch/create-after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testCreateMessageWithMessageFetchingFromAnotherUser() throws Exception {
        File requestFile = ResourceUtils.getFile("classpath:message/create-with-fetch/create-request.json");
        String requestJson = new String(Files.readAllBytes(requestFile.toPath()));

        File responseFile = ResourceUtils.getFile("classpath:message/create-with-fetch/create-response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));

        Mockito.when(clock.now()).thenReturn(currentDate);
        mockMvc.perform(post("/api/v1/message")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));;
    }

    @Test
    @DatabaseSetup(value = "classpath:message/create-without-fetch/create-before.xml")
    @ExpectedDatabase(value = "classpath:message/create-without-fetch/create-after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testCreateMessageWithoutFetchingFromAnotherUser() throws Exception {
        File requestFile = ResourceUtils.getFile("classpath:message/create-without-fetch/create-request.json");
        String requestJson = new String(Files.readAllBytes(requestFile.toPath()));

        File responseFile = ResourceUtils.getFile("classpath:message/create-without-fetch/create-response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));

        Mockito.when(clock.now()).thenReturn(currentDate);
        mockMvc.perform(post("/api/v1/message")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));;
    }

    @Test
    @DatabaseSetup(value = "classpath:message/fetch/fetch-before.xml")
    @ExpectedDatabase(value = "classpath:message/fetch/fetch-after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testFetch() throws Exception {
        File requestFile = ResourceUtils.getFile("classpath:message/fetch/fetch-request.json");
        String requestJson = new String(Files.readAllBytes(requestFile.toPath()));

        File responseFile = ResourceUtils.getFile("classpath:message/fetch/fetch-response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));

        Mockito.when(clock.now()).thenReturn(currentDate);
        mockMvc.perform(get("/api/v1/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
