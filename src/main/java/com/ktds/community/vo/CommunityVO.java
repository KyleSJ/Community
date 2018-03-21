package com.ktds.community.vo;

import java.io.File;
import java.io.IOException;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.ktds.member.vo.MemberVO;

public class CommunityVO {

	private int id;
	
	@NotEmpty(message="제목이 없습니다.")
	private String title;
	
	@NotEmpty(message="내용이 없습니다.")
	private String body;
	private int userId;
	
	private String writeDate;
	
	private int viewCount;
	private int recommendCount;
	private String requestIp;
	
	private MultipartFile file;
	private String displayFilename;
	
	private MemberVO memberVO;

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	public String getDisplayFilename() {
		if(displayFilename == null) {
			return "";
		}
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	//fileUpload Code
	public String save() {
		
		displayFilename = file.getOriginalFilename();
		
		if( file != null && !file.isEmpty() ) {
			File newFile = new File("/Users/kangseongjae/Desktop/study/ktdsCommunityFile/" + file.getOriginalFilename());
			try {
				file.transferTo(newFile);
				return newFile.getAbsolutePath();
			} catch (IllegalStateException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		return null;
	}
}
