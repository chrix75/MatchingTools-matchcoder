package matchingtools.matchcoding

import matchingtools.matchcoding.matchcoding.KeyHelper
import matchingtools.matchcoding.matchcoding.Matchcoder
import domain.Address
import domain.CompanyName
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

import java.util.stream.Collectors

/**
 * Created by csperandio on 24/08/2016.
 */
@SpringBootApplication
@ComponentScan(basePackages = "matchingtools.matchcoding")
class Main implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(Main.class)

    @Autowired
    @Qualifier("addressMatchcoder")
    Matchcoder<Address> addressMatchcoder

    @Autowired
    @Qualifier("companyNameMatchcoder")
    Matchcoder<CompanyName> companyNameMatchcoder

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    void run(String... args) throws Exception {
        def cl = new CliBuilder(usage: 'matchcoder -a "addrFieldNumbner1",..., "addrFielNumberN"')
        cl.a(argName: 'addressList', args: 1, longOpt: 'address', 'Address field number', required: true)
        cl.d(argName: 'delimiter', args: 1, longOpt: 'delimiter', 'Field delimiter')
        cl.n(argName: 'companyName', args: 1, longOpt: 'companyName', 'Company name field numbers', required: true)

        def opt = cl.parse(args)

        if (!opt) {
            println cl.usage
            System.exit(1)
        }

        logger.debug("Arguments: ${opt.arguments()}")

        def (outputFile, inputFile) = opt.arguments().collect { new File(it) }
        def fieldDelimiter = opt.d ?: ';'
        def addressFieldIndices = opt.a.split(',').collect { Integer.parseInt(it) - 1 }
        def companyNameFieldIndices = opt.n.split(',').collect { Integer.parseInt(it) - 1 }

        logger.info("Input filename: $inputFile")
        logger.info("Output filename: $outputFile")
        logger.info("Field delinmiter: [$fieldDelimiter]")
        logger.info("Address fields: $addressFieldIndices")
        logger.info("Company name fields: $companyNameFieldIndices")

        //def addressMatchcoder = new AddressMatchcoder()
        def outputWriter = outputFile.newWriter("UTF-8")

        logger.info("Reading file $inputFile")

        inputFile.eachLine { line ->
            def fields = line.split(fieldDelimiter, -1)
            def outputFields = fields as List

            matchcodeAddressFields(addressMatchcoder, fields, addressFieldIndices, outputFields)
            matchcodeNameFields(companyNameMatchcoder, fields, companyNameFieldIndices, outputFields)

            outputWriter.write("${outputFields.join(";")}\n")
        }

        logger.info("Processing file $inputFile is over")

        outputWriter.close()
    }

    def matchcodeAddressFields(Matchcoder<Address> matchcoder, fields, addressFieldIndices, outputFields) {
        def addressFields = addressFieldIndices.findAll { it >= 0 && it < fields.length }
                                               .collect { fields[it] }
                                               .findAll { !it.empty }

        addressFields.each { field ->
            def matchcodedAddress = matchcoder.matchcode("$field")
            if (matchcodedAddress) {
                outputFields << matchcoder.convertToString(matchcodedAddress)
            }

            KeyHelper.addAddressKeyWords(matchcodedAddress.name, outputFields)
        }
    }

    def matchcodeNameFields(Matchcoder<CompanyName> matchcoder, fields, fieldIndices, outputFields) {
        def nameFields = fieldIndices.findAll { it >= 0 && it < fields.length }
                                               .collect { fields[it] }
                                               .findAll { !it.empty }

        nameFields.each { field ->
            def matchcodedName = matchcoder.matchcode("$field")
            if (matchcodedName) {
                outputFields << matchcoder.convertToString(matchcodedName)
            }

            if (!matchcodedName.isService) {
                KeyHelper.addNameKeyWords(matchcodedName.wordsName, outputFields)
            }
        }
    }
}
