package matchingtools.matchcoding.matchcoding

import java.util.stream.Collectors

/**
 * Created by batman on 11/09/2016.
 */
class KeyHelper {
    static private List<String> INVALID_KEY_WORDS = ["LE", "LES", "LA", "DE", "DU", "DES", "POUR", "CHEZ",
                                                     "SA", "SARL", "EURL", "SCI", "SOCIETE", "GMBH", "SERVICE", "SERVICES",
                                                     "INTERNATIONALE", "INTERNATIONAL", "FRANCE" ]


    static def addAddressKeyWords(String addressName, outputFields) {
        if (addressName) {
            def words = addressName.split()
            def lastWord = words[-1]

            String keyValue
            if (lastWord.size() >= 3) {
                keyValue = lastWord[0..2]
            } else {
                keyValue = lastWord
            }

            outputFields << "KAD/$keyValue"
        }
    }

    static def addNameKeyWords(String name, outputFields) {
        if (name) {
            def words = name.tokenize()
            def keys = words.stream()
                            .filter { it.length() > 1 && !INVALID_KEY_WORDS.contains(it) }
                            .map { w -> w.length() > 3 ? w[0..2] : w }
                            .collect(Collectors.toList())

            keys.each { outputFields << "KNA/$it" }
        }
    }

}
