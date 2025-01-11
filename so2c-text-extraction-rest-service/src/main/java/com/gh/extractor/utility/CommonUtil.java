package com.gh.extractor.utility;

import com.amazonaws.services.textract.model.Query;
import com.gh.extractor.enums.IAliasName;
import com.gh.extractor.model.Name;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class CommonUtil {

    public static Name computeName(String fullName) {

        if (!StringUtils.hasLength(fullName)) {
            return Name.builder().build();
        }
        String[] nameSplit;
        String lastName;
        String firstName = null;
        if (fullName.contains(",")) {
            nameSplit = fullName.split("[,]+");
            lastName = nameSplit[0].trim();
            firstName = nameSplit[1].trim();
        } else if (fullName.contains(" ")) {
            nameSplit = fullName.split("[\\s]+");
            lastName = nameSplit[nameSplit.length - 1].trim();
            firstName = Arrays.stream(nameSplit).limit(nameSplit.length - 1).collect(Collectors.joining(" "));
        } else {
            lastName = fullName;
        }
        return Name.builder().lastName(lastName).firstName(firstName).build();
    }
    public static List<Query> getTextractQueries(IAliasName[] iAliasNames) {
        final List<Query> queries = new ArrayList<>();
        for (var value : iAliasNames) {
            queries.add(new Query().withText(value.getQuery()).withAlias(value.getName()));
        }
        return queries;
    }
}
