package com.orhon.smartcampus.modules.core.graphql.gql.service;


import java.sql.Timestamp;
import java.util.Date;

public class DateService {


	public static Date convertDbDate(Object dbDate){
		if (dbDate == null){
			return null;
		}

		try {
			Date date = new Date(Long.parseLong(dbDate.toString()));
			return date;
		} catch (Exception e) {
			Timestamp t = Timestamp.valueOf(dbDate.toString());
			return new Date(t.getTime());
		}

	}
}
