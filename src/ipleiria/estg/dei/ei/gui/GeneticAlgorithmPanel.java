package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GeneticAlgorithmPanel extends JPanel {
    private JPanel bestIndividualPanel;
    private XYSeries seriesBestIndividual;
    private XYSeries seriesAverage;
    private MainFrame mainFrame;
    private PanelParameters panelParameters;
    private JPanel gaPanelParametersWest;
    public JTextArea textArea;

    public GeneticAlgorithmPanel(MainFrame mainFrame) {
        this.mainFrame= mainFrame;
        setLayout(new BorderLayout());

        gaPanelParametersWest = new JPanel(new BorderLayout());

        panelParameters = new PanelParameters(this.mainFrame);
        panelParameters.setBorder(new EmptyBorder(0,5,0,5));//top,left,bottom,right

        //Chart
        seriesBestIndividual = new XYSeries("Best");
        seriesAverage = new XYSeries("Average");

        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(seriesBestIndividual);
        dataSet.addSeries(seriesAverage);
        JFreeChart chart = ChartFactory.createXYLineChart("Evolution", "generation", "fitness", dataSet, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(new EmptyBorder(0,0,0,0));
        chartPanel.setPreferredSize(new Dimension(400,200));
        //----


        bestIndividualPanel= new JPanel(new BorderLayout());
        textArea= new JTextArea(6,5);
        JScrollPane jScrollPane= new JScrollPane(textArea);
        bestIndividualPanel.setBorder(new EmptyBorder(1,0,0,0));
        bestIndividualPanel.add(jScrollPane);

        gaPanelParametersWest.add(panelParameters,BorderLayout.NORTH);
        gaPanelParametersWest.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.GRAY));
        textArea.setText("Best Solution");
        this.add(gaPanelParametersWest,BorderLayout.WEST);
        this.add(bestIndividualPanel,BorderLayout.SOUTH);
        this.add(chartPanel,BorderLayout.CENTER);
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
        this.textArea.setText(bestIndividualPanelText);
    }

    public void setValuesOfGenerationEnded(GeneticAlgorithm e){
        this.seriesBestIndividual.add(e.getGenerationNr(), e.getBestInRun().getFitness());
        this.seriesAverage.add(e.getGenerationNr(), e.getAverageFitness());
    }

}
