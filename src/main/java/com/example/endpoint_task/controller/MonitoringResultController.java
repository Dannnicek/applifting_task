package com.example.endpoint_task.controller;

import com.example.endpoint_task.entity.MonitoringResult;
import com.example.endpoint_task.service.MonitoringResultService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class MonitoringResultController {

    private final MonitoringResultService monitoringResultService;

    public MonitoringResultController(MonitoringResultService monitoringResultService) {
        this.monitoringResultService = monitoringResultService;
    }

    @GetMapping()
    public List<MonitoringResult> getMonitoringResult(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        return monitoringResultService.getAllMonitoringResults(accessToken);
    }

    @GetMapping("/{endpointId}")
    public List<MonitoringResult> getResultsForEndpoint(@PathVariable("endpointId") Long endpointId,
                                                        HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        return monitoringResultService.getResultsForEndpoint(endpointId, accessToken);
    }

    @PostMapping
    public void createMonitoringResult(@RequestBody MonitoringResult monitoringResult) {
        monitoringResultService.createMonitoringResult(monitoringResult);
    }

    @PutMapping("/{id}")
    public void updateMonitoringResult(@PathVariable Long id, @RequestBody MonitoringResult monitoringResult) {
        monitoringResultService.updateMonitoringResult(id, monitoringResult);
    }

    @DeleteMapping("/{id}")
    public void deleteMonitoringResult(@PathVariable Long id) {
        monitoringResultService.deleteMonitoringResult(id);
    }
}
