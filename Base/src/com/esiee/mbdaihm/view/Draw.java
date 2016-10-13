/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.dataaccess.wdi.RawWDIData;
import com.esiee.mbdaihm.dataaccess.wdi.WDIDataDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.Polygon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sébastien Hu
 * @author florian
 */
public class Draw {
    private final JFrame frame;

    private final Draw.DrawingPanel panel;
    private static List<Country> cousties;
    private static final String WDI_FOLDER = "./data/WDI";
    
    
    public Draw(){
        frame = new JFrame("Melina");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new Draw.DrawingPanel();
        cousties = DataManager.INSTANCE.getCountries();
    }
    
    public void LayoutComponents()
    {
        frame.setLayout(new BorderLayout());
        frame.add(panel);
    }
    
    public void display()
    {
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        frame.setBounds(0, 0, width, height);
        frame.setVisible(true);
    }
    
    private static String testMax(String value, String indicatorCode){
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
            if(!Double.isNaN(max.get().getValueForYear(value))){
                return max.get().countryCode;
            }
            else{
                return "";
            }
        }
        else{
            return "";
        }
    }
    
    private static class DrawingPanel extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g;
            Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int height = (int)dimension.getHeight();
            int width  = (int)dimension.getWidth();
            AffineTransform atr = g2d.getTransform();
            atr.translate(width/2, height/2);
            atr.scale(1, -1);
            g2d.setTransform(atr);
            
            
            g2d.setPaint(Color.BLACK);
            g2d.drawRect(-5, 5, 10, 10);
            
            g2d.drawRect(50, 50, 50, 50);
            g2d.setPaint(Color.BLUE);
            g2d.drawRect(-50, -50, 50, 50);
            String cd = testMax("2020", "DT.CUR.SWFR.ZS");
            //
            //"IQ.SCI.OVRL" -> Afghanistan
            //"DC.DAC.NORL.CD" -> Tanzanie
            
            Country kinderCountry;
            
            if(cd.equals("")){
                kinderCountry = new Country();
            }else{
                kinderCountry = DataManager.INSTANCE.getCountryByCode(cd);
            }
            
            /*if (kinderCountry == null){
                kinderCountry = DataManager.INSTANCE.getCountryByCode("FRA");
                System.out.println(testMax("1993", "IQ.SCI.OVRL"));
            }*/
            //cd = "FRA";
            
            GeneralPath gp = new GeneralPath();
            GeneralPath selected = new GeneralPath();
            
            for(Country c : cousties){
                List<Polygon> poly = c.getGeometry().getPolygons();
                //c.getSubRegion().contains("Europe")
                if(!cd.equals("") && c.getIsoCode().equals(kinderCountry.getIsoCode())){
                    System.out.println(c.getName());
                    for(Polygon p : poly){
                        for(int i=0;i<p.points.length;i++){
                            if(i!=0){
                                selected.lineTo(p.points[i].lon, p.points[i].lat);
                            }else{
                                selected.moveTo(p.points[i].lon, p.points[i].lat);
                            }
                        }
                        selected.closePath();
                    }
                }else{
                    for(Polygon p : poly){
                        for(int i=0;i<p.points.length;i++){
                            if(i!=0){
                               gp.lineTo(p.points[i].lon, p.points[i].lat);
                            }else{
                               gp.moveTo(p.points[i].lon, p.points[i].lat);
                            }
                        }
                        gp.closePath();
                    }
                }
                
            }
            
            /*g2d.setPaint(Color.BLUE);
            g2d.draw(gp);*/
            g2d.setPaint(Color.RED);
            g2d.draw(selected);
            
        }
    }
    
}

    

    

    