package io.spring.planner.presentation.controller.member;

import io.spring.planner.presentation.config.authorization.Authorization;
import io.spring.planner.presentation.config.authorization.AuthorizationInfo;
import io.spring.planner.usecase.member.delete.MemberDeleteUseCase;
import io.spring.planner.usecase.member.find.MemberFindResult;
import io.spring.planner.usecase.member.find.MemberFindUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/my-members")
@RestController
public class MemberController {

    private final MemberFindUseCase memberFindUseCase;
    private final MemberDeleteUseCase memberDeleteUseCase;


    @GetMapping
    public ResponseEntity<MemberFindResponse> findMember(@Parameter(hidden = true) @Authorization AuthorizationInfo authorizationInfo) {
        MemberFindResult result = memberFindUseCase.findById(authorizationInfo.id());
        MemberFindResponse response = new MemberFindResponse(result.id(), result.nickname(), result.email(), result.registrationState());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@Parameter(hidden = true) @Authorization AuthorizationInfo authorizationInfo) {
        memberDeleteUseCase.deleteById(authorizationInfo.id());
        return ResponseEntity.noContent().build();
    }

}
