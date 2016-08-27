package matchingtools.matchcoding

import matchingtools.matchcoding.matchcoding.AddressMatchcoder
import matchingtools.matchcoding.matchcoding.CompanyNameMatchcoder
import matchingtools.matchcoding.matchcoding.Matchcoder
import domain.Address
import domain.CompanyName
import matchingtools.matchcoding.matchcoding.services.CompanyNameConverter
import matchingtools.matchcoding.matchcoding.services.FieldAddressConverter
import matchingtools.matchcoding.matchcoding.services.MatchcodeConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by csperandio on 25/08/2016.
 */
@Configuration
class AppConfiguration {

    @Bean
    @Qualifier("addressConverter")
    MatchcodeConverter<Address> addressConverter() {
        new FieldAddressConverter()
    }

    @Bean
    @Qualifier("companyNameConverter")
    MatchcodeConverter<CompanyName> companyNameConverter() {
        new CompanyNameConverter()
    }

    @Bean
    @Qualifier("addressMatchcoder")
    Matchcoder<Address> addressMatchcoder(@Qualifier("addressConverter") converter) {
        new AddressMatchcoder(converter)
    }

    @Bean
    @Qualifier("companyNameMatchcoder")
    Matchcoder<CompanyName> companyNameMatchcoder(@Qualifier("companyNameConverter") converter) {
        new CompanyNameMatchcoder(converter)
    }
}
