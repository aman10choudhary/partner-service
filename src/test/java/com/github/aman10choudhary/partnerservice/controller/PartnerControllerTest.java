package com.github.aman10choudhary.partnerservice.controller;

import com.github.aman10choudhary.partnerservice.PartnerServiceApplication;
import com.github.aman10choudhary.partnerservice.service.dto.response.Partner;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartnerServiceApplication.class)
@WebAppConfiguration
public class PartnerControllerTest {

    private MockMvc mockMvc;
    Date expectedExpiryDate;

    Gson gson = new Gson();
    Type listType = new TypeToken<List<Partner>>() {}.getType();
    String basePath = "/api/partners";

    @Autowired
    WebApplicationContext wac;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        createPartner();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        expectedExpiryDate = simpleDateFormat.parse("2020-08-02T07:10:28+02:00");
    }

    @Test
    public void getPartnersDefaultValuesTest() throws Exception {
        String path = basePath;
        executeGetPartnersRequest(path);
    }

    @Test
    public void getPartnersProvidedValueTest() throws Exception {
        String path = basePath + "?from=0&size=1";
        executeGetPartnersRequest(path);
    }

    @Test
    public void getPartnersEmptyPageTest() throws Exception {
        String path = basePath + "?from=2&size=4";
        MockHttpServletResponse response = performRequest(path);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void getPartnersInvalidParameterTest() throws Exception {
        String path = basePath + "?from=2&size=5";
        MockHttpServletResponse response = performRequest(path);
        assertEquals(400, response.getStatus());
    }

    private void executeGetPartnersRequest(String path) throws Exception {
        MockHttpServletResponse response = performRequest(path);
        List<Partner> partners = gson.fromJson((response.getContentAsString()), listType);
        partnerAssertions(partners, response.getStatus());
    }

    private MockHttpServletResponse performRequest(String path) throws Exception {
        MockHttpServletRequestBuilder request = get(path)
                .contentType(MediaType.APPLICATION_JSON);
        return this.mockMvc.perform(request).andReturn().getResponse();
    }

    private void partnerAssertions(List<Partner> partners, int status) {
        assertEquals(200, status);
        if(!partners.isEmpty()){
            assertEquals(1, (long)partners.get(0).getId());
            assertEquals("B2Boost", partners.get(0).getName());
            assertEquals("QR-AE945DF", partners.get(0).getReference());
            assertEquals("en_GB", partners.get(0).getLocale());
            assertEquals(expectedExpiryDate.getTime(), partners.get(0).getExpirationTime().getTime());
        }
    }

    @Test
    public void getPartnerById() throws Exception{
        String path = basePath + "/1";
        MockHttpServletResponse response = performRequest(path);
        Partner partner = gson.fromJson(response.getContentAsString(), Partner.class);
        assertEquals(200, response.getStatus());
        assertEquals(1, (long)partner.getId());
        assertEquals("B2Boost", partner.getName());
        assertEquals("QR-AE945DF", partner.getReference());
        assertEquals("en_GB", partner.getLocale());
        assertEquals(expectedExpiryDate.getTime(), partner.getExpirationTime().getTime());
    }

    @Test
    public void getPartnerByIdEmptyResponse() throws Exception {
        String path = basePath + "/0";
        MockHttpServletResponse response = performRequest(path);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void createPartnerTest() throws Exception {
        partnerAssertions(createPartner());
    }

    private Partner createPartner() throws Exception {
        MockHttpServletRequestBuilder request = post(basePath)
                .content(getPartnerRequest())
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = this.mockMvc.perform(request).andReturn().getResponse();
        return gson.fromJson(response.getContentAsString(), Partner.class);
    }

    private void partnerAssertions(Partner partnerResponse) {
        assertEquals("B2Boost", partnerResponse.getName());
        assertEquals("QR-AE945DF", partnerResponse.getReference());
        assertEquals("en_GB", partnerResponse.getLocale());
        assertEquals(expectedExpiryDate.getTime(), partnerResponse.getExpirationTime().getTime());
    }

    private String getPartnerRequest() {
        return "{\n" +
                "  \"expirationTime\": \"2020-08-02T07:10:28+02:00\",\n" +
                "  \"locale\": \"en_GB\",\n" +
                "  \"name\": \"B2Boost\",\n" +
                "  \"reference\": \"QR-AE945DF\"\n" +
                "}";
    }
}
