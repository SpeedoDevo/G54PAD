package hu.devo.experiment;

import hu.devo.util.Csv;
import hu.devo.util.Url;

import java.util.List;

public class Main {

    private static final String DATA = "data/data.less.csv";

    public static void main(String[] args) {
        List<Url> urls = Csv.readIntoBeanList(DATA, Url.class);

//        ComparisonExperiment.run(urls);
        FalsePositivesExperiment.run(urls);
    }
}
