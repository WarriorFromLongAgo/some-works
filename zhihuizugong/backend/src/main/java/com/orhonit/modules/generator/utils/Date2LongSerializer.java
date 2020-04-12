package com.orhonit.modules.generator.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther:Vampire
 * @date:2018/12/10 0010
 */
public class Date2LongSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(date.getTime() / 1000);
        
		/*
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); String
		 * formattedDate = formatter.format(date);
		 * jsonGenerator.writeString(formattedDate);
		 */

    }
}
