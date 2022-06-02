import java.util.concurrent.ThreadLocalRandom;

public class Neuron {
    double[] weights;
    double Inputs;
    double bias, Output;
    public Neuron(int AnzahlWeights) {
        weights = new double[AnzahlWeights];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = ThreadLocalRandom.current().nextDouble(-1,1);
        }
    }

    public Neuron() {

    }

    public double Eingabefunktion(double[] eingang) {
        double result = 0.0;
        for (int i = 0; i < weights.length; i++) {
            result += weights[i]*eingang[i];
        }
        Inputs =(result+bias);
        return Inputs;
    }

    public double AktivierungsFunktion(double in) {
        //Sollte Sidmoid sein
        Output = 1/(1+Math.exp(-in));
        return Output;
    }
    public double getOutput() {
        return Output;
    }
}
