import java.util.Arrays;

public class Layer {
    public Neuron[] neuronen;

    //Nur Hidden und OutputLayer
    public Layer(int AnzahlInputs, int AnzahlNeuronen) {
        this.neuronen = new Neuron[AnzahlNeuronen];

        for (int current = 0; current < neuronen.length; current++) {
            neuronen[current] = new Neuron(AnzahlInputs);
        }
    }

    //InputLayer
    public Layer(int AnzahlNeuronen) {
        this.neuronen = new Neuron[AnzahlNeuronen];
        for (int current = 0; current < neuronen.length; current++) {
            neuronen[current] = new Neuron();
        }
    }
    public int getSize(){
        return neuronen.length;
    }

}
