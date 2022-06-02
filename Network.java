import java.util.Arrays;

public class Network {
    static Layer[] layers;
    static double alpha = 0.5;

    public Network(int InputKnoten, int[] HiddenKnoten, int OutputKnoten) {
        int HiddenLayerAnzahl = HiddenKnoten.length;
        int gesamtLayerAnzahl = HiddenLayerAnzahl + 2;
        int lastLayerAnzahl = InputKnoten;

        layers = new Layer[gesamtLayerAnzahl];
        layers[0] = new Layer(InputKnoten);
        for (int i = 1; i < gesamtLayerAnzahl - 1; i++) {
            layers[i] = new Layer(lastLayerAnzahl, HiddenKnoten[i - 1]);
            lastLayerAnzahl = HiddenKnoten[i - 1];
        }
        layers[gesamtLayerAnzahl - 1] = new Layer(lastLayerAnzahl, OutputKnoten);
        System.out.println("Done!");

    }

    public void train(double[][] Trainingsdaten, int epochen) {
        int epoche = 0;
        do {
            for (double[] input : Trainingsdaten) {
                Forward(input);
                double[] target = Arrays.copyOfRange(input, input.length-layers[layers.length-1].neuronen.length,input.length);
                Backward(target);
            }
            epoche++;
        } while (epoche < epochen);

    }

    public void Forward(double[] input) {
        double[] output;
        Neuron n;

        for (int Schicht = 1; Schicht < layers.length; Schicht++) {
            output = new double[layers[Schicht].neuronen.length];

            for (int i = 0; i < layers[Schicht].neuronen.length; i++) {
                n = layers[Schicht].neuronen[i];
                output[i] = n.AktivierungsFunktion(n.Eingabefunktion(input));
            }
            input = output;
        }

    }

    private void Backward(double[] targets) {
        Layer OutputLayer = layers[layers.length-1];

        int Anzahl = 0;
        double[] Fehler = new double[layers.length];


        for (int i = 0; i < OutputLayer.getSize(); i++) {
            Fehler[layers.length - 1] += InverseSigmoid(OutputLayer.neuronen[i].Inputs) * (targets[i] - OutputLayer.neuronen[i].getOutput());
            Anzahl++;
        }
        Fehler[layers.length - 1] /= Anzahl;

        for (int L = layers.length - 2; L > 0; L--) {
            Anzahl = 0;
            for (int i = 0; i < layers[L].getSize() - 1; i++) {
                double delta = 0;
                for (int j = 0; j < layers[L].neuronen[i].weights.length - 1 ; j++) {
                    delta += layers[L].neuronen[i].weights[j] * Fehler[L+1];
                }
                Fehler[L] += InverseSigmoid(layers[L].neuronen[i].Inputs) * delta;
                Anzahl++;
            }
            Fehler[L] /= Anzahl;
        }

        //updateWeights
        for (int L = 1; L < layers.length; L++) {
            System.out.println("Layer " + L);
            for (int K = 0; K < layers[L].getSize(); K++) {
                System.out.println("Knoten " + K);
                Neuron N = layers[L].neuronen[K];
                for (int w = 0; w < N.weights.length; w++) {
                    System.out.println("Vorher "+ N.weights[w]);
                    System.out.println(Fehler[L]);
                    N.weights[w] = N.weights[w] + alpha*N.getOutput()*Fehler[L];
                    System.out.println(" Danach " + N.weights[w]);
                }
            }
        }
    }

    private double InverseSigmoid(double in) {
        //TODO
        return in;
    }
}
