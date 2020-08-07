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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartnerServiceApplication.class)
@WebAppConfiguration
public class PartnerControllerTest {

    private MockMvc mockMvc;
    Date expectedExpiryDate;

    Gson gson = new Gson();
    Type listType = new TypeToken<List<Partner>>() {}.getType();
    String basePath = "/api/partners";
    String slash = "/";

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
    public void getPartnersInvalidSizeTest() throws Exception {
        String path = basePath + "?from=0&size=-1";
        MockHttpServletResponse response = performRequest(path);
        assertEquals(400, response.getStatus());
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

    @Test
    public void createPartnerNullNameTest() throws Exception {
        MockHttpServletResponse response = createPartnerResponse(null);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void updatePartnerTest() throws Exception {
        Partner partner = updatePartner();
        partnerAssertions(partner, "Google");
    }

    @Test
    public void updatePartnerNotFoundTest() throws Exception {
        assertEquals(404, updatePartner(1000L).getStatus());
    }

    @Test
    public void deletePartnerTest() throws Exception {
        MockHttpServletResponse response = deletePartner();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void deletePartnerNotFoundTest() throws Exception {
        MockHttpServletResponse response = deletePartner(1000L);
        assertEquals(404, response.getStatus());
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

    private MockHttpServletResponse deletePartner(long id) throws Exception {
        String path = basePath + slash + id;
        MockHttpServletRequestBuilder request = delete(path).
                contentType(MediaType.APPLICATION_JSON);
        return this.mockMvc.perform(request).andReturn().getResponse();
    }

    private MockHttpServletResponse deletePartner() throws Exception {
        return deletePartner(createPartner().getId());
    }


    private MockHttpServletResponse updatePartner(long id) throws Exception {
        String path = basePath + slash + id;
        MockHttpServletRequestBuilder request = put(path).
                content(getPartnerRequest("Google")).
                contentType(MediaType.APPLICATION_JSON);
        return this.mockMvc.perform(request).andReturn().getResponse();
    }

    private Partner updatePartner() throws Exception {
        MockHttpServletResponse response = updatePartner(createPartner().getId());
        return gson.fromJson(response.getContentAsString(), Partner.class);
    }

    private Partner createPartner() throws Exception {
        return createPartner("B2Boost");
    }

    private Partner createPartner(String name) throws Exception {
        MockHttpServletResponse response = createPartnerResponse(name);
        return gson.fromJson(response.getContentAsString(), Partner.class);
    }

    private MockHttpServletResponse createPartnerResponse(String name) throws Exception {
        MockHttpServletRequestBuilder request = post(basePath)
                .content(getPartnerRequest(name))
                .contentType(MediaType.APPLICATION_JSON);
        return this.mockMvc.perform(request).andReturn().getResponse();
    }

    private void partnerAssertions(Partner partnerResponse) {
        partnerAssertions(partnerResponse, "B2Boost");
    }

    private void partnerAssertions(Partner partner, String name) {
        assertEquals(name, partner.getName());
        assertEquals("QR-AE945DF", partner.getReference());
        assertEquals("en_GB", partner.getLocale());
        assertEquals(expectedExpiryDate.getTime(), partner.getExpirationTime().getTime());
    }

    private String getPartnerRequest(String name) {
        return "{\n" +
                "  \"expirationTime\": \"2020-08-02T07:10:28+02:00\",\n" +
                "  \"locale\": \"en_GB\",\n" +
                (name != null ? "  \"name\": \""+ name +"\",\n" : "") +
                "  \"reference\": \"QR-AE945DF\"\n" +
                "}";
    }
}
