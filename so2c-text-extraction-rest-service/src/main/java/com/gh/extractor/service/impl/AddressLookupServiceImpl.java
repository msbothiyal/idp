package com.gh.extractor.service.impl;

import com.amazonaws.services.location.AmazonLocation;
import com.amazonaws.services.location.model.Place;
import com.amazonaws.services.location.model.SearchPlaceIndexForTextRequest;
import com.amazonaws.services.location.model.SearchPlaceIndexForTextResult;
import com.gh.extractor.service.AddressLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressLookupServiceImpl implements AddressLookupService {

    private final AmazonLocation amazonLocation;

    @Override
    public Place computeAddress(String fullAddress) {
        SearchPlaceIndexForTextRequest searchPlaceIndexForTextRequest =
                new SearchPlaceIndexForTextRequest().withText(fullAddress).withIndexName("explore.place");

        SearchPlaceIndexForTextResult searchPlaceIndexForTextResult =
                amazonLocation.searchPlaceIndexForText(searchPlaceIndexForTextRequest);

        if (searchPlaceIndexForTextResult.getResults() != null && !searchPlaceIndexForTextResult.getResults().isEmpty()) {

            Place place = searchPlaceIndexForTextResult.getResults().get(0).getPlace();

            if (place != null) {
                return place;
            }
        }
        return null;
    }
}
