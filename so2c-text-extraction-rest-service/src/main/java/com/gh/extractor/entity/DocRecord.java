package com.gh.extractor.entity;

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
@Table(name = "idp_doc_status")
public class DocRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;
    private String docType; //TODO need to change to enum Doctype
    @Enumerated(EnumType.STRING)
    private Status docStatus;
    private String docResponse;
    private String cdmResponse;
    private Long jobId;
    private String error;
    @CreationTimestamp
    private OffsetDateTime created;
    @UpdateTimestamp
    private OffsetDateTime updated;
}
