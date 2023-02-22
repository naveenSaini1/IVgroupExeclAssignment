package com.excelapplication.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excelapplication.Models.Files;

public interface FileRepo  extends JpaRepository<Files, String>{

	
}
