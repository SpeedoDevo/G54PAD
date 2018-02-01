To run the experiments, just execute `./run.sh`. Please ensure that Java >= 8 is
available on the PATH. If this does not work then import the folder into IntelliJ IDEA
or Eclipse and run from `src/hu/devo/experiments/Main.java`. To change between the two
experiments modify the corresponding line in `main()`. By default the shorter dataset
is used to run experiments (`data.less.csv`), if you want to use the original size
dataset change the constant in Main to `data/data.csv`.

Used libraries:
* uniVocity parsers 2.5.9
  * source: https://github.com/uniVocity/univocity-parsers
  * license: Apache License 2.0
  * purpose: CSV parsing and creation
  * file: univocity-parsers-2.5.9.jar
