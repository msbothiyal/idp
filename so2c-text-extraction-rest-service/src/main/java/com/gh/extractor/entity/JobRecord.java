package com.gh.extractor.entity;

import com.guardant.so2c.ocr.textextractor.enums.JobType;
import com.guardant.so2c.ocr.textextractor.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;


@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "idp_job_queue")
public class JobRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String textractJobId;
    @Enumerated(EnumType.STRING)
    private Status jobStatus;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private String inboundMessage;
    private String cdmResponse;
    private String textractResponse;
    @Column(name = "s3_location")
    private String s3Location;
    private String error;
    @CreationTimestamp
    private OffsetDateTime created;
    @UpdateTimestamp
    private OffsetDateTime updated;

    public void setErrorRecord(String jobError){
        this.error = jobError;
        this.jobStatus = Status.FAILED;
    }
}
