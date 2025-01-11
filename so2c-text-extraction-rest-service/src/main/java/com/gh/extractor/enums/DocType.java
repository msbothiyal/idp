package com.gh.extractor.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/* Document type for file to be processed
 * @author msbothiyal
 * @date 26/07/21,2:58 PM
 */
public enum DocType {

    LUNAR_2("LUNAR2 TRF"),
    PATIENT_FACE_SHEET("PATIENT FACE SHEET"),
    INSURANCE_CARD("INSURANCE CARD"),

    OTHER("OTHER");

    @JsonValue
    public final String docName;

    DocType(String name) {
        this.docName = name;
    }

    public static DocType valueOfLabel(String label) {
        for (DocType e : values()) {
            if (e.docName.equals(label)) {
                return e;
            }
        }
        return null;
    }
    public static String docTypeValuesAsStrList() {
        return Arrays.stream(values()).map(dt -> dt.docName).collect(Collectors.joining(", "));
    }
}
