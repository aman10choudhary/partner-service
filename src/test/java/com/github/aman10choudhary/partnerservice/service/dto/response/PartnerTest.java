package com.github.aman10choudhary.partnerservice.service.dto.response;

import com.github.aman10choudhary.partnerservice.dao.dto.response.PartnerEntityTest;
import com.github.aman10choudhary.partnerservice.dao.dto.response.PartnerEntity;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PartnerTest {

    @Test
    public void noArgumentConstructorTest(){
        Partner partner = new Partner();
        assertEquals(null, partner.getId());
        assertEquals(null, partner.getName());
        assertEquals(null, partner.getReference());
        assertEquals(null, partner.getLocale());
        assertEquals(null, partner.getExpirationTime());
    }

    @Test
    public void allArgumentConstructorTest(){
        Partner partner = new Partner(
                1L,
                "B2Boost",
                "QR-AE945DF",
                "en_GB",
                new Date());
        assertEquals(1, (long)partner.getId());
        assertEquals("B2Boost", partner.getName());
        assertEquals("QR-AE945DF", partner.getReference());
        assertEquals("en_GB", partner.getLocale());
        assertNotNull(partner.getExpirationTime());
    }

    @Test
    public void partnerEntityConstructorTest(){
        PartnerEntity partnerEntity = PartnerEntityTest.allArgumentConstructorTest();
        Partner partner = new Partner(partnerEntity);
        assertEquals(1, (long)partner.getId());
        assertEquals("B2Boost", partner.getName());
        assertEquals("QR-AE945DF", partner.getReference());
        assertEquals("en_GB", partner.getLocale());
        assertNotNull(partner.getExpirationTime());
    }

    @Test
    public void setterTest(){
        Partner partner = new Partner();
        partner.setId(1L);
        partner.setName("B2Boost");
        partner.setReference("QR-AE945DF");
        partner.setLocale("en_GB");
        partner.setExpirationTime(new Date());
        assertEquals(1, (long)partner.getId());
        assertEquals("B2Boost", partner.getName());
        assertEquals("QR-AE945DF", partner.getReference());
        assertEquals("en_GB", partner.getLocale());
        assertNotNull(partner.getExpirationTime());
    }
}
