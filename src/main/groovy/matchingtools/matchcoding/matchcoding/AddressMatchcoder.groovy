package matchingtools.matchcoding.matchcoding

import domain.Address
import matchingtools.matchcoding.matchcoding.services.MatchcodeConverter
import org.springframework.stereotype.Component

import java.util.regex.Pattern
/**
 * Created by csperandio on 24/08/2016.
 */
@Component
class AddressMatchcoder implements Matchcoder<Address> {

    MatchcodeConverter converter

    AddressMatchcoder(MatchcodeConverter<Address> converter) {
        this.converter = converter
    }

    static private List<String> ways = [" RUE ", " BOULEVARD ", " AVENUE ", " GRANDE RUE ", " GRAND RUE ", "GR",
                                        " ZONE INDUSTRIELLE ", " ZI ", " Z I ", " PLACE ", " ZONE IND ", " ZONE INDUS ",
                                        " COURS ", " CHEMIN ", " ZA ", " Z A ", " ZONE D ACTIVITE ", " ZONE ACTIVITE ",
                                        " ZONE D ACTIVITES ", " ZONE ACTIVITES ", " ALLEE ", " ALLEES ", " CHEMIN ",
                                        " LOTISSEMENT ", " LOT ", " SQUARE ", " SQ ", " ROUTE ", " LD ", " LIEU DIT ",
                                        " IMPASSE ", " PARC ", " RUELLE ", " PROMENADE ", " ZONE ARTISANALE ",
                                        " ZONE ARTISANAL ", " QUAI ", " VENELLE ", " CLOS ", " ROND POINT ",
                                        " PASSAGE ", " SENTE ", " RESIDENCE ", " ZAC ", " QUARTIER ", " AV ", " PL ",
                                        " MONTEE ", " TRAVERSE ", " ESPLANADE ", " CITE "] as List

    static private String BP_LABEL = /B(OITE)? ?P(POSTALE?)?/
    static private String CS_LABEL = /C ?S/
    static private Pattern BP_REGEXP = ~/\b($BP_LABEL|$CS_LABEL) ?(\d+)\b/

    static private String ROAD = /R(OUTE)?/
    static private String NAT = /N(ATIONALE?)?/
    static private String DEPT = /D(EPARTEMANTALE?)?/

    static private Pattern ROAD_REGEXP = ~/\b$ROAD ?($NAT|$DEPT) ?(\d+)/

    private List extractRoadNumber(String field) {
        def matcher = (field =~ ROAD_REGEXP)

        if (matcher) {
            def roadNumber = matcher[0][-1]
            [ roadNumber, field - matcher[0][0] ]

        } else { [ "0", field ] }
    }

    private List extractPostBox(String field) {
        def matcher = (field =~ BP_REGEXP)

        if (matcher) {
            def postBoxNumber = matcher[0][-1]
            [ postBoxNumber, field - matcher[0][0] ]

        } else { [ "0", field ] }
    }

    private List extractAddressParts(String field) {
        def postBoxAndRest = extractPostBox(field)
        def postBoxNumber = postBoxAndRest[0]
        def postBoxRest = postBoxAndRest[1]

        def roadAndRest = extractRoadNumber(postBoxRest)
        def roadNumber = roadAndRest[0]
        def address = roadAndRest[1]

        [roadNumber, postBoxNumber, address]
    }

    private List splitWay(String field) {
        def found = ways.find { field.startsWith(it) }
        if (found) {
            if (found.size() == field.size()) {
                return [found, ""] as String[]
            }
            else {
                return [found, field[found.size()..-1]] as List
            }
        }
    }

    Address matchcode(String field) {
        def addressParts = extractAddressParts(" $field ")
        def roadNumber = addressParts[0]
        def postBoxNumber = addressParts[1]
        def addrCandidate = addressParts[2]

        def limit = addrCandidate.size() - 1
        def i = 0
        while (i < limit) {
            List found = splitWay(addrCandidate[i..-1])

            if (found) {
                def data = [ addrCandidate[0..i] ] + found + postBoxNumber + roadNumber as String[]
                return data as Address
            }

            ++i
        }

        def addrTrimed = addrCandidate.trim()

        if (!addrTrimed && postBoxNumber == "0" && roadNumber == "0") { return null }

        new Address("", "", addrTrimed, postBoxNumber, roadNumber)
    }

    @Override
    String convertToString(Address matchcode) {
        converter.convertToString(matchcode)
    }
}
