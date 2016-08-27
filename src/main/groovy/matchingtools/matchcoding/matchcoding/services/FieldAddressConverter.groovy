package matchingtools.matchcoding.matchcoding.services

import domain.Address

/**
 * Created by csperandio on 24/08/2016.
 */
class FieldAddressConverter implements MatchcodeConverter<Address> {
    @Override
    String convertToString(Address address) {
        "ADR/${address.number}/${address.way}/${address.name}/${address.postBox}/${address.roadNumber}"
    }
}
