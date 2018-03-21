package com.ktds.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class IpBlockInterceptor extends HandlerInterceptorAdapter{
	
	private List<String> blakcList;
	
	public IpBlockInterceptor() {
		blakcList = new ArrayList<String>();
		blakcList.add("192.168.123");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestorIp = request.getRemoteAddr();
		String contextPath = request.getContextPath();
		
		for (String blackIp : blakcList) {
			if(requestorIp.startsWith(blackIp)) {
				response.sendRedirect(contextPath + "/login");
			}
			
		}
		
		return true;
	}

}
