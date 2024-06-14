package io.spring.planner.presentation.controller.member;

import io.spring.planner.authorization.AuthorizationManager;
import io.spring.planner.authorization.AuthorizationPayload;
import io.spring.planner.authorization.AuthorizationToken;
import io.spring.planner.domain.member.MemberRegistrationState;
import io.spring.planner.presentation.controller.AbstractRestControllerMockMvc;
import io.spring.planner.presentation.controller.login.MemberRegistrationRequest;
import io.spring.planner.usecase.member.find.MemberFindResult;
import io.spring.planner.usecase.member.find.MemberFindUseCase;
import io.spring.planner.usecase.member.registration.MemberRegistrationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class MemberControllerTest extends AbstractRestControllerMockMvc {

    @MockBean
    private MemberFindUseCase memberFindUseCase;

    @Autowired
    private AuthorizationManager authorizationManager;

    @DisplayName("회원 가입 API 요청 성공 테스트")
    @Test
    public void 회원가입_API_요청_성공_테스트() throws Exception {

        //given
        String email = "tester@example.com";
        String nickname = "tester";
        long memberId = 1L;
        String password = "1234aA!1234";

        AuthorizationToken authorizationToken = authorizationManager.generateAuthorization(new AuthorizationPayload(memberId, email));
        MemberRegistrationRequest request = new MemberRegistrationRequest(email, nickname, password);

        //when
        MemberRegistrationState registrationState = MemberRegistrationState.REGISTRATION;
        when(memberFindUseCase.findById(any())).thenReturn(new MemberFindResult(memberId, nickname, email, registrationState));

        mockMvc.perform(get("/api/v1/my-members")
                .header("ACCESS_TOKEN", authorizationToken.accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id").value(memberId))
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.nickname").value(nickname))
            .andExpect(jsonPath("$.registrationState").value(registrationState.name()))
        ;
    }
}