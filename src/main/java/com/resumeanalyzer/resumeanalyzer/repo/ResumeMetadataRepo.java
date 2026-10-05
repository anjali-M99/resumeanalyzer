package com.resumeanalyzer.resumeanalyzer.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.resumeanalyzer.resumeanalyzer.entity.ResumeMetadata;

import jakarta.transaction.Transactional;

@Repository
@EnableJpaRepositories
public interface ResumeMetadataRepo extends JpaRepository<ResumeMetadata, Integer> {

	@Query("select id from ResumeMetadata r where r.uploaderMailId =:mailId AND r.jobId.jobId=:jobId" )
	int findId(@Param("mailId") String mailId, @Param("jobId") String jobId);
	
	@Query("select fileData from ResumeMetadata r where r.id =:id" )
	byte[] findByIdUploaderMailId(@Param("id") int id);
	
	@Modifying
	@Transactional
	@Query("update ResumeMetadata r set r.resumeParseData=:s where r.id =:id")
	int updateResumeParseData(@Param("s") String s, @Param("id") int id);

	
	@Query("select resumeParseData from ResumeMetadata r where r.id =:id")
	String fetchParseDatafromUploaderMailId(@Param("id") int id);
	
	@Query("select r from ResumeMetadata r where r.status='New' OR  r.status='Error'")
	List<ResumeMetadata> fetchRecordsForNewStatus();
	
	@Query("select r from ResumeMetadata r where  r.yrExp >=:yearExp AND r.jobId.jobId=:jobId "
			+ "AND r.uploadedDateTime >=:lastUpdatedtime order by r.matchScore desc ")
	List<ResumeMetadata> fetchRecordForJobMatch(@Param("yearExp") int yearExp, 
			@Param("jobId") String jobId,
			@Param("lastUpdatedtime") LocalDateTime lastUpdatedtime);

	@Modifying
	@Transactional
	@Query("update ResumeMetadata r set r.matchScore=:matchScore, r.aiSuggestion=:aiSuggestion where r.uploaderMailId =:mailId AND "
			+ "r.jobId.jobId=:jobId")
	int updateMatchScore_Suggestion(@Param("matchScore") int matchScore, @Param("aiSuggestion") String aiSuggestion,
			@Param("mailId") String mailId, @Param("jobId") String jobId);
	
	
	
}
