/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.dataaccess.geojson.NEGeoJsonDecoder;
import com.esiee.mbdaihm.dataaccess.geojson.RawCountry;
import com.esiee.mbdaihm.dataaccess.wdi.WDIIndicatorsDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import java.io.File;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sébastien Hu
 */
public class MainFrame extends javax.swing.JFrame {
    public static Draw tp;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }
    
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu_region = new javax.swing.JMenu();
        region_europe = new javax.swing.JCheckBoxMenuItem();
        region_asie = new javax.swing.JCheckBoxMenuItem();
        region_afrique = new javax.swing.JCheckBoxMenuItem();
        region_nord_amerique = new javax.swing.JCheckBoxMenuItem();
        region_sud_amerique = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        menu_donnees = new javax.swing.JMenu();
        menu_periode = new javax.swing.JMenu();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();
        menu_type = new javax.swing.JMenu();
        menu_sauvegarder = new javax.swing.JMenu();
        menu_charger = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menu_region.setText("Région");

        region_europe.setText("Europe");
        region_europe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                region_europeActionPerformed(evt);
            }
        });
        menu_region.add(region_europe);

        region_asie.setText("Asie");
        region_asie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                region_asieActionPerformed(evt);
            }
        });
        menu_region.add(region_asie);

        region_afrique.setText("Afrique");
        menu_region.add(region_afrique);

        region_nord_amerique.setText("Amérique du Nord");
        menu_region.add(region_nord_amerique);

        region_sud_amerique.setText("Amérique du Sud");
        menu_region.add(region_sud_amerique);

        jCheckBoxMenuItem1.setText("Océanie");
        menu_region.add(jCheckBoxMenuItem1);

        jMenuBar1.add(menu_region);

        menu_donnees.setText("Données");
        jMenuBar1.add(menu_donnees);

        menu_periode.setText("Période");

        buttonGroup1.add(jRadioButtonMenuItem3);
        jRadioButtonMenuItem3.setSelected(true);
        jRadioButtonMenuItem3.setText("jRadioButtonMenuItem3");
        menu_periode.add(jRadioButtonMenuItem3);

        buttonGroup1.add(jRadioButtonMenuItem4);
        jRadioButtonMenuItem4.setText("jRadioButtonMenuItem4");
        menu_periode.add(jRadioButtonMenuItem4);

        jMenuBar1.add(menu_periode);

        menu_type.setText("Type");
        jMenuBar1.add(menu_type);

        menu_sauvegarder.setText("Sauvegarder");
        jMenuBar1.add(menu_sauvegarder);

        menu_charger.setText("Charger");
        jMenuBar1.add(menu_charger);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void region_europeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_region_europeActionPerformed
        SwingUtilities.invokeLater(()-> tp.display());;
    }//GEN-LAST:event_region_europeActionPerformed

    private void region_asieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_region_asieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_region_asieActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        populateCountries();
        populatesIndicators();
        tp = new Draw();
        tp.LayoutComponents();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JMenu menu_charger;
    private javax.swing.JMenu menu_donnees;
    private javax.swing.JMenu menu_periode;
    private javax.swing.JMenu menu_region;
    private javax.swing.JMenu menu_sauvegarder;
    private javax.swing.JMenu menu_type;
    private javax.swing.JCheckBoxMenuItem region_afrique;
    private javax.swing.JCheckBoxMenuItem region_asie;
    private javax.swing.JCheckBoxMenuItem region_europe;
    private javax.swing.JCheckBoxMenuItem region_nord_amerique;
    private javax.swing.JCheckBoxMenuItem region_sud_amerique;
    // End of variables declaration//GEN-END:variables
}
