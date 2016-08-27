package matchingtools.matchcoding.matchcoding

/**
 * Created by csperandio on 25/08/2016.
 */
interface Matchcoder<T> {
    T matchcode(String field)
    String convertToString(T matchcode)
}
