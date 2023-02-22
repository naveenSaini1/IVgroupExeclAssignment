package com.excelapplication.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Files {
	@Id
	private String fileName;
	private String url;
	public Files() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Files(String fileName, String url) {
		super();
		this.fileName = fileName;
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
