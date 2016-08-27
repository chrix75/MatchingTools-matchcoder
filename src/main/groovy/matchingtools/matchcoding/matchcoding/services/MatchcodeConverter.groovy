package matchingtools.matchcoding.matchcoding.services
/**
 * Created by csperandio on 24/08/2016.
 */
interface MatchcodeConverter<T> {
    String convertToString(T matchcode)
}