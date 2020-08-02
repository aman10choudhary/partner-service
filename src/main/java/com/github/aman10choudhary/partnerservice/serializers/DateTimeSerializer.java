package com.github.aman10choudhary.partnerservice.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.format.DateTimeFormat;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        String outPattern = "yyyy-MM-dd'T'HH:mm:ssXXX";
        SimpleDateFormat outFormat = new SimpleDateFormat(outPattern, Locale.getDefault());
        String outDate = outFormat.format(dateTime);
        jsonGenerator.writeString(outDate);
    }
}
