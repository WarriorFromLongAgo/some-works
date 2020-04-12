package com.orhonit.modules.generator.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;



/**
 * 
 * Title: JacksonConfig.java
 * @Description:使用Jackon作为JSON MessageConverter 对null处理为" "
 * @author YaoSC
 * @date 2019年7月21日
 */
@Configuration
public class JacksonConfig {
	
	@Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        
		//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		//objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //objectMapper.setDateFormat(dateFormat);
        //objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, false);
       
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
               // jsonGenerator.writeNumber(-1);
            }

        });
        return objectMapper;
    }


}
