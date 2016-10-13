package com.esiee.mbdaihm.dataaccess.wdi;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author N. Mortier
 */
public class WDIDataDecoder
{
    private static final String FILE_NAME = "WDI_Data-utf8.csv";

    public static List<RawWDIData> decode(String directoryPath, String indicatorCode)
    {
        long in = System.nanoTime();

        Instant entryInstant = Instant.now();

        int inCount = 0;
        int countryNoMatchCount = 0;
        int noDataCount = 0;
        int dataSetCount = 0;

        //We may need to return the list instead.
        List<RawWDIData> result = new ArrayList<>();
        CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();

        CsvMapper mapper = new CsvMapper();

        File file = new File(directoryPath + "/" + FILE_NAME);

        MappingIterator<RawWDIData> readValues;

        try
        {
            readValues = mapper.reader(RawWDIData.class).
                    with(headerSchema).
                    without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).
                    readValues(file);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return result;
        }

        while (readValues != null && readValues.hasNext())
        {
            RawWDIData data = readValues.next();
            if (data.indicatorCode.equals(indicatorCode))
            {
                result.add(data);
                inCount++;

                // Safety check if we have the country code in our data
                Country country = DataManager.INSTANCE.getCountryByCode(data.countryCode);

                if (country == null)
                {
                    countryNoMatchCount++;
                    continue;
                }

                if (data.value1993 == null || data.value1993.equals(""))
                {
                    noDataCount++;
                    DataManager.INSTANCE.getCountryByCode(data.countryCode).setIndicatorValue(Double.NaN);
                }
                else
                {
                    dataSetCount++;
                    DataManager.INSTANCE.getCountryByCode(data.countryCode).
                            setIndicatorValue(Double.parseDouble(data.value1993));
                }
            }

        }

        long out = System.nanoTime();
/*
        System.err.println("Data decoding time = " + (out - in) / (1000 * 1000) + " ms.");

        System.err.println("#### Data parse stats ####");
        System.err.println("inCount = " + inCount);
        System.err.println("out = " + countryNoMatchCount);
        System.err.println("noDataCount = " + noDataCount);
        System.err.println("dataSetCount = " + dataSetCount);
        System.err.println("####");
    */    
        return result;
    }
}
