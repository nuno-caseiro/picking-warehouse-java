package ipleiria.estg.dei.ei.model;

import ipleiria.estg.dei.ei.model.search.Location;

import java.util.List;

public interface EnvironmentListener {

    void updateEnvironment(List<Location> agents);

    void createEnvironment();

    void createSimulation();

    void createSimulationPicks();
}


