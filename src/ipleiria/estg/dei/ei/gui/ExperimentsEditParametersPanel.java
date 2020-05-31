package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.IntegerTextField_KeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ExperimentsEditParametersPanel extends JPanel {

    private final ExperimentParametersPanel experimentParametersPanel;

    private HashMap<String,Object> availableParameters;

    private JButton add;
    private JButton remove;
    private JButton ok;
    private JButton cancel;

    private JLabel errors;

    private DefaultListModel<String> atualParametersModel;
    private JList<String> atualParametersList;

    private DefaultListModel<String> availableParametersModel;
    private JList<String> availableParametersList;

    private JScrollPane jScrollPaneOfAtualParam;
    private JScrollPane jScrollPaneOfAvailableParam;

    private JTextField gaProbabilityToAdd;
    private JTextField integerToAdd;
    private JTextField selectivePressureToAdd;

    private JPanel centerPanel = new JPanel(new GridBagLayout());
    private JPanel southPanel = new JPanel(new BorderLayout());

    private JComponent actualComponent;
    private JLabel activeParameter;
    private GridBagConstraints c = new GridBagConstraints();

    public ExperimentsEditParametersPanel(int panelId, List<String> actualParameters, HashMap<String,Object> availableParameters, ExperimentParametersPanel experimentsPanel) {
        setLayout(new GridBagLayout());


        init();

        this.availableParameters=availableParameters;
        this.experimentParametersPanel=experimentsPanel;
        for (String actualParameter : actualParameters) {
            atualParametersModel.addElement(actualParameter);
        }

        errors= new JLabel("");
        switch (panelId){
            case 1:
                break;
            case 2:
                switchHelper("Editing population sizes",1);
                break;
            case 3:
                switchHelper("Editing max generations",1);
                break;
            case 4:
                switchHelper("Editing selection methods",4);
                break;
            case 5:
                switchHelper("Editing tournament sizes",1);
                break;
            case 6:
                switchHelper("Editing selective pressure values",6);
                break;
            case 7:
                switchHelper("Editing recombination methods",7);
                break;
            case 8:
                switchHelper("Editing recombination probabilities",8);
                break;
            case 9:
                switchHelper("Editing mutation methods",9);
                break;
            case 10:
                switchHelper("Editing mutation probabilities",8);
                break;
            case 11:
                switchHelper("Editing time weights",1);
                break;
            case 12:
                switchHelper("Editing collision weights",1);
                break;
            case 13:
                switchHelper("Editing statistics ",10);
                break;
            case 14:
                switchHelper("Editing number of agents",1);
                break;
            case 15:
                switchHelper("Editing number of picks",1);
                break;
        }
        
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String atualItem= null;
                if(actualComponent.equals(integerToAdd)){
                    if(!integerToAdd.getText().trim().isBlank()){
                        atualItem=integerToAdd.getText();
                        //proteger valores
                    }
                }
                if(actualComponent.equals(jScrollPaneOfAvailableParam)){
                    if(availableParametersList.getSelectedValue()!=null){
                        atualItem=availableParametersList.getSelectedValue();
                    }
                }
                if(actualComponent.equals(selectivePressureToAdd)){
                    if(!selectivePressureToAdd.getText().trim().isBlank()){
                        atualItem=selectivePressureToAdd.getText();
                        //proteger valores
                    }
                }
                if(actualComponent.equals(gaProbabilityToAdd)){
                    if(!gaProbabilityToAdd.getText().trim().isBlank()){
                        atualItem=gaProbabilityToAdd.getText();
                        //proteger valores
                    }
                }

                if(!atualParametersModel.contains(atualItem) && atualItem!=null){
                    atualParametersModel.addElement(atualItem);
                }

                actualParameters.clear();
                for (int i = 0; i < atualParametersModel.size(); i++) {
                    actualParameters.add(atualParametersModel.get(i));
                }

                experimentParametersPanel.updateTextFieldParameters(panelId);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                  Object atualItem= "";
                if(atualParametersList.getSelectedValue()!=null){
                    atualItem=atualParametersList.getSelectedValue();
                }
                if(atualParametersModel.contains(atualItem.toString())){
                    atualParametersModel.removeElement(atualItem);
                }

                actualParameters.clear();
                for (int i = 0; i < atualParametersModel.size(); i++) {
                    actualParameters.add(atualParametersModel.get(i));
                }

                experimentParametersPanel.updateTextFieldParameters(panelId);

            }
        });

        JPanel addRemovePanel = new JPanel(new GridLayout(2,1));
        addRemovePanel.add(add);
        addRemovePanel.add(remove);

        JLabel labelAtualList = new JLabel("Atual Parameters");

        c.anchor= GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 0;
        c.insets= new Insets(0,0,2,0);
        this.add(activeParameter,c);
        c.insets= new Insets(0,0,0,0);
        c.gridx=0;
        c.gridy=1;
        this.add(labelAtualList,c);
        c.gridy=2;
        c.gridx=0;
        c.gridheight=4;
        c.ipadx=100;
        c.ipady=100;
        c.fill= GridBagConstraints.BOTH;
        jScrollPaneOfAtualParam.setMinimumSize(new Dimension(100,100));
        jScrollPaneOfAtualParam.setMaximumSize(new Dimension(100,100));
        this.add(jScrollPaneOfAtualParam,c);
        c.gridx=1;
        c.gridy=2;
        c.ipadx=0;
        c.fill= GridBagConstraints.BOTH;
        c.ipady=0;
        this.add(addRemovePanel,c);
        c.gridx=2;
        c.gridy=2;
        c.ipadx=100;
        c.ipady=100;
        c.gridheight=4;
        c.fill= GridBagConstraints.BOTH;
        actualComponent.setMinimumSize(new Dimension(100,100));
        actualComponent.setMaximumSize(new Dimension(100,100));
        availableParametersList.setMinimumSize(new Dimension(100,100));
        availableParametersList.setMaximumSize(new Dimension(100,100));
        this.add(actualComponent,c);

    }

    private void init(){
        activeParameter = new JLabel();

        add = new JButton("<");
        remove = new JButton(">");
        ok = new JButton("OK");
        cancel = new JButton("Cancel");

        atualParametersModel = new DefaultListModel<>();
        atualParametersList = new JList<String>(atualParametersModel);
        availableParametersModel= new DefaultListModel<String>();
        availableParametersList= new JList<>();

        jScrollPaneOfAtualParam = new JScrollPane(atualParametersList);
        jScrollPaneOfAvailableParam = new JScrollPane(availableParametersList);

        centerPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());

        activeParameter = new JLabel();
    }

    private void switchHelper(String labelTitleText, int typeOfComponent) {
        activeParameter.setText(labelTitleText);
        switch (typeOfComponent) {
            case 1:
                integerToAdd = new JTextField("",7);
                integerToAdd.addKeyListener(new IntegerTextField_KeyAdapter(null));
                integerToAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                actualComponent = integerToAdd;
                break;
            case 4:
                availableParametersModel.addAll((Collection<? extends String>) availableParameters.get("Selection"));
                availableParametersList.setModel(availableParametersModel);
                actualComponent = jScrollPaneOfAvailableParam;
                break;
            case 6:
                selectivePressureToAdd = new JTextField("",7);
                //proteger para valores <1 e >2. decimais
                selectivePressureToAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                actualComponent = selectivePressureToAdd;
                break;
            case 7:
                availableParametersModel.addAll((Collection<? extends String>) availableParameters.get("Recombination"));
                availableParametersList.setModel(availableParametersModel);
                actualComponent = jScrollPaneOfAvailableParam;
                break;
            case 8:
                gaProbabilityToAdd = new JTextField("",7);
                //proteger
                gaProbabilityToAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                actualComponent = gaProbabilityToAdd;
                break;
            case 9:
                availableParametersModel.addAll((Collection<? extends String>) availableParameters.get("Mutation"));
                availableParametersList.setModel(availableParametersModel);
                actualComponent = jScrollPaneOfAvailableParam;
                break;
            case 10:
                availableParametersModel.addAll((Collection<? extends String>) availableParameters.get("Statistics"));
                availableParametersList.setModel(availableParametersModel);
                actualComponent = jScrollPaneOfAvailableParam;
                break;
                //actualComponent =component;
            case 11:
               HashMap<String,Object> layouts = (HashMap<String, Object>) availableParameters.get("WarehouseLayout");
                availableParametersModel.addAll(layouts.keySet());
                availableParametersList.setModel(availableParametersModel);
                actualComponent = jScrollPaneOfAvailableParam;
                break;
            case 12:
                HashMap<String,Object> picks = (HashMap<String, Object>) availableParameters.get("Picks");
                availableParametersModel.addAll(picks.keySet());
                availableParametersList.setModel(availableParametersModel);
                actualComponent = jScrollPaneOfAvailableParam;
                break;
        }

    }

}
