rm -rf classes
mkdir classes
javac -g -d classes -cp libs/* \
    src/hu/devo/bloom/*.java \
    src/hu/devo/experiment/*.java \
    src/hu/devo/hash/*.java \
    src/hu/devo/util/*.java
java -cp "libs/*;classes" hu/devo/experiment/Main
