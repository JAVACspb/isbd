package com.ifmo.isdb.strattanoakmant.controller;

import com.ifmo.isdb.strattanoakmant.model.Role;
import com.ifmo.isdb.strattanoakmant.security.AccessRole;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {

    @PostMapping
    @AccessRole({Role.SELLER})
    @ApiOperation(value = "Send report", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully sent report"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<String> sendReport(@ApiIgnore @RequestHeader("Authorization") String token,
                                              @RequestBody Map<String, String> reportData) {
        // Заглушка - просто возвращаем успех
        return ResponseEntity.ok("Report sent successfully");
    }
}


