package es.uma.lcc.neo.cintrano.robustness.mo.shortestpath.algorithm.dijkstra;

import java.util.Map.Entry;

public class DijkstraWNDim extends Dijkstra {

    private Long[] labs;
    private float[] weights;

    public DijkstraWNDim(Long[] labs) {
        super();
        this.labs = labs;
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }

    protected float getEdgeDistance(Long source, Entry<Long, Long> target) {
        float sum = 0f;
//        System.out.print("getEdgeDistance ");
//        System.out.print(getGraph().getWeightsMatrix().columnKeySet().size() + " ");
        for (int i = 0; i < labs.length; i++) {
//            System.out.print(" " + getGraph().getWeightsMatrix().get(target.getValue(), labs[i]));
            sum += (weights[i] * getGraph().getWeightsMatrix().get(target.getValue(), labs[i]));
        }
//        System.out.println();
        return sum;
    }

}
