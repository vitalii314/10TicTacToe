package playground;

public interface Playground {
    void start();

    State doStep(int i, int j);

    String print();


}
