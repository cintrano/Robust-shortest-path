package es.uma.lcc.neo.robustness.mo.shortestpath;

import es.uma.lcc.neo.robustness.mo.shortestpath.model.graph.guava.GraphTable;
import es.uma.lcc.neo.robustness.mo.shortestpath.utilities.ProcessGraph;

import java.io.IOException;

/**
 * Created by Christian Cintrano on 24/04/17.
 *
 */
public class GraphUtilitiesMain {

    public static void main (String[] args) throws IOException, InterruptedException {
        System.out.println("=== START EXPERIMENTS ===");
        for (String s : args) {
            System.out.print(s + " ");
        }
        System.out.println();


        // graph = prepareGraph();
        // graph = ProcessGraph.fixVertexIndex(graph);

        GraphTable graph = ProcessGraph.parserFile("graph.ampliado.xml"); // Load Speed (km/h) -> 10
        graph = ProcessGraph.getMaxConnectedComponent(graph, "componentes_conexas_malaga.txt");
        //graph = ProcessGraph.applyMapping(graph, "mapping-malaga.txt");
        //graph = ProcessGraph.fixVertexIndex(graph);

        graph = ProcessGraph.applyWeights(graph, "weights.ampliado.xml"); // Load Distance (m) -> 11
        graph = ProcessGraph.divideWeights(graph, 11L, 10L, 0L, 3.6f); // time (s) -> 0

        graph = ProcessGraph.addValuesGraph(graph, "malaga.opendata.noise.mod.ssv"); // Load Noise -> 1

        graph = ProcessGraph.computeRandomWeights(graph, 0L, 0.9f, 1.1f, 2L);
        ProcessGraph.printGraph(graph, "new-malaga-graph.xml");
        ProcessGraph.printWeights(graph, "weights_time-noise_COMPLETE.xml");

        graph.getWeightsMatrix().column(10L).clear();
        graph.getWeightsMatrix().column(11L).clear();
        ProcessGraph.printWeights(graph, "weights_time-noise.xml");

/*
        graph = prepareGraph();
        ProcessGraph.printRandomWeights(graph, "wVar0.xml", 0L, 0.9f, 1.1f, 2);
        ProcessGraph.printRandomWeights(graph, "wVar1.xml", 1L, 0.9f, 1.1f, 3);
        ProcessGraph.printMapping(graph);
        */
    }

    private static GraphTable prepareGraph() {
        // Graph
        String graphFilePath = "graph_connected.xml";
        String weightFilePath0 = "wNew.xml";
        GraphTable graph = ProcessGraph.parserFile(graphFilePath);
        System.out.println("Adding weight to the graph...");
        graph = ProcessGraph.applyWeights(graph, weightFilePath0);
        graph = ProcessGraph.applyWeights(graph, "wVar0.xml");
        graph = ProcessGraph.applyWeights(graph, "wVar1.xml");
        graph = ProcessGraph.applyMapping(graph, "mapping-malaga.txt");
        return graph;
    }
}