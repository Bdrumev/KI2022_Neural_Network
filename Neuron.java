public class Neuron {
    double[] weights;
    int Inputs;
    double bias;
    public Neuron(int AnzahlWeights) {
        weights = new double[AnzahlWeights];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = (Math.random()*2)-1;
        }
    }

    public Neuron() {

    }

    private void updateWeights(Layer prevLayer) {
        for (int n = 0; n < prevLayer.neuronen.length; n++) {
            System.out.println("Updated");
        }
    }

    public double AktivierungsFunktion(double in) {
        //Sollte Sidmoid sein
        return 1/(1+Math.exp(-10*in));
    }
    public double Eingabefunktion(double[] eingang) {
        double result = 0.0;

        for (int i = 0; i < weights.length; i++) {
            result += weights[i]*eingang[i];
        }
        System.out.println(result+bias);
        return result+bias;
    }

}
