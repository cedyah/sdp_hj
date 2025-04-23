package com.jebi.sdp.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class PopupController {
	private static final Logger logger = LoggerFactory.getLogger(PopupController.class);
	
	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value="popup.{type}.do")
	public String callPopup(@PathVariable String type
			, HttpServletRequest request, ModelMap model) throws Exception{
		String url = "";
		
		if("searchAddress".equals(type)) {
			url = "/popup/popSearchAddress";
		}
		return url;
	}
}
