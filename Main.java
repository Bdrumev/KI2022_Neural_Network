import java.util.Scanner;

public class Main {

    static int inputKnoten = 8;
    static int outputKnoten = 1;

    public static void main(String[] args) {
        Scanner KonsoleScanner = new Scanner(System.in);
        System.out.print("Anzahl HiddenLayers: ");

        int Anzahl = KonsoleScanner.nextInt();
        int[] HiddenLayer = new int[Anzahl];
        for (int i = 0; i < Anzahl; i++) {
            System.out.print("Neuronenanzahl in Layer " + (i+1) + ": ");
            HiddenLayer[i] = KonsoleScanner.nextInt();
        }
        System.out.println("Anzahl Epochen: ");
        int epochen = KonsoleScanner.nextInt();

        Network Test = new Network(inputKnoten, HiddenLayer, outputKnoten);

        double[][] Trainingsdaten = Einlesen.einlesenDia();
        Test.train(Trainingsdaten, epochen);
        Test.evaluierenDiabetes(Trainingsdaten);
    }
}
