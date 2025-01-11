package com.gh.extractor.repository;

import com.gh.extractor.entity.DocRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRecordRepository extends JpaRepository<DocRecord, Long> {
}