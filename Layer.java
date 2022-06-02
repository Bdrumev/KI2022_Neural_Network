import java.util.Arrays;

public class Layer {
    public Neuron[] neuronen;
    private int size;

    //Nur Hidden und OutputLayer
    public Layer(int AnzahlInputs, int AnzahlNeuronen) {
        this.neuronen = new Neuron[AnzahlNeuronen];

        for (int current = 0; current < neuronen.length; current++) {
            neuronen[current] = new Neuron(AnzahlInputs);
        }
    }

    public int getSize(){
        return neuronen.length;
    }

    //InputLayer
    public Layer(int AnzahlNeuronen) {
        this.neuronen = new Neuron[AnzahlNeuronen];
        for (int current = 0; current < neuronen.length; current++) {
            neuronen[current] = new Neuron();
        }
    }
}
