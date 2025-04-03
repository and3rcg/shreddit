package com.example.shreddit.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportControllerV1 {
    @PostMapping
    public String createReport() {
        return "Report created";
    }

    @GetMapping
    public String getReports() {
        // Apenas staff pode acessar os reports
        return "Reports";
    }
}
