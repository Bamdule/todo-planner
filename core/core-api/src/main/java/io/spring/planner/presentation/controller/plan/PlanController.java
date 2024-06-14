package io.spring.planner.presentation.controller.plan;

import io.spring.planner.presentation.config.authorization.Authorization;
import io.spring.planner.presentation.config.authorization.AuthorizationInfo;
import io.spring.planner.usecase.plan.findlist.PlanFindListQuery;
import io.spring.planner.usecase.plan.findlist.PlanFindListUseCase;
import io.spring.planner.usecase.plan.registration.PlanRegistrationCommand;
import io.spring.planner.usecase.plan.registration.PlanRegistrationResult;
import io.spring.planner.usecase.plan.registration.PlanRegistrationUseCase;
import io.spring.planner.usecase.plan.updatestate.PlanStateUpdateCommand;
import io.spring.planner.usecase.plan.updatestate.PlanStateUpdateUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/plans")
@RestController
public class PlanController {

    private final PlanFindListUseCase planFindListUseCase;
    private final PlanRegistrationUseCase planRegistrationUseCase;
    private final PlanStateUpdateUseCase planStateUpdateUseCase;


    @GetMapping
    public ResponseEntity<Page<PlanResponse>> findListPlan(
        @Parameter(hidden = true) @Authorization AuthorizationInfo authorizationInfo,
        @Parameter(name = "페이징 OR 정렬 옵션", description = "sort = 'id,DESC'", in = QUERY)
        @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        PlanFindListQuery query = new PlanFindListQuery(authorizationInfo.id());
        Page<PlanResponse> responses = planFindListUseCase.findList(query, pageable)
            .map(PlanResponse::create);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<PlanRegistrationResponse> registerPlan(
        @Parameter(hidden = true) @Authorization AuthorizationInfo authorizationInfo,
        @RequestBody @Valid PlanRegistrationRequest request
    ) {
        PlanRegistrationCommand command = new PlanRegistrationCommand(request.title(), authorizationInfo.id());
        PlanRegistrationResult result = planRegistrationUseCase.register(command);
        PlanRegistrationResponse response = PlanRegistrationResponse.create(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<Void> updatePlanState(
        @Parameter(hidden = true) @Authorization AuthorizationInfo authorizationInfo,
        @PathVariable(value = "planId") Long planId,
        @RequestBody @Valid PlanStateUpdateRequest request
    ) {
        PlanStateUpdateCommand command = new PlanStateUpdateCommand(planId, request.state(), authorizationInfo.id());
        planStateUpdateUseCase.updatePlanState(command);

        return ResponseEntity.noContent().build();
    }
}
