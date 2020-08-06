package com.github.aman10choudhary.partnerservice.controller;

import com.github.aman10choudhary.partnerservice.PartnerServiceApplication;
import com.sun.xml.bind.v2.TODO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartnerServiceApplication.class)
@WebAppConfiguration
public class PartnerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getPartnersDefaultValuesTest() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/partners");
        MockHttpServletResponse response = this.mockMvc.perform(request).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        //check for entity response object
    }
}
