import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int inputKnoten = 8;
    static int outputKnoten = 1;

    public static void shuffle(double[][] a) {

        for (int i = a.length - 1; i > 0; i--) {
            for (int j = a[i].length - 1; j > 0; j--) {
                int m = ThreadLocalRandom.current().nextInt(i + 1);

                double[] temp = a[i];
                a[i] = a[m];
                a[m] = temp;
            }
        }
    }


    public static void main(String[] args) {
        //Daten einlesen
        double[][] Daten = Einlesen.einlesenDia();

        //Trennen in Trainings und Testdaten
        int AnzahlTestdaten = (int) (Daten.length*0.15);
        shuffle(Daten);
        double[][] Trainingsdaten = Arrays.copyOfRange(Daten, 0, Daten.length-AnzahlTestdaten);
        double[][] Testdaten = Arrays.copyOfRange(Daten, Daten.length-AnzahlTestdaten, Daten.length);

        //Gewuenschte Werte einlesen
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

        //Netz initiieren
        Network Test = new Network(inputKnoten, HiddenLayer, outputKnoten);

        //Netz trainieren und evaluieren
        Test.train(Trainingsdaten, epochen);
        Test.evaluieren(Testdaten);
    }
}
