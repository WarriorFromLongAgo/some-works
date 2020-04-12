package com.orhonit.modules.generator.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class JacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
	
	
	/**
	 * 处理数组类型的null值
	 * Title: JacksonHttpMessageConverter.java
	 * @Description:
	 * @author YaoSC
	 * @date 2019年7月21日
	 */
	public class NullArrayJsonSerializer  extends JsonSerializer<Object>{
		
		@Override
		public void serialize(Object value,JsonGenerator jgen,SerializerProvider provider) throws IOException {
			if(value == null) {
				jgen.writeStartArray();
				jgen.writeEndArray();
			}
		}
	}
	
	/**
	 * 处理字符串等类型的Null值
	 * Title: JacksonHttpMessageConverter.java
	 * @Description:
	 * @author YaoSC
	 * @date 2019年7月21日
	 */
	public class NullStringJsonSerializer extends JsonSerializer<Object>{

		@Override
		public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
			// TODO Auto-generated method stub
			jsonGenerator.writeString(StringUtils.EMPTY);
			
		}
	}
	
	
	public class NullBooleanJsonSerializer  extends JsonSerializer<Object>{

		@Override
		public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
			// TODO Auto-generated method stub
			jsonGenerator.writeBoolean(false);
		}
	}
	
	public class MyBeanSerializerModifier  extends BeanSerializerModifier {
		@Override
		public List<BeanPropertyWriter>changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties){
			for(Object beanProperty : beanProperties) {
				BeanPropertyWriter  writer = (BeanPropertyWriter) beanProperty;
				if(isArrayType(writer)) {
					writer.assignNullSerializer(new NullArrayJsonSerializer());
				}else if(isStringType(writer)) {
					writer.assignNullSerializer(new NullArrayJsonSerializer());
				}else if(isNumberType(writer)) {
					writer.assignNullSerializer(new NullArrayJsonSerializer());
				}else if(isBooleanType(writer)) {
					writer.assignNullSerializer(new NullArrayJsonSerializer());
				}
			}
			return beanProperties;
		}
        
		
		//是否数组
		private boolean isArrayType(BeanPropertyWriter writer) {
			Class<?>clazz = writer.getType().getRawClass();
			return CharSequence.class.isAssignableFrom(clazz)||CharSequence.class.isAssignableFrom(clazz);
		}
		//是否String
		private boolean isStringType(BeanPropertyWriter writer) {
			Class<?>clazz = writer.getType().getRawClass();
			return CharSequence.class.isAssignableFrom(clazz)||CharSequence.class.isAssignableFrom(clazz);
		}
		//是否int
		private boolean isNumberType(BeanPropertyWriter writer) {
			Class<?>clazz = writer.getType().getRawClass();
			return CharSequence.class.isAssignableFrom(clazz)||CharSequence.class.isAssignableFrom(clazz);
		}
		//是否boolean
		private boolean isBooleanType(BeanPropertyWriter writer) {
			Class<?>clazz =writer.getType().getRawClass();
			return CharSequence.class.isAssignableFrom(clazz)||CharSequence.class.isAssignableFrom(clazz);
		}
	}
	
	
	JacksonHttpMessageConverter(){
		getObjectMapper().setSerializerFactory(getObjectMapper()
				.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));
	}
	

}
