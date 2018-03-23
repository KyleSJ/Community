package com.ktds.community.vo;

import io.github.seccoding.web.pager.annotations.EndRow;
import io.github.seccoding.web.pager.annotations.StartRow;

public class CommunitySearchVO {

	private int pageNo;
	
	@StartRow // Pager와 annotation Package에 있는 코드들로 인해 자동으로 지정해준다.
	private int startNumber;
	
	@EndRow
	private int endNumber;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	public int getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(int endNumber) {
		this.endNumber = endNumber;
	}

}
