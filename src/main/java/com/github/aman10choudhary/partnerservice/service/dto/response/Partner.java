package com.github.aman10choudhary.partnerservice.service.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.aman10choudhary.partnerservice.dao.dto.request.PartnerEntity;
import com.github.aman10choudhary.partnerservice.serializers.DateTimeDeSerializer;
import com.github.aman10choudhary.partnerservice.serializers.DateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Date;

import static com.github.aman10choudhary.partnerservice.utilities.ApplicationConstants.Errors.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @ApiModelProperty(readOnly = true)
    private Long id;

    @NotNull(message = NAME_NOT_NULL)
    private String name;

    private String reference;

    @Pattern(regexp = "^([a-z]{2}_[A-Z]{2})$" , message = INVALID_LOCALE_FORMAT)
    private String locale;

    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeSerializer.class)
    @ApiModelProperty(example = "2020-08-02T07:10:28+02:00")
    private Date expirationTime;

    public Partner(PartnerEntity partnerEntity) {
        this.id = partnerEntity.getId();
        this.name = partnerEntity.getCompanyName();
        this.reference = partnerEntity.getRef();
        this.locale = partnerEntity.getLocale().toString();
        this.expirationTime = partnerEntity.getExpires();
    }
}
