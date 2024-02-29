package com.example.gymAp.aspectTest;

import com.example.gymAp.aspect.RestCallLoggingAspect;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestCallLoggingAspectTest {
    private static Logger LOGGER = LoggerFactory.getLogger(RestCallLoggingAspect.class);
    @Test
    public void testExtractResponseDetailsWithResponseEntity() {
        RestCallLoggingAspect aspect = new RestCallLoggingAspect();

        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>("Mock Response Body", HttpStatus.OK);

        String result = aspect.extractResponseDetails(mockResponseEntity);

        assertEquals("Status: 200, Response Body: Mock Response Body", result);
    }

    @Test
    public void testExtractResponseDetailsWithInvalidResponseType() {
        RestCallLoggingAspect aspect = new RestCallLoggingAspect();

        Object mockInvalidResponse = "Invalid Response";

        String result = aspect.extractResponseDetails(mockInvalidResponse);

        assertEquals("Invalid response type", result);
    }
    @Test
    public void testExtractRequestParameters() {
        RestCallLoggingAspect aspect = new RestCallLoggingAspect();

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("param1", "value1");
        mockRequest.addParameter("param2", "value2");

        String result = aspect.extractRequestParameters(mockRequest);

        assertEquals("param1=value1; param2=value2; ", result);
    }

}