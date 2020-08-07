package com.github.aman10choudhary.partnerservice.controller;

import com.github.aman10choudhary.partnerservice.exceptions.BadRequestException;
import com.github.aman10choudhary.partnerservice.service.PartnerService;
import com.github.aman10choudhary.partnerservice.service.dto.request.PartnersRequest;
import com.github.aman10choudhary.partnerservice.service.dto.response.Partner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;

import static com.github.aman10choudhary.partnerservice.utilities.ApplicationConstants.Errors.*;

@RestController
@Validated
@RequestMapping(path = "/api/partners" , produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Partners.")
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    @GetMapping()
    public ResponseEntity<List<Partner>> getPartners(@RequestParam(defaultValue = "0", required = false) @Min(value = 0, message = INVALID_FROM)Integer from,
                                                     @RequestParam(defaultValue = "10", required = false) @Min(value = 0, message = INVALID_SIZE) Integer size){
        if(from % size != 0){
            throw new BadRequestException(INVALID_FROM_SIZE_COMBINATION);
        }
        return ResponseEntity.ok(
                partnerService.getPartners(
                        PartnersRequest.builder()
                                .from(from)
                                .size(size)
                                .build()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@ApiParam(name = "id", example = "id") @PathVariable Long id){
        return ResponseEntity.ok(
                partnerService.getPartners(
                        PartnersRequest.builder()
                                .id(id)
                                .build()
                ).get(0)
        );
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@Valid @RequestBody Partner partner){
        partnerService.createPartner(partner);
        return ResponseEntity.status(HttpStatus.CREATED).body(partner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> update(@ApiParam(name = "id", example = "id") @PathVariable Long id,
            @Valid @RequestBody Partner partner){
        partner.setId(id);
        partnerService.updatePartner(partner);
        return ResponseEntity.ok(partner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePartnerById(@ApiParam(name = "id", example = "id") @PathVariable Long id){
        partnerService.deletePartner(
                PartnersRequest.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.ok().build();
    }
}
