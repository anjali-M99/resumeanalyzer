package com.resumeanalyzer.resumeanalyzer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name ="JobDetails")
@Table
@Getter
@Setter
@ToString
public class JobDetails {
	
	@Id
	private String jobId;
	
	@Column(length=2048)
	private String jobDescription;

}
