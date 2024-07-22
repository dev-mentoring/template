package org.project;

import org.junit.jupiter.api.Test;
import org.project.controller.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@WebMvcTest(MemberController.class)
public class MemberTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void 회원가입성공() throws Exception {
        String jsonContent = "{\"email\":\"test@naver.com\",\"phone\":\"010-0000-0000\",\"username\":\"테스트유저\",\"password\":\"Test5959@\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 회원가입실패_공백() throws Exception {
        String invalidJson = "{ \"email\": \"\", \"phone\": \"\", \"username\": \"\", \"password\": \"\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Validation failed:")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("이메일은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("휴대폰은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("이름은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("비밀번호은 필수 입력 항목입니다.")));
    }

}
