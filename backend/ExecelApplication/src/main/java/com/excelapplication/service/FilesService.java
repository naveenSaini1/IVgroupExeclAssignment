package com.excelapplication.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.excelapplication.Models.Files;
import com.excelapplication.exception.FilesException;

public interface FilesService {
	public List<Files> getAllfiles() throws FilesException;
	public Files saveFiles( MultipartFile file,String columnName)  throws FilesException;
	public Files delteFiles(Files files) throws FilesException;

}
