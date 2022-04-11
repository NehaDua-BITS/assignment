package com.intuit.profilevalidationsystem.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateDeserializer extends JsonDeserializer<ZonedDateTime> {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException {

		if(StringUtils.isEmpty(p.getValueAsString()))
			return null;
		else
			return ZonedDateTime.parse(p.getValueAsString(), formatter).withZoneSameInstant( ZoneId.of("UTC"));
	}

}