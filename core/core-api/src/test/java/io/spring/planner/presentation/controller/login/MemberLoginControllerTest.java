package io.spring.planner.presentation.controller.login;

import io.spring.planner.authorization.AuthorizationManager;
import io.spring.planner.authorization.AuthorizationPayload;
import io.spring.planner.authorization.AuthorizationToken;
import io.spring.planner.domain.common.exception.EmailPatternInvalidException;
import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.exception.MemberPasswordPatternInvalidException;
import io.spring.planner.presentation.controller.AbstractRestControllerMockMvc;
import io.spring.planner.usecase.member.login.MemberLoginResult;
import io.spring.planner.usecase.member.login.MemberLoginUseCase;
import io.spring.planner.usecase.member.registration.MemberRegistrationResult;
import io.spring.planner.usecase.member.registration.MemberRegistrationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class MemberLoginControllerTest extends AbstractRestControllerMockMvc {

    @MockBean
    private MemberRegistrationUseCase memberRegistrationUseCase;

    @MockBean
    private AuthorizationManager authorizationManager;

    @MockBean
    private MemberLoginUseCase memberLoginUseCase;

    @DisplayName("회원 가입 API 요청 성공 테스트")
    @Test
    public void 회원가입_API_요청_성공_테스트() throws Exception {

        //given
        String email = "tester@example.com";
        String nickname = "tester";
        long memberId = 1L;
        String password = "1234aA!1234";

        MemberRegistrationRequest request = new MemberRegistrationRequest(email, nickname, password);
        MemberRegistrationResult result = new MemberRegistrationResult(memberId, nickname, email);

        //when
        when(memberRegistrationUseCase.register(any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/member-login/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(jsonPath("$.id").value(memberId))
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.nickname").value(nickname))
        ;
    }

    @DisplayName("회원 가입 API 요청시 이메일 형식이 올바르지 않다면 예외 발생")
    @Test
    public void 회원가입_API_이메일_형식이_올바르지_않다면_예외_발생() throws Exception {

        //given
        String email = "tester";
        String nickname = "tester";
        String password = "1234aA!1234";

        MemberRegistrationRequest request = new MemberRegistrationRequest(email, nickname, password);
        ExceptionCode exceptionCode = ExceptionCode.EMAIL_PATTERN_INVALID;

        //when
        doThrow(new EmailPatternInvalidException(exceptionCode)).when(memberRegistrationUseCase)
            .register(any());

        //then
        mockMvc.perform(post("/api/v1/member-login/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(jsonPath("$.timestamp").isNotEmpty())
            .andExpect(jsonPath("$.message").value(exceptionCode.getMessage()))
            .andExpect(jsonPath("$.code").value(exceptionCode.name()))
        ;
    }

    @DisplayName("회원 가입 API 요청시 비밀번호 형식이 올바르지 않다면 예외 발생")
    @Test
    public void 회원가입_API_비밀번호_형식이_올바르지_않다면_예외_발생() throws Exception {

        //given
        String email = "tester";
        String nickname = "tester";
        String password = "234";

        MemberRegistrationRequest request = new MemberRegistrationRequest(email, nickname, password);
        ExceptionCode exceptionCode = ExceptionCode.MEMBER_PASSWORD_PATTERN_INVALID;

        //when
        doThrow(new MemberPasswordPatternInvalidException(exceptionCode)).when(memberRegistrationUseCase)
            .register(any());

        //then
        mockMvc.perform(post("/api/v1/member-login/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(jsonPath("$.timestamp").isNotEmpty())
            .andExpect(jsonPath("$.message").value(exceptionCode.getMessage()))
            .andExpect(jsonPath("$.code").value(exceptionCode.name()))
        ;
    }


    @DisplayName("로그인 성공 테스트")
    @Test
    public void 로그인_성공_테스트() throws Exception {

        //given
        long memberId = 1L;
        String email = "tester";
        String nickname = "tester";
        String password = "234";

        MemberLoginRequest request = new MemberLoginRequest(email, password);
        String accessToken = "ACCESSTOKEN";
        String refreshToken = "REFRESHTOKEN";

        //when
        when(memberLoginUseCase.login(any())).thenReturn(new MemberLoginResult(memberId, nickname, email));
        when(authorizationManager.generateAuthorization(any())).thenReturn(new AuthorizationToken(new AuthorizationPayload(memberId, email), accessToken, refreshToken));

        //then
        mockMvc.perform(post("/api/v1/member-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.accessToken").value(accessToken))
            .andExpect(jsonPath("$.refreshToken").value(refreshToken))
        ;
    }
}