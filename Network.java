import java.util.Arrays;

public class Network {
    static Layer[] layers;
    static double alpha = 0.5;	//Lernrate

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

        for (double out:output) {
            System.out.println(out);
        }
    }

    private void Backward(double[] targets) {
        Layer OutputLayer = layers[layers.length-1];
        int outL = layers.length-1;
        double[][] delta;
        
        delta = new double[layers.length][];
    	delta[outL] = new double[OutputLayer.getSize()];
    	
        //Backpropagation OutputLayer
        for(int k = 0; k < OutputLayer.getSize(); k++) {
        	Neuron N = OutputLayer.neuronen[k];
        	delta[outL][k] = (targets[k] - N.getOutput())*InverseSigmoid(N.Inputs);
        }
        
        //Backpropagation HiddenLayer
        for(int L = layers.length - 2; L > 0; L--) {	//je Hidden Layer L, rückwärts
        	delta[L] = new double[layers[L].getSize()];
        	
        	if(true) {	//Schwellwert nicht erreicht: Bias-Abhängigkeit
	        	for(int i = 0; i < layers[L].getSize() - 1; i++) { //je Neuron i
	        		double sum = 0.0;
		        	for(int j =0; j<layers[L].neuronen[i].weights.length -1; j++) { //pro Gewicht im Knoten i
		        		//im Vorlesung: delta[j] = g'(in[j])*Summe(w[j][k]*delta[k]); k-Knoten i.d. Ausgabeschicht
		        		sum = delta[L][j]*layers[L].neuronen[i].weights[j];
		        	}
		        	delta[L][i] = InverseSigmoid(layers[L].neuronen[i].Inputs)*sum;
	        	}
        	}
        }

        //updateWeights
        for (int L = 1; L < layers.length; L++) { //je Layer
            for (int K = 0; K < layers[L].getSize(); K++) { // je Neuron im Layer
                Neuron N = layers[L].neuronen[K];
                for (int w = 0; w < N.weights.length; w++) { // Gewichte
                    N.weights[w] = N.weights[w] + alpha*N.getOutput()*delta[L][K];
                }
                
            }
        }
        
        //updateBiases
        for (int L = 1; L < layers.length; L++) { //je Layer
            for (int K = 0; K < layers[L].getSize(); K++) { // je Neuron im Layer
                Neuron N = layers[L].neuronen[K];
                N.updateBias(alpha*delta[L][K]);  //b=b+alpha*delta
            }
        }
        
        
        
        
    }

    private double InverseSigmoid(double in) { //g'(x)
        return (1/(1+Math.exp(-in)))*(1-(1/(1+Math.exp(-in))));
    }
    
    
}
