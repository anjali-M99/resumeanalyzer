package com.resumeanalyzer.resumeanalyzer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resumeanalyzer.resumeanalyzer.entity.JobDetails;

@Repository
public interface JobDescription extends JpaRepository<JobDetails, String>{

}
