public class Network {
    static Layer[] layers;

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
                //Backward(input);
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
        Layer last = layers[layers.length-1];
    }
}
