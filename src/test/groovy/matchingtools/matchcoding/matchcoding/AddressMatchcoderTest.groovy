package matchingtools.matchcoding.matchcoding

import domain.Address

/**
 * Created by csperandio on 24/08/2016.
 */
class AddressMatchcoderTest extends GroovyTestCase {
    void testmatchcode_1() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = " 140 RUE DU RENDEZ VOUS BP80 "

        Address address = matchcoder.matchcode(addressLabel)

        assertNotNull(address)
        assertEquals(140, address.number)
        assertEquals("RUE", address.way)
        assertEquals("RENDEZ VOUS", address.name)
        assertEquals(80, address.postBox)
        assertEquals(0, address.roadNumber)
    }

    void testmatchcode_2() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = " 80BIS AVENUE DE SAINT MANDE C S 150 "

        Address address = matchcoder.matchcode(addressLabel)

        assertNotNull(address)
        assertEquals(80, address.number)
        assertEquals("AV", address.way)
        assertEquals("SAINT MANDE", address.name)
        assertEquals(150, address.postBox)
        assertEquals(0, address.roadNumber)
    }

    void testmatchcode_3() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = " BP 180 140 A 160 ZONE INDUSTRIELLE DE LA RAPEE "

        Address address = matchcoder.matchcode(addressLabel)

        assertNotNull(address)
        assertEquals(140, address.number)
        assertEquals("ZI", address.way)
        assertEquals("RAPEE", address.name)
        assertEquals(180, address.postBox)
        assertEquals(0, address.roadNumber)
    }

    void testmatchcode_4() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = " Z I RN 20 "

        Address address = matchcoder.matchcode(addressLabel)

        assertNotNull(address)
        assertEquals(0, address.number)
        assertEquals("ZI", address.way)
        assertEquals("", address.name)
        assertEquals(0, address.postBox)
        assertEquals(20, address.roadNumber)
    }

    void testmatchcode_5() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = " ROUTE N 20 "

        Address address = matchcoder.matchcode(addressLabel)

        assertNotNull(address)
        assertEquals(0, address.number)
        assertEquals("", address.way)
        assertEquals("", address.name)
        assertEquals(0, address.postBox)
        assertEquals(20, address.roadNumber)
    }

    void testmatchcode_6() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = " NO WAY "

        Address address = matchcoder.matchcode(addressLabel)

        assertNotNull(address)
        assertEquals(0, address.number)
        assertEquals("", address.way)
        assertEquals("NO WAY", address.name)
        assertEquals(0, address.postBox)
        assertEquals(0, address.roadNumber)
    }

    void testmatchcode_7() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = "  "

        Address address = matchcoder.matchcode(addressLabel)

        assertNull(address)
    }

    void testmatchcode_8() {
        def matchcoder = new AddressMatchcoder()
        def addressLabel = ""

        Address address = matchcoder.matchcode(addressLabel)

        assertNull(address)
    }


}
