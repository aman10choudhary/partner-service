package com.github.aman10choudhary.partnerservice.service.impl;

import com.github.aman10choudhary.partnerservice.dao.PartnerDao;
import com.github.aman10choudhary.partnerservice.dao.dto.response.PartnerEntity;
import com.github.aman10choudhary.partnerservice.exceptions.PartnerNotFoundException;
import com.github.aman10choudhary.partnerservice.service.PartnerService;
import com.github.aman10choudhary.partnerservice.service.dto.request.PartnersRequest;
import com.github.aman10choudhary.partnerservice.service.dto.response.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.aman10choudhary.partnerservice.utilities.ApplicationConstants.Errors.PARTNER_NOT_FOUND;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    PartnerDao partnerDao;

    @Override
    public List<Partner> getPartners(PartnersRequest request) {
        if(request.getId() != null){
            return getPartners(request.getId());
        } else {
            return getPartners(request.getFrom(), request.getSize());
        }
    }

    private List<Partner> getPartners(Long id) {
        Optional<PartnerEntity> partnerEntity = partnerDao.findById(id);
        if(partnerEntity.isPresent()){
            List<Partner> partners = new ArrayList<>();
            partners.add(
                    new Partner(partnerEntity.get())
            );
            return partners;
        } else {
            throw new PartnerNotFoundException(PARTNER_NOT_FOUND);
        }

    }

    private List<Partner> getPartners(Integer offset, Integer limit) {
        return partnerDao.findAll(PageRequest.of(calculatePage(offset, limit), limit)).stream()
                .map(partnerEntity -> new Partner(partnerEntity))
                .collect(Collectors.toList());
    }

    private int calculatePage(Integer offset, Integer limit) {
        return ( offset + limit ) /limit - 1;
    }

    @Override
    @Transactional
    public void deletePartner(PartnersRequest request) {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setId(request.getId());
        partnerDao.delete(partnerEntity);
    }

    @Override
    @Transactional
    public void createPartner(Partner partner) {
        PartnerEntity partnerEntity = createPartnerEntity(partner);
        partnerDao.save(partnerEntity);
        partner.setId(partnerEntity.getId());
    }

    private PartnerEntity createPartnerEntity(Partner partner) {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setCompanyName(partner.getName());
        partnerEntity.setRef(partner.getReference());
        String[] lc = getLc(partner.getLocale());
        partnerEntity.setLocale(new Locale(lc[0], lc[1]));
        partnerEntity.setExpires(partner.getExpirationTime());
        return partnerEntity;
    }

    private String[] getLc(String locale) {
        return locale.split("_");
    }

    @Override
    @Transactional
    public void updatePartner(Partner partner) {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setId(partner.getId());
        partnerEntity.setCompanyName(partner.getName());
        partnerEntity.setRef(partner.getReference());
        partnerEntity.setExpires(partner.getExpirationTime());
        String[] lc = getLc(partner.getLocale());
        partnerEntity.setLocale(new Locale(lc[0], lc[1]));
        partnerDao.save(partnerEntity);
    }
}
