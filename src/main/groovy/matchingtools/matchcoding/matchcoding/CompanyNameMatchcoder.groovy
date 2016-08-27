package matchingtools.matchcoding.matchcoding

import domain.CompanyName
import matchingtools.matchcoding.matchcoding.services.MatchcodeConverter

/**
 * Created by csperandio on 25/08/2016.
 */
class CompanyNameMatchcoder implements Matchcoder<CompanyName> {

    MatchcodeConverter converter

    CompanyNameMatchcoder(MatchcodeConverter<CompanyName> converter) { this.converter = converter}

    CompanyName matchcode(String field) {
        new CompanyName(field)
    }

    @Override
    String convertToString(CompanyName matchcode) {
        converter.convertToString(matchcode)
    }
}
