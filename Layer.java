public class Layer {
    public Neuron[] neuronen;
    float[] weights;

    //Nur Hidden und OutputLayer
    public Layer(int AnzahlWeights, int AnzahlNeuronen) {
        this.neuronen = new Neuron[AnzahlNeuronen];
        this.weights = new float[AnzahlWeights];

        for (Neuron currentNeuron : neuronen) {
            new Neuron();
        }

    }

    public Layer(int input) {
        this.neuronen = new Neuron[input];
        for (Neuron currentNeuron : neuronen) {
            new Neuron();
        }
    }
}
