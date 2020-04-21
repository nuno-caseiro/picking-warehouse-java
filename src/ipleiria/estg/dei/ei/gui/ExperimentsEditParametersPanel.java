package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.IntegerTextField_KeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class ExperimentsEditParametersPanel extends JPopupMenu {

    private final ExperimentParametersPanel experimentParametersPanel;

    private HashMap<String,List<String>> availableParameters;

    private JButton add;
    private JButton remove;
    private JButton ok;
    private JButton cancel;

    private JLabel errors;

    private DefaultListModel atualParametersModel;
    private JList atualParametersList;

    private DefaultListModel availableParametersModel;
    private JList availableParametersList;

    private JScrollPane jScrollPaneOfAtualParam;
    private JScrollPane jScrollPaneOfAvailableParam;

    private JTextField gaProbabilityToAdd;
    private JTextField integerToAdd;
    private JTextField selectivePressureToAdd;

    private JPanel centerPanel = new JPanel(new BorderLayout());
    private JPanel southPanel = new JPanel(new BorderLayout());

    private JComponent actualComponent;
    private JLabel activeParameter = new JLabel();

    public ExperimentsEditParametersPanel(int panelId, List<String> actualParameters, HashMap<String,List<String>> availableParameters, ExperimentParametersPanel experimentsPanel) {
        setLayout(new BorderLayout());

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
        }
        this.add(actualComponent,BorderLayout.EAST);

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
                    atualItem=availableParametersList.getSelectedValue().toString();
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
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actualParameters.clear();
                for (int i = 0; i < atualParametersModel.size(); i++) {
                    actualParameters.add(atualParametersModel.get(i).toString());
                }
                experimentParametersPanel.hideEditParameters(panelId);
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                experimentParametersPanel.hideEditParameters(panelId);
            }
        });

        this.add(activeParameter,BorderLayout.NORTH);

        this.add(jScrollPaneOfAtualParam,BorderLayout.WEST);


        JPanel addRemovePanel = new JPanel(new GridLayout(2,1));
        addRemovePanel.add(add);
        addRemovePanel.add(remove);

        this.centerPanel.add(addRemovePanel,BorderLayout.CENTER);
        this.add(centerPanel,BorderLayout.CENTER);

        this.southPanel.add(ok,BorderLayout.WEST);
        this.southPanel.add(cancel,BorderLayout.EAST);
        this.add(southPanel,BorderLayout.SOUTH);

    }

    private void init(){
        add = new JButton("+");
        remove = new JButton("-");
        ok = new JButton("OK");
        cancel = new JButton("Cancel");

        atualParametersModel = new DefaultListModel();
        atualParametersList = new JList(atualParametersModel);
        availableParametersModel= new DefaultListModel();
        availableParametersList= new JList();

        jScrollPaneOfAtualParam = new JScrollPane(atualParametersList);
        jScrollPaneOfAvailableParam = new JScrollPane(availableParametersList);
        //jScrollPaneOfAtualParam.setPreferredSize(new Dimension(70,100));

        centerPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());

        activeParameter = new JLabel();
    }

    private void switchHelper(String labelTitleText, int typeOfComponent){
        JComponent component = null;
        activeParameter.setText(labelTitleText);
        if(typeOfComponent==1){
            integerToAdd= new JTextField(7);
            integerToAdd.addKeyListener(new IntegerTextField_KeyAdapter(null));
            component=integerToAdd;
        }
        if(typeOfComponent==4){
            availableParametersModel.addAll(availableParameters.get("Selection"));
            availableParametersList.setModel(availableParametersModel);
            component=jScrollPaneOfAvailableParam;
        }
        if(typeOfComponent==6){
            selectivePressureToAdd=new JTextField(7);
            //proteger para valores <1 e >2. decimais
            component=selectivePressureToAdd;
        }
        if(typeOfComponent==7){
            availableParametersModel.addAll(availableParameters.get("Recombination"));
            availableParametersList.setModel(availableParametersModel);
            component=jScrollPaneOfAvailableParam;
        }
        if(typeOfComponent==8){
            gaProbabilityToAdd=new JTextField(7);
            //proteger
            component=gaProbabilityToAdd;
        }
        if(typeOfComponent==9){
            availableParametersModel.addAll(availableParameters.get("Mutation"));
            availableParametersList.setModel(availableParametersModel);
            component=jScrollPaneOfAvailableParam;
        }

        actualComponent =component;
    }

    @Override
    protected void firePopupMenuWillBecomeInvisible() {
        experimentParametersPanel.reverseEditing();
        super.firePopupMenuWillBecomeInvisible();
    }
}
