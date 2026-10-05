package com.resumeanalyzer.resumeanalyzer.dto;

import java.io.Serializable;
import java.util.Objects;

import com.resumeanalyzer.resumeanalyzer.entity.JobDetails;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Embeddable
public class ResumeMetadataId implements Serializable{
	
	private String uploaderMailId;
	
	private JobDetails jobId;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeMetadataId that = (ResumeMetadataId) o;
        return Objects.equals(uploaderMailId, that.uploaderMailId) && Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploaderMailId, jobId);
    }
	

}
