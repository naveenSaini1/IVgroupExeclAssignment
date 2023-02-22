package com.excelapplication.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.excelapplication.Models.Files;
import com.excelapplication.exception.FilesException;
import com.excelapplication.service.FilesService;

@RestController
public class FileController {
	@Autowired
	private FilesService filesService;
	
	  @PostMapping("/upload")
	    public ResponseEntity<Files> handleFileUpload(@RequestParam("file") MultipartFile file,
	                                                   @RequestParam("column") String columnName) throws FilesException {
		  return new ResponseEntity<Files>(filesService.saveFiles(file, columnName),HttpStatus.OK);
	  }  
	  @GetMapping("/all")
	  public ResponseEntity<List<Files>> getAllFiles() throws FilesException{
		  return new ResponseEntity<List<Files>>(filesService.getAllfiles(),HttpStatus.OK);
	  }
	  @DeleteMapping("/del")
	  public ResponseEntity<Files> delteFilesHandller(@RequestBody Files files) throws FilesException{
		  return new ResponseEntity<Files>(filesService.delteFiles(files),HttpStatus.OK);
	  }
	  
	  
	  
}
