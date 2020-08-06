package com.github.aman10choudhary.partnerservice.dao;

import com.github.aman10choudhary.partnerservice.dao.dto.response.PartnerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PartnerDao extends PagingAndSortingRepository<PartnerEntity, Long> {
}
