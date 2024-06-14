package io.spring.planner.presentation.controller.login;

import io.spring.planner.authorization.AuthorizationManager;
import io.spring.planner.authorization.AuthorizationPayload;
import io.spring.planner.authorization.AuthorizationToken;
import io.spring.planner.authorization.jwt.exception.AuthorizationException;
import io.spring.planner.domain.member.exception.MemberDeletedException;
import io.spring.planner.presentation.config.exception.AuthorizationExceptionCode;
import io.spring.planner.presentation.config.exception.TokenAuthorizationException;
import io.spring.planner.usecase.member.find.MemberFindResult;
import io.spring.planner.usecase.member.find.MemberFindUseCase;
import io.spring.planner.usecase.member.login.MemberLoginCommand;
import io.spring.planner.usecase.member.login.MemberLoginResult;
import io.spring.planner.usecase.member.login.MemberLoginUseCase;
import io.spring.planner.usecase.member.registration.MemberRegistrationCommand;
import io.spring.planner.usecase.member.registration.MemberRegistrationResult;
import io.spring.planner.usecase.member.registration.MemberRegistrationUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member-login")
@RestController
public class MemberLoginController {

    private final MemberRegistrationUseCase memberRegistrationUseCase;
    private final MemberFindUseCase memberFindUseCase;
    private final MemberLoginUseCase memberLoginUseCase;
    private final AuthorizationManager authorizationManager;

    @PostMapping(value = "/registration")
    public ResponseEntity<MemberRegistrationResponse> registerMember(@RequestBody @Valid MemberRegistrationRequest request) {

        MemberRegistrationCommand command = new MemberRegistrationCommand(
            request.nickname(),
            request.email(),
            request.password()
        );
        MemberRegistrationResult result = memberRegistrationUseCase.register(command);
        MemberRegistrationResponse response = MemberRegistrationResponse.convert(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping
    public ResponseEntity<MemberLoginResponse> loginMember(@RequestBody @Valid MemberLoginRequest request) {
        MemberLoginResult loginResult = login(request);
        AuthorizationToken authorizationToken = generateAuthorizationToken(loginResult);

        MemberLoginResponse response = new MemberLoginResponse(authorizationToken.accessToken(), authorizationToken.refreshToken());

        return ResponseEntity.ok(response);
    }

    @Hidden
    @PostMapping("/refresh")
    public ResponseEntity<MemberLoginRefreshResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        try {
            AuthorizationToken authorizationToken = authorizationManager.validateRefreshToken(refreshToken);
            validateMember(authorizationToken);

            MemberLoginRefreshResponse response = new MemberLoginRefreshResponse(authorizationToken.accessToken(), authorizationToken.refreshToken());
            return ResponseEntity.ok(response);
        } catch (AuthorizationException e) {
            throw new TokenAuthorizationException(AuthorizationExceptionCode.INVALID_TOKEN);
        } catch (MemberDeletedException e) {
            throw e;
        }


    }

    private MemberFindResult validateMember(AuthorizationToken authorizationToken) {
        return memberFindUseCase.findById(authorizationToken.payload().id());
    }

    private AuthorizationToken generateAuthorizationToken(MemberLoginResult loginResult) {
        return authorizationManager.generateAuthorization(new AuthorizationPayload(loginResult.id(), loginResult.email()));
    }

    private MemberLoginResult login(MemberLoginRequest request) {
        return memberLoginUseCase.login(new MemberLoginCommand(request.email(), request.password()));
    }

}
