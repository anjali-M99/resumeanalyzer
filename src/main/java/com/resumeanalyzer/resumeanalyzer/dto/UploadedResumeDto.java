package com.resumeanalyzer.resumeanalyzer.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UploadedResumeDto implements Serializable {
	 
		private	String uploaderName;
		private	String uploaderMailId;
		private	String fileName;
		private	String fileExtenstion;
		private	int yrExp;
		private	transient MultipartFile  fileData;
		private	String JobId;
}
