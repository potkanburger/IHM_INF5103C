/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

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
import java.util.List;

/**
 *
 * @author Sébastien Hu
 * @author florian
 */
public class Draw {
    private final JFrame frame;

    private final Draw.DrawingPanel panel;
    private static List<Country> cousties;
    
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
            
            GeneralPath gp = new GeneralPath();
            for(Country c : cousties){
                List<Polygon> poly = c.getGeometry().getPolygons();
                for(Polygon p : poly){
                    for(int i=0;i<p.points.length;i++){
                        if(i!=0){
                           gp.lineTo(p.points[i].lon, p.points[i].lat);
                        }else{
                           gp.moveTo(p.points[i].lon, p.points[i].lat);
                        }
                    }
                    gp.closePath();
                    g2d.draw(gp);
                }
            }
            
        }
    }
    
}

    

    

    