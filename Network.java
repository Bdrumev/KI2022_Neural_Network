import java.util.Arrays;

public class Network {
    static Layer[] layers;
    static double alpha = 0.3;	//Lernrate

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
        System.out.println("Created Network");

    }

    public void train(double[][] Trainingsdaten, int epochen) {
        int epoche = 0;
        System.out.println("Train...");
        do {
            for (double[] input : Trainingsdaten) {
                FeedForward(input);
                double[] target = Arrays.copyOfRange(input, input.length-layers[layers.length-1].neuronen.length,input.length);
                Backpropagation(target);
            }
            epoche++;
        } while (epoche < epochen);

    }

    public double[] FeedForward(double[] input) {
        double[] output = null;
        Neuron n;

        for (int Schicht = 1; Schicht < layers.length; Schicht++) {
            output = new double[layers[Schicht].neuronen.length];

            for (int i = 0; i < layers[Schicht].neuronen.length; i++) {
                n = layers[Schicht].neuronen[i];
                output[i] = n.AktivierungsFunktion(n.Eingabefunktion(input)+n.bias);
            }
            input = output;

        }

        return output;
    }

    private void Backpropagation(double[] targets) {
        Layer OutputLayer = layers[layers.length-1];
        int outL = layers.length-1;
        double[][] delta;
        
        delta = new double[layers.length][];
    	delta[outL] = new double[OutputLayer.getSize()];
    	
        //Backpropagation OutputLayer
        for(int k = 0; k < OutputLayer.getSize(); k++) {
        	Neuron N = OutputLayer.neuronen[k];
        	delta[outL][k] = (targets[k] - N.getOutput())* Neuron.SigmoidAbleitung(N.Inputs);
        }
        
        //Backpropagation HiddenLayer
        for(int L = layers.length - 2; L > 0; L--) {	//je Hidden Layer L, r??ckw??rts
        	delta[L] = new double[layers[L].getSize()];
        	
	        	for(int i = 0; i < layers[L].getSize() - 1; i++) { //je Neuron i
	        		double sum = 0.0;
		        	for(int j =0; j<layers[L+1].neuronen.length; j++) { //pro Gewicht im Knoten i
		        		//im Vorlesung: delta[j] = g'(in[j])*Summe(w[j][k]*delta[k]); k-Knoten i.d. Ausgabeschicht
		        		sum = delta[L+1][j]*layers[L+1].neuronen[j].weights[i];
		        	}
		        	delta[L][i] = Neuron.SigmoidAbleitung(layers[L].neuronen[i].Inputs)*sum;
	        	}
        	
        }


        for (int L = 1; L < layers.length; L++) { //je Layer
            for (int K = 0; K < layers[L].getSize(); K++) { // je Neuron im Layer
                Neuron N = layers[L].neuronen[K];
                //updateWeights
                for (int w = 0; w < N.weights.length; w++) { // Gewichte
                	Neuron M = layers[L-1].neuronen[w];
                    N.weights[w] += alpha*M.getOutput()*delta[L][K];
                }

                //updateBias
                N.bias += (alpha*delta[L][K]);  //b=b+alpha*delta
            }
        }
    }

    public void evaluieren(double[][] liste) {
        int falschPositiv  = 0;
        int falschNegativ  = 0;
        int richtigPositiv = 0;
        int richtigNegativ = 0;
        int anzahlPositiv  = 0;
        int anzahlNegativ  = 0;
        int n = liste[0].length-1;

        double[] ergebnis = new double[13];

        for (double[] doubles : liste) {

            double out = FeedForward(doubles)[0];
            double y = doubles[n];


            if (Math.round(out) == 0 && Math.round(y) == 1) {
                falschNegativ++;
                anzahlPositiv++;
            } else if (Math.round(out) == 1 && Math.round(y) == 1) {
                richtigPositiv++;
                anzahlPositiv++;
            } else if (Math.round(out) == 1 && Math.round(y) == 0) {
                falschPositiv++;
                anzahlNegativ++;
            } else if (Math.round(out) == 0 && Math.round(y) == 0) {
                richtigNegativ++;
                anzahlNegativ++;
            } else {
                System.out.println("Error0 in Auswertung");
            }
        }
        if(anzahlPositiv != richtigPositiv+falschNegativ)System.out.println("Error1 in Auswertung");
        if(anzahlNegativ != richtigNegativ+falschPositiv)System.out.println("Error2 in Auswertung");
        if(anzahlPositiv+anzahlNegativ != liste.length)System.out.println("Error3 in Auswertung");


        ergebnis[0] = liste.length;
        ergebnis[1] = anzahlPositiv;
        ergebnis[2] = anzahlNegativ;
        ergebnis[3] = (double)anzahlPositiv/(double)liste.length;
        ergebnis[4] = (double)anzahlNegativ/(double)liste.length;
        ergebnis[5] = (double)(richtigPositiv+richtigNegativ)/(double)liste.length;
        ergebnis[6] = richtigPositiv;
        ergebnis[7] = falschPositiv;
        ergebnis[8] = richtigNegativ;
        ergebnis[9] = falschNegativ;
        ergebnis[10] = (double)richtigPositiv / (double)(richtigPositiv+falschNegativ);
        ergebnis[11] = (double)falschPositiv  / (double)(richtigNegativ+falschPositiv);
        ergebnis[12] = (2*ergebnis[5]*(ergebnis[10]+ergebnis[11]))/(ergebnis[5]+ergebnis[10]+ergebnis[11]);

        System.out.println("Anzahl Muster:  \t" + ergebnis[0]);
        System.out.println("Anzahl Positiv: \t" + ergebnis[1]);
        System.out.println("Anzahl Negativ: \t" + ergebnis[2]);
        System.out.println("Anteil Positiv: \t" + ergebnis[3]);
        System.out.println("Anteil Negativ: \t" + ergebnis[4]);

        System.out.println("Genauigkeit  :  \t" + ergebnis[5]);
        System.out.println("Trefferquote:   \t" + ergebnis[10]);
        System.out.println("Ausfallrate :   \t" + ergebnis[11]);

        System.out.println("richtigPositiv: \t" + ergebnis[6]);
        System.out.println("falsch Negativ: \t" + ergebnis[9]);
        System.out.println("richtigNegativ: \t" + ergebnis[8]);
        System.out.println("falsch Positiv: \t" + ergebnis[7]);

        System.out.println("F-Wert: \t" + ergebnis[12]);


    }
    
    
}
