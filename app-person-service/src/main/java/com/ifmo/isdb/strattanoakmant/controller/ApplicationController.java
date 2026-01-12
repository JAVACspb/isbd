package com.ifmo.isdb.strattanoakmant.controller;

import com.ifmo.isdb.strattanoakmant.controller.dto.ApplicationDto;
import com.ifmo.isdb.strattanoakmant.model.Application;
import com.ifmo.isdb.strattanoakmant.model.Role;
import com.ifmo.isdb.strattanoakmant.security.AccessRole;
import com.ifmo.isdb.strattanoakmant.service.MappingService;
import com.ifmo.isdb.strattanoakmant.service.ifc.ApplicationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final MappingService mappingService;

    @GetMapping
    @AccessRole({Role.HR})
    @ApiOperation(value = "Get applications", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully get applications"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<List<ApplicationDto>> getApplications(@ApiIgnore @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(mappingService.mapList(applicationService.getApplications(), ApplicationDto.class));
    }

    @PostMapping
    @ApiOperation(value = "Add application", response = ApplicationDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added application"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<ApplicationDto> saveApplication(@RequestBody @Valid ApplicationDto applicationDto) {
        Application saved = applicationService.saveApplication(mappingService.map(applicationDto, Application.class));
        return ResponseEntity.ok(mappingService.map(saved, ApplicationDto.class));
    }

    @DeleteMapping("/{id}")
    @AccessRole({Role.HR})
    @ApiOperation(value = "Delete application")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully deleted application"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<String> deleteApplication(@ApiIgnore @RequestHeader("Authorization") String token, boolean reason,
                                                         @PathVariable Long id) {
        return ResponseEntity.ok(applicationService.deleteApplication(id, reason));
    }
}
