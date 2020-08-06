package com.github.aman10choudhary.partnerservice.dao.dto.response;

import com.github.aman10choudhary.partnerservice.dao.dto.response.PartnerEntity;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PartnerEntityTest {

    @Test
    public void noArgumentConstructorTest(){
        PartnerEntity partnerEntity = new PartnerEntity();
        assertEquals(null, partnerEntity.getId());
        assertEquals(null, partnerEntity.getCompanyName());
        assertEquals(null, partnerEntity.getRef());
        assertEquals(null, partnerEntity.getLocale());
        assertEquals(null, partnerEntity.getExpires());
    }

    @Test
    public static PartnerEntity allArgumentConstructorTest(){
        PartnerEntity partnerEntity = new PartnerEntity(
                1L,
                "B2Boost",
                "QR-AE945DF",
                new Locale("en", "gb"),
                new Date());
        assertEquals(1, (long)partnerEntity.getId());
        assertEquals("B2Boost", partnerEntity.getCompanyName());
        assertEquals("QR-AE945DF", partnerEntity.getRef());
        assertEquals("en_GB", partnerEntity.getLocale().toString());
        assertNotNull(partnerEntity.getExpires());
        return partnerEntity;
    }

    @Test
    public void setterTest(){
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setId(1L);
        partnerEntity.setCompanyName("B2Boost");
        partnerEntity.setRef("QR-AE944DF");
        partnerEntity.setLocale(new Locale("en", "gb"));
        partnerEntity.setExpires(new Date());
        assertEquals(1, (long)partnerEntity.getId());
        assertEquals("B2Boost", partnerEntity.getCompanyName());
        assertEquals("QR-AE944DF", partnerEntity.getRef());
        assertEquals("en_GB", partnerEntity.getLocale().toString());
        assertNotNull(partnerEntity.getExpires());
    }
}
