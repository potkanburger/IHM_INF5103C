package com.esiee.mbdaihm;

import com.esiee.mbdaihm.dataaccess.geojson.NEGeoJsonDecoder;
import com.esiee.mbdaihm.dataaccess.geojson.RawCountry;
import com.esiee.mbdaihm.dataaccess.wdi.RawWDIData;
import com.esiee.mbdaihm.dataaccess.wdi.WDIDataDecoder;
import com.esiee.mbdaihm.dataaccess.wdi.WDIIndicatorsDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.Polygon;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import com.esiee.mbdaihm.view.Draw;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.swing.SwingUtilities;

/**
 * Application entry point
 *
 * @author Nicolas M.
 */
public class Launch
{
    private static final String COUNTRIES_FILE = "./data/ne_50m_admin_0_countries.json";

    private static final String WDI_FOLDER = "./data/WDI";

    private static void populateCountries()
    {
        List<RawCountry> rawData = NEGeoJsonDecoder.parseFile(new File(COUNTRIES_FILE));

        System.err.println("Parsed " + rawData.size() + " countries.");
        List<Country> countries = NEGeoJsonDecoder.convert(rawData);
        System.err.println("Converted " + countries.size() + " countries.");

        DataManager.INSTANCE.setCountries(countries);
    }

    private static void populatesIndicators()
    {
        List<Indicator> indicators = WDIIndicatorsDecoder.decode(WDI_FOLDER);

        System.err.println("Parsed " + indicators.size() + " indicators.");

        WDIIndicatorsDecoder.categoriseIndicators(indicators);
    }
    
    private static void test(String country, String value, String indicatorCode){
        List<RawWDIData> data = WDIDataDecoder.decode(WDI_FOLDER, indicatorCode);
        /*for (RawWDIData rawWDIData : data) {
            if(rawWDIData.countryCode.equals(country)){
                System.out.println(rawWDIData.getValueForYear(value));
            }
        }*/
        Optional<RawWDIData> findFirst = data.stream().filter(rawWDIData -> rawWDIData.countryCode.equals(country)).findFirst();
        if(findFirst.isPresent()){
             System.out.println(findFirst.get().getValueForYear(value));
        }
    }
    
    private static void testMax(String value, String indicatorCode){
        List<RawWDIData> data = WDIDataDecoder.decode(WDI_FOLDER, indicatorCode);
        
        double valMax = 0.0;
        double tmp;
        String res = "";
        
        /*for (RawWDIData rawWDIData : data) {
            tmp = rawWDIData.getValueForYear(value);
            if(tmp>valMax){
                valMax = tmp;
                res = rawWDIData.countryCode;
            }
        }*/
        Comparator<RawWDIData> comp = new Comparator<RawWDIData>() {
            @Override
            public int compare(RawWDIData o1, RawWDIData o2) {
                if(o1.getValueForYear(value)<o2.getValueForYear(value)){
                    return -1;
                }
                else{
                    return 1;
                }
                        
            }
        };
        Optional<RawWDIData> max = data.stream().max(comp);
         
        if(max.isPresent()){
             System.out.println(max.get().countryCode);
        }
    }

    /**
     * Application entry point.
     *
     * @param args no parameter used
     */
    public static void main(String[] args)
    {
        populateCountries();
        populatesIndicators();
        System.out.println("Q1:");
        //test("ITA", "1993" , "SP.DYN.LE00.FE.IN");
        System.out.println("Q2:");
        //testMax("2014", "EN.POP.DNST"); 
        
    }
}
