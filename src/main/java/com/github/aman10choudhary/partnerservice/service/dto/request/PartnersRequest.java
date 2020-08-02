package com.github.aman10choudhary.partnerservice.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnersRequest {
    private Integer from;
    private Integer size;
    private Long id;
}
