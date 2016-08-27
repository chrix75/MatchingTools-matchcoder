package matchingtools.matchcoding.matchcoding.services

import domain.CompanyName
/**
 * Created by csperandio on 25/08/2016.
 */
class CompanyNameConverter implements MatchcodeConverter<CompanyName> {
    @Override
    String convertToString(CompanyName company) {
        "RS/${company.isService}/${company.wordsName.join(",")}"
    }
}
