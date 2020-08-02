package com.github.aman10choudhary.partnerservice.service;

import com.github.aman10choudhary.partnerservice.service.dto.request.PartnersRequest;
import com.github.aman10choudhary.partnerservice.service.dto.response.Partner;

import java.util.List;

public interface PartnerService {
    List<Partner> getPartners(PartnersRequest request);

    void deletePartner(PartnersRequest request);

    void createPartner(Partner partner);

    void updatePartner(Partner partner);
}
