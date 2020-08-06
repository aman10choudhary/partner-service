package com.github.aman10choudhary.partnerservice.service.dto.request;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PartnersRequestTest {
    @Test
    public void noArgumentConstructorTest(){
        PartnersRequest partnersRequest = new PartnersRequest();
        assertEquals(null, partnersRequest.getFrom());
        assertEquals(null, partnersRequest.getSize());
        assertEquals(null, partnersRequest.getId());
    }

    @Test
    public void allArgumentConstructorTest(){
        PartnersRequest partnersRequest = new PartnersRequest(
                0,
                10,
                1L);
        assertEquals(1, (long)partnersRequest.getId());
        assertEquals(0, (int)partnersRequest.getFrom());
        assertEquals(10, (int)partnersRequest.getSize());
    }

    @Test
    public void builderTest(){
        PartnersRequest partnersRequest = PartnersRequest.builder()
                .id(1L)
                .from(0)
                .build();
        assertEquals(1, (long)partnersRequest.getId());
        assertEquals(0, (int)partnersRequest.getFrom());
        assertEquals(null, partnersRequest.getSize());
    }
}
