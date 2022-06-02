import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner Einlesen = new Scanner(System.in);
        System.out.print("Anzahl HiddenLayers: ");

        int Anzahl = Einlesen.nextInt();
        int[] HiddenLayer = new int[Anzahl];
        for (int i = 0; i < Anzahl; i++) {
            System.out.print("Neuronenanzahl in Layer " + (i+1) + ": ");
            HiddenLayer[i] = Einlesen.nextInt();
        }

        Network Test = new Network(2, HiddenLayer, 2);

        double[][] Testdaten = {{0.2,0.3,1,1},{0.2,0.3,1,1},{0.1,0.7,1,0}};
        Test.train(Testdaten, 1);
    }
}
