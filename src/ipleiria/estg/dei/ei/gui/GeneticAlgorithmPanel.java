package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class GeneticAlgorithmPanel extends JPanel {
    private PanelTextArea bestIndividualPanel;
    private XYSeries seriesBestIndividual;
    private XYSeries seriesAverage;
    private MainFrame mainFrame;
    private PanelParameters panelParameters;
    private JPanel gaPanelParametersNorth;

    public GeneticAlgorithmPanel(MainFrame mainFrame) {
        this.mainFrame= mainFrame;
        setLayout(new BorderLayout());

        panelParameters = new PanelParameters(this.mainFrame);
        gaPanelParametersNorth= new JPanel(new BorderLayout());

        //Chart
        seriesBestIndividual = new XYSeries("Best");
        seriesAverage = new XYSeries("Average");

        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(seriesBestIndividual);
        dataSet.addSeries(seriesAverage);
        JFreeChart chart = ChartFactory.createXYLineChart("Evolution", "generation", "fitness", dataSet, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createEmptyBorder(1,1,1,1)));
        chartPanel.setPreferredSize(new Dimension(400,200));
        //----

        bestIndividualPanel= new PanelTextArea("Best solution: ", 15,10);

        gaPanelParametersNorth.add(panelParameters,BorderLayout.NORTH);
        gaPanelParametersNorth.add(bestIndividualPanel,BorderLayout.CENTER);
        this.add(gaPanelParametersNorth, BorderLayout.WEST);
        this.add(chartPanel,BorderLayout.CENTER);
    }

    public PanelTextArea getBestIndividualPanel() {
        return bestIndividualPanel;
    }

    public XYSeries getSeriesBestIndividual() {
        return seriesBestIndividual;
    }

    public XYSeries getSeriesAverage() {
        return seriesAverage;
    }

    public PanelParameters getPanelParameters() {
        return panelParameters;
    }

    public void setBestIndividualPanel(String bestIndividualPanelText) {
        this.bestIndividualPanel.textArea.setText(bestIndividualPanelText);
    }

    public void setValuesOfGenerationEnded(GeneticAlgorithm e){
        this.seriesBestIndividual.add(e.getGenerationNr(), e.getBestInRun().getFitness());
        this.seriesAverage.add(e.getGenerationNr(), e.getAverageFitness());
    }

}
