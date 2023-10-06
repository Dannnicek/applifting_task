package com.example.endpoint_task.integration;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.service.MonitoredEndpointService;
import com.example.endpoint_task.service.MonitoringService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class MonitoredEndpointControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoredEndpointService monitoredEndpointService;

    @MockBean
    private MonitoringService monitoringService;

    @Test
    void testGetAllMonitoredEndpointsForUser() throws Exception {
        String accessToken = "someAccessToken";
        List<MonitoredEndpoint> expectedEndpoints = Arrays.asList(
                new MonitoredEndpoint("Endpoint1", "http://example.com", 60, null),
                new MonitoredEndpoint("Endpoint2", "http://example.org", 120, null)
        );
        doReturn(expectedEndpoints).when(monitoredEndpointService).getAllMonitoredEndpointsForUser(accessToken);
        mockMvc.perform(get("/endpoint")
                        .header("Authorization", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(expectedEndpoints.size()))
                .andExpect(jsonPath("$[0].name").value(expectedEndpoints.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(expectedEndpoints.get(1).getName()));

        verify(monitoredEndpointService, times(1)).getAllMonitoredEndpointsForUser(accessToken);
    }

//    @Test
//    void testCreateMonitoredEndpoint() throws Exception {
//        MonitoredEndpoint endpoint = new MonitoredEndpoint();
//        endpoint.setName("Test Endpoint");
//        endpoint.setUrl("http://example.com");
//        endpoint.setMonitoredInterval(60);
//        String requestJson = asJsonString(endpoint);
//        mockMvc.perform(post("/endpoint")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isOk());
//        List<MonitoredEndpoint> endpoints = monitoredEndpointService.getAllMonitoredEndpoints();
//        assertEquals(1, endpoints.size());
//        MonitoredEndpoint createdEndpoint = endpoints.get(0);
//        assertEquals("Test Endpoint", createdEndpoint.getName());
//        assertEquals("http://example.com", createdEndpoint.getUrl());
//        assertEquals(60, createdEndpoint.getMonitoredInterval());
//    }
//
//    private String asJsonString(Object object) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(object);
//    }
}

