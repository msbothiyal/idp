package com.gh.extractor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Name {
    private String lastName;
    private String firstName;
}
