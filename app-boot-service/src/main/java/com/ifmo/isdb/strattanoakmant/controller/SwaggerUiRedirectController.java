package com.ifmo.isdb.strattanoakmant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Springfox 3 exposes UI at /swagger-ui/index.html (plus /swagger-ui/).
 * For convenience (and for demos), keep the legacy /swagger-ui.html working too.
 *
 * Note: server.servlet.context-path is "/api", so resulting URLs are:
 * - /api/swagger-ui/index.html (real UI)
 * - /api/swagger-ui.html (redirect)
 */
@Controller
public class SwaggerUiRedirectController {

    @GetMapping("/swagger-ui.html")
    public String redirectToIndex() {
        return "redirect:/swagger-ui/index.html";
    }
}



