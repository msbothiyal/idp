package com.gh.extractor.repository;

import com.gh.extractor.entity.JobRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRecordRepository extends JpaRepository<JobRecord, Long> {

    JobRecord findByTextractJobId(String jobId);
}