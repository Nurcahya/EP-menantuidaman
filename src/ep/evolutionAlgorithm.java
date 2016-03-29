package ep;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;


public class evolutionAlgorithm {
    int jumMutasi;
    public evolutionAlgorithm(String filename, int mutasi) throws FileNotFoundException {
        mate(individu(filename), mutasi);
    }

    private void mate(PriorityQueue<evolutionaryDecisionTree> family, int mutasi) {
        evolutionaryDecisionTree kromosom1 = family.poll();
        evolutionaryDecisionTree kromosom2 = family.poll();

        /** Genetic : */
        Random random = new Random();
        jumMutasi = mutasi;
        int shearLocation = random.nextInt(this.jumMutasi);
        ArrayList<Node> branch = kromosom1.train().get(shearLocation);
        kromosom2.train().add(branch);
        kromosom1.train().remove(branch);
    }

    private PriorityQueue<evolutionaryDecisionTree> individu(String filename) throws FileNotFoundException {
        PriorityQueue<evolutionaryDecisionTree> family = new PriorityQueue<evolutionaryDecisionTree>(4, new Comparator<evolutionaryDecisionTree>() {
            @Override
            public int compare(evolutionaryDecisionTree kromosom1, evolutionaryDecisionTree kromosom2) throws NullPointerException, ClassCastException {
                double accuracy1 = test(kromosom1.train(), kromosom1.test(), 1);
                double accuracy2 = test(kromosom2.train(), kromosom2.test(), 2);
                double size1 = kromosom1.train().size();
                double size2 = kromosom2.train().size();
                if ((accuracy1 - size1) < (accuracy2 - size2)) {
                    return -1;
                } else if ((accuracy1 - size2) == (accuracy2 - size2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        for (int i = 0; i < 2; i++) {
            evolutionaryDecisionTree parent = new evolutionaryDecisionTree(filename);
            family.add(parent);
        }
        return family;
    }

    public double test(ArrayList<ArrayList<Node>> tree, ArrayList<Node> data, int urutan) {
        double right = 0;
        double wrong = 0;
        for (Node d : data) {
            String actual = d.label;
            d.ID = d.label;
            int nav = 1;
            boolean identified = false;
            while (!identified) {
                Node sample = tree.get(nav).get(0);
                int feature = sample.previous.get(sample.previous.size() - 1);

                if (d.features.get(feature).equals(sample.features.get(feature))) {
                    nav = sample.startNext;
                    if (sample.startNext == 0) {
                        d.label = sample.label;
                        identified = true;
                    }
                } else {
                    do {
                        nav++;
                        if (nav >= tree.size()) {
                            d.label = "";
                            identified = true;
                        }
                    } while (!identified && tree.get(nav).isEmpty());
                }
            }
            if (actual.equals(d.label)) {
                //System.out.println("Bener!");
                right++;
            } else {
                //System.out.println("Duh dek, salah!");
                wrong++;
            }
        }
        System.out.println("Accuracy individu "+urutan+":\t" + right/(right + wrong));
        System.out.println("Fitness individu "+urutan+":\t" +wrong);
        return right/(right + wrong);
    }
}
