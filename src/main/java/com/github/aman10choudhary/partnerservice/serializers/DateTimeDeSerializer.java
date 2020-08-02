package com.github.aman10choudhary.partnerservice.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.aman10choudhary.partnerservice.exceptions.BadRequestException;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Date;

import static com.github.aman10choudhary.partnerservice.utilities.ApplicationConstants.Errors.INVALID_DATE_FORMAT;

public class DateTimeDeSerializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        try {
            return DateTime.parse(node.asText()).toDate();
        } catch (IllegalArgumentException e){
            throw new BadRequestException(INVALID_DATE_FORMAT);
        }
    }
}