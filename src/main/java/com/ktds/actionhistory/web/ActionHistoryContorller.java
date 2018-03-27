package com.ktds.actionhistory.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.service.ActionHistoryService;
import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.util.DateUtil;

@Controller
public class ActionHistoryContorller {
	
	private ActionHistoryService actionHistoryService;
	
	public void setActionHistoryService(ActionHistoryService actionHistoryService) {
		this.actionHistoryService = actionHistoryService;
	}

	@RequestMapping("/admin/actionhistory")
	public ModelAndView viewActionHistoryPage( ActionHistorySearchVO actionHistorySearchVO) {
		ModelAndView view = new ModelAndView();
		view.setViewName("actionHistory/actionHistory");
		
		String endDate = DateUtil.getNowDate();
		String[] splitedEndDate = endDate.split("-");
		actionHistorySearchVO.setEndDateYear(splitedEndDate[0]);
		actionHistorySearchVO.setEndDateMonth(splitedEndDate[1]);
		actionHistorySearchVO.setEndDateDate(splitedEndDate[2]);
		actionHistorySearchVO.setEndDate(endDate);
		
		String startDate = DateUtil.getComputedDate(
				Integer.parseInt(splitedEndDate[0]),
				Integer.parseInt(splitedEndDate[1]),
				Integer.parseInt(splitedEndDate[2]),
				DateUtil.DATE, -7);
		String[] splitedStartDate = startDate.split("-");
		actionHistorySearchVO.setStartDateYear(splitedStartDate[0]);
		actionHistorySearchVO.setStartDateMonth(splitedStartDate[1]);
		actionHistorySearchVO.setStartDateDate(splitedStartDate[2]);
		actionHistorySearchVO.setEndDate(startDate);
		
		view.addObject("search", actionHistorySearchVO);
		
		int startDateMaximumDate = DateUtil.getActualyMaximumDate(
				Integer.parseInt(splitedStartDate[0]),
				Integer.parseInt(splitedStartDate[1]));
		
		int endDateMaximumDate = DateUtil.getActualyMaximumDate(
				Integer.parseInt(splitedEndDate[0]),
				Integer.parseInt(splitedEndDate[1]));
		
		view.addObject("startDatemaximumDate", startDateMaximumDate);
		view.addObject("endDateMaximumDate", endDateMaximumDate);
		
		return view;
	}
	
}
