package com.gh.extractor.service;

import com.amazonaws.services.location.model.Place;

public interface AddressLookupService {

    Place computeAddress(String fullAddress);
}
