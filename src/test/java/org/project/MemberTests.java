package org.project;

import org.junit.jupiter.api.Test;
import org.project.controller.MemberController;
import org.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(MemberController.class)
public class MemberTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void 회원가입성공() throws Exception {
        String jsonContent = "{\"email\":\"test@naver.com\",\"phone\":\"010-0000-0000\",\"username\":\"테스트유저\",\"password\":\"Test12345@@\"}";

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
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 상태 코드 400으로 예상
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("이메일은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("휴대폰은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("이름은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("비밀번호은 필수 입력 항목입니다.")))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("대소문자, 숫자 5개 이상, 특수문자 포함 2개 이상 검즘")));
    }

    @Test
    public void 회원가입실패_이메일() throws Exception {
        String jsonContent = "{\"email\":\"testnaver.com\",\"phone\":\"010-0000-0000\",\"username\":\"테스트유저\",\"password\":\"Test12345@@\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("유효한 이메일 주소를 입력해주세요.")));
    }

    @Test
    public void 회원가입실패_전화번호() throws Exception {
        String jsonContent = "{\"email\":\"test@naver.com\",\"phone\":\"0100000-0000\",\"username\":\"테스트유저\",\"password\":\"Test12345@@\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("휴대폰 번호 형식이 올바르지 않습니다.")));
    }

    @Test
    public void 회원가입실패_작성자() throws Exception {
        String jsonContent = "{\"email\":\"test@naver.com\",\"phone\":\"010-0000-0000\",\"username\":\"테저\",\"password\":\"Test12345@@\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("이름은 대소문자 및 한글만 포함이 가능합니다.")));
    }

    @Test
    public void 회원가입실패_비밀번호() throws Exception {
        String jsonContent = "{\"email\":\"test@naver.com\",\"phone\":\"010-0000-0000\",\"username\":\"테스트유저\",\"password\":\"Test123@@\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("대소문자, 숫자 5개 이상, 특수문자 포함 2개 이상 검즘")));
    }

}
