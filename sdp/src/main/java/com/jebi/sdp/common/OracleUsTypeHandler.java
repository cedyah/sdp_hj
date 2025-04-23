package com.jebi.sdp.common;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class OracleUsTypeHandler implements TypeHandlerCallback {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Object getResult(ResultGetter getter) throws SQLException {
		String str = null;

		try {
			//str = new String(getter.getString().getBytes("8859_1"), "EUC_KR");
			str = new String(getter.getString().getBytes("UTF-8"), "UTF-8");

		} catch (UnsupportedEncodingException e) {

			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			str = getter.getString();

		} catch (Exception localException) {

		}
		return str;
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		String str = null;

		try {
			//str = new String(((String) parameter).getBytes("EUC_KR"), "8859_1");
			str = new String(((String) parameter).getBytes("UTF-8"), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			str = (String) parameter;

		} catch (Exception localException) {

		}
		setter.setString(str);

	}

	public Object valueOf(String s) {
		return s;
	}

}
