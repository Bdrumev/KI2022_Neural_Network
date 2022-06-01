public class Network {
    static Layer[] layers;

    public Network(int InputKnoten, int[] HiddenKnoten, int OutputKnoten) {
        int HiddenLayerAnzahl = HiddenKnoten.length;
        int gesamtLayerAnzahl = HiddenLayerAnzahl + 2;
        int lastLayerAnzahl = InputKnoten;

        layers = new Layer[gesamtLayerAnzahl];
        layers[0] = new Layer(InputKnoten);
        for (int i = 1; i < gesamtLayerAnzahl-1; i++) {
            layers[i] = new Layer(lastLayerAnzahl ,HiddenKnoten[i-1]);
            lastLayerAnzahl = HiddenKnoten[i-1];
        }
        layers[gesamtLayerAnzahl-1] = new Layer(lastLayerAnzahl, OutputKnoten);
        System.out.println("Done!");

    }

    public void train() {
    }
}
