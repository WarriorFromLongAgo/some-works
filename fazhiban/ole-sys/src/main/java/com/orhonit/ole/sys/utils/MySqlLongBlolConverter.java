package com.orhonit.ole.sys.utils;


import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;



public class MySqlLongBlolConverter extends BaseTypeHandler<String> {  
	
    //###指定字符集  
    private static final String DEFAULT_CHARSET = "utf-8";  

    public void setNonNullParameter(PreparedStatement ps, int i,  
            String parameter, JdbcType jdbcType) throws SQLException {  
        ByteArrayInputStream bis;  
        try {  
            //###把String转化成byte流  
            bis = new ByteArrayInputStream(parameter.getBytes(DEFAULT_CHARSET));  
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException("Blob Encoding Error!");  
        }     
        ps.setBinaryStream(i, bis, parameter.length());  
    }  

    @Override  
    public String getNullableResult(ResultSet rs, String columnName)  
            throws SQLException {  
        Blob blob = (Blob) rs.getBlob(columnName);  
        byte[] returnValue = null;  
        if (null != blob) {  
            returnValue = blob.getBytes(1, (int) blob.length());  
        }  
        try {  
            //###把byte转化成string  
        	if ( returnValue != null ) {
        		return new String(returnValue, DEFAULT_CHARSET);  
        	} else {
        		return "";
        	}
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException("Blob Encoding Error!");  
        }  
    }  

    public String getNullableResult(CallableStatement cs, int columnIndex)  
            throws SQLException {  
        Blob blob = (Blob) cs.getBlob(columnIndex);  
        byte[] returnValue = null;  
        if (null != blob) {  
            returnValue = blob.getBytes(1, (int) blob.length());  
        }  
        try {  
            return new String(returnValue, DEFAULT_CHARSET);  
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException("Blob Encoding Error!");  
        }  
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }




}  



