import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner KonsoleScanner = new Scanner(System.in);
        System.out.print("Anzahl HiddenLayers: ");

        int Anzahl = KonsoleScanner.nextInt();
        int[] HiddenLayer = new int[Anzahl];
        for (int i = 0; i < Anzahl; i++) {
            System.out.print("Neuronenanzahl in Layer " + (i+1) + ": ");
            HiddenLayer[i] = KonsoleScanner.nextInt();
        }

        Network Test = new Network(8, HiddenLayer, 1);

        //,{0.2,0.3,1,1},{0.1,0.7,1,0}
        double[][] Trainingsdaten = Einlesen.einlesenDia();
        double[][] Testdaten = {{0.2,0.3,1,1},{0.2,0.3,1,1},{0.1,0.7,1,0}};
        Test.train(Trainingsdaten, 100);
        //Test.validate(Daten);
    }
}
