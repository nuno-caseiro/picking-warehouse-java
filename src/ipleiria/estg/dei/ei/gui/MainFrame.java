package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;

import javax.swing.*;
import java.awt.*;



public class MainFrame extends JFrame implements GAListener {

    private static final long serialVersionUID = 1L;

    private MenuBarHorizontal menuBarHorizontal;
    private ToolBarVertical toolBarVertical;
    private ToolBarHorizontal toolBarHorizontal;
    private GeneticAlgorithmPanel gaPanel;
    private PanelProblemData problemData;
    private PanelSimulation simulationPanel;
    private PanelGeneticAlgorithmHistory gaHistoryPanel;
    private ExperimentsPanel experimentsPanel;
    private JPanel simulationGlobalPanel;
    private boolean mainPageShow;
    private boolean gaPanelShow;
    private boolean simulationPanelShow;
    private boolean gaHistoryPanelShow;
    private boolean experimentsPanelShow;

    private MainPagePanel mainPagePanel;
    private JPanel mainPanel;


    public MainFrame() throws HeadlessException {
        try{
           jbInit();
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    public void jbInit() throws Exception{
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("PICKING");
        this.setPreferredSize(new Dimension(1024, 820));

        gaPanel = new GeneticAlgorithmPanel(this);
        problemData = new PanelProblemData();
        mainPagePanel = new MainPagePanel();
        gaHistoryPanel = new PanelGeneticAlgorithmHistory();
        experimentsPanel= new ExperimentsPanel();

        this.menuBarHorizontal = new MenuBarHorizontal();
        this.toolBarVertical = new ToolBarVertical();
        this.toolBarHorizontal= new ToolBarHorizontal();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolBarHorizontal,BorderLayout.NORTH);
        mainPanel.add(toolBarVertical,BorderLayout.WEST);
        mainPanel.add(mainPagePanel,BorderLayout.CENTER);
        this.setJMenuBar(menuBarHorizontal);
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
        pack();

        simulationPanel = new PanelSimulation(this.mainPagePanel.getWidth(), this.mainPagePanel.getHeight());
    }

    public void manageButtons(boolean layout, boolean picks,boolean runSearch, boolean runGA, boolean stopRunGA, boolean runEnvironment) {
        this.menuBarHorizontal.manageButtons(layout,picks,runSearch,runGA,stopRunGA,runEnvironment);
        this.toolBarHorizontal.manageButtons(layout,picks,runSearch,runGA,stopRunGA,runEnvironment);
    }

    public void changeShowVar(int panelNumber, boolean show){
        switch (panelNumber){
            case 0:
                this.mainPageShow=show;
                this.gaPanelShow=false;
                this.simulationPanelShow=false;
                this.gaHistoryPanelShow=false;
                this.experimentsPanelShow=false;
                break; 
            case 1:
                this.mainPageShow=false;
                this.gaPanelShow=false;
                this.simulationPanelShow=false;
                this.gaHistoryPanelShow=false;
                this.experimentsPanelShow=show;
                break;
            case 2:
                this.mainPageShow=false;
                this.gaPanelShow=show;
                this.simulationPanelShow=false;
                this.gaHistoryPanelShow=false;
                this.experimentsPanelShow=false;
                break;
            case 3:
                this.mainPageShow=false;
                this.gaPanelShow=false;
                this.simulationPanelShow=show;
                this.gaHistoryPanelShow=false;
                this.experimentsPanelShow=false;
                break;
            case 4:
                this.mainPageShow=false;
                this.gaPanelShow=false;
                this.simulationPanelShow=false;
                this.gaHistoryPanelShow=show;
                this.experimentsPanelShow=false;
                break;
            default:
                show= false;
        }
    }

    public void showPanel(int panelNumber){
        boolean show;
        JComponent panel = new JPanel();

        switch (panelNumber){
            case 0:
                show=this.mainPageShow;
                panel=this.mainPagePanel;
                break;
            case 1:
                show=this.experimentsPanelShow;
                panel=this.experimentsPanel;
                break;
            case 2:
                show=this.gaPanelShow;
                panel=this.gaPanel;
                break;
            case 3:
                show=this.simulationPanelShow;
                panel=this.simulationPanel;
                break;
            case 4:
                show=this.gaHistoryPanelShow;
                panel= this.gaHistoryPanel;
                break;
            default:
                show= false;
        }

        show= !show;
        changeShowVar(panelNumber,show);

        if(show){
            for (Component component : this.mainPanel.getComponents()) {
                if(!component.equals(toolBarVertical) && !component.equals(toolBarHorizontal)){
                    this.mainPanel.remove(component);
                }
            }
            this.mainPanel.add(panel,BorderLayout.CENTER);
            this.validate();
            this.pack();
            this.repaint();
        }else{
            for (Component component : this.mainPanel.getComponents()) {
                if(!component.equals(toolBarVertical) && !component.equals(toolBarHorizontal)){
                    this.mainPanel.remove(component);
                }
            }
            this.mainPanel.add(this.mainPagePanel);
            this.revalidate();
            this.pack();
            this.repaint();
        }
    }

    public void setBestIndividualPanelText(String bestIndividualPanelText) {
        gaPanel.setBestIndividualPanel(bestIndividualPanelText);
    }

    public boolean isGaPanelShow() {
        return gaPanelShow;
    }

    public boolean isSimulationPanelShow() {
        return simulationPanelShow;
    }

    public MenuBarHorizontal getMenuBarHorizontal() {
        return menuBarHorizontal;
    }

    public PanelSimulation getSimulationPanel() {
        return simulationPanel;
    }

    public ToolBarVertical getToolBarVertical() {
        return toolBarVertical;
    }

    public GeneticAlgorithmPanel getGaPanel() {
        return gaPanel;
    }

    public ToolBarHorizontal getToolBarHorizontal() {
        return toolBarHorizontal;
    }

    public PanelGeneticAlgorithmHistory getGaHistoryPanel() {
        return gaHistoryPanel;
    }

    public ExperimentsPanel getExperimentsPanel() {
        return experimentsPanel;
    }

    @Override
    public void generationEnded(GeneticAlgorithm e) {
        this.setBestIndividualPanelText("Best solution\n"+e.getBestInRun().toString());
        this.getGaPanel().setValuesOfGenerationEnded(e);
    }

    @Override
    public void runEnded(GeneticAlgorithm e) {

    }

    @Override
    public void experimentEnded() {

    }
}


