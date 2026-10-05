package com.resumeanalyzer.resumeanalyzer.entity;



import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table(name="ResumeMetadata", uniqueConstraints = {
		@UniqueConstraint (columnNames = {"uploaderMailId","jobId"})
})
@Entity
@ToString
public class ResumeMetadata {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "job_id", nullable = false)
	private JobDetails jobId;
	
	private String uploaderName;
	
	@Column(nullable = false)
	private String uploaderMailId;
	
	private String fileName;
	
	private String fileExtenstion;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
    private byte[] fileData;
	
	@Column(length = 22064)
	private String resumeParseData;
		
	private int matchScore;
	
	private String status = "New";
	
	private int yrExp;
	
	private String aiSuggestion;
	
	@CreationTimestamp
	private LocalDateTime uploadedDateTime;
}
