package com.excelapplication.service.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.excelapplication.Models.Files;
import com.excelapplication.Repo.FileRepo;
import com.excelapplication.exception.FilesException;
import com.excelapplication.service.FilesService;
@Service
public class FileSeriviceImpl implements FilesService {

	@Autowired
	private FileRepo fileRepo;
	
	@Override
	public List<Files> getAllfiles() throws FilesException {
		List<Files> list=fileRepo.findAll();
		for(Files i:list) {
			String download=ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(i.getFileName()).toUriString();
			i.setUrl(download);
		}
		return list;
	}

	@Override
	public Files saveFiles( MultipartFile file,String columnName) throws FilesException {
		
	    try {	            
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet=workbook.getSheetAt(0);
            if(sheet.getRow(0)==null) {
            	throw new FilesException("there is no data in excel file");
            }
            int row=sheet.getLastRowNum();
            List<String> list = new ArrayList<>();
            columnName=columnName.toLowerCase();
            String string="abcdefghijklmnopqrstuvwxyz";
            int in=string.indexOf(columnName);
            if(in==-1) {
            	throw new FilesException("there is no colunm present");

            }
            int data=0;
            System.out.println(row+"  row");
            for(int i=0;i<row;i++) {
            	Row row2=sheet.getRow(data);
            	System.out.println(i);

            	if(row2==null) {
        			continue;
        		}
            	for(int j=0;j<row2.getLastCellNum();j++) {
            		if(j!=in) {
            			continue;
            		}
            		System.out.println(j+" j");
            		Cell cell=row2.getCell(j);
            		if(cell==null) {
            			continue;
            		}
            		switch (cell.getCellType()) {
					case STRING: 
						list.add(cell.getStringCellValue());
						break;
					case NUMERIC:
						String aString=cell.getNumericCellValue()+"";
						list.add(aString);
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + cell);
					}
            	}
            	data++;
            }
            
        // Step 2: Create a new Excel sheet and copy the data from the specified column to the new sheet
            Workbook newWorkbook = new XSSFWorkbook();
            Sheet newSheet = newWorkbook.createSheet(file.getOriginalFilename());
            for (int i = 0; i < list.size(); i++) {
                Row rows = newSheet.createRow(i);
                Cell cell = rows.createCell(0);
                cell.setCellValue(list.get(i));
            }
            FileOutputStream outputStream = new FileOutputStream("files/"+file.getOriginalFilename());
            newWorkbook.write(outputStream);
            outputStream.close();
			Files files=new Files();
			files.setFileName(file.getOriginalFilename());
			files.setUrl(file.getOriginalFilename());
			Files aFiles= fileRepo.save(files);
			String download=ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(aFiles.getFileName()).toUriString();
			aFiles.setUrl(download);
			return aFiles;
            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
             throw new FilesException(e.getMessage());
        }
		
	}

	@Override
	public Files delteFiles(Files files) throws FilesException {
		Optional<Files> opFiles= fileRepo.findById(files.getFileName());
		if(opFiles.isEmpty()) {
			throw new FilesException("File not found");
		}
		
		fileRepo.delete(opFiles.get());
		return opFiles.get();
	}

}
