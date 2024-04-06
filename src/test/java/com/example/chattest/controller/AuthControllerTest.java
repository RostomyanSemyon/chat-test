package com.example.chattest.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
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
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DatabaseSetup(value = "classpath:auth/register/register-before.xml")
    @ExpectedDatabase(value = "classpath:auth/register/register-after.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRegister() throws Exception {
        File requestFile = ResourceUtils.getFile("classpath:auth/register/register-request.json");
        String requestJson = new String(Files.readAllBytes(requestFile.toPath()));

        File responseFile = ResourceUtils.getFile("classpath:auth/register/register-response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));


        mockMvc.perform(post("/api/v1/auth/register")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @Disabled //TODO how to pass through authenticationManager?
    @DatabaseSetup(value = "classpath:auth/authenticate/auth-before.xml")
    @ExpectedDatabase(value = "classpath:auth/authenticate/auth-before.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testAuthenticate() throws Exception {
        File requestFile = ResourceUtils.getFile("classpath:auth/authenticate/auth-request.json");
        String requestJson = new String(Files.readAllBytes(requestFile.toPath()));

        File responseFile = ResourceUtils.getFile("classpath:auth/authenticate/auth-response.json");
        String responseJson = new String(Files.readAllBytes(responseFile.toPath()));

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}

