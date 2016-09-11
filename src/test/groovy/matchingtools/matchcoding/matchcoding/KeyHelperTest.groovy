package matchingtools.matchcoding.matchcoding

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by batman on 11/09/2016.
 */
public class KeyHelperTest {
    @Test
    public void addAddressKeyWords() throws Exception {
        def keys = []
        KeyHelper.addAddressKeyWords("RENDEZ VOUS", keys)
        assert keys == ["KAD/VOU"]
    }

    @Test
    public void addNameKeyWords() throws Exception {
        def keys = []
        KeyHelper.addNameKeyWords("POUR LE TOTAL STATION GO SA SARL", keys)
        assert ["KNA/TOT", "KNA/STA", "KNA/GO"] == keys
    }

}