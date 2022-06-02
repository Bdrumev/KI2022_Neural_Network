import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Einlesen {
    public static double[][] einlesenDia() {
        String thisLine;
        BufferedReader myInput;
        double[][] data;
        List<String[]> datatemp = new ArrayList<>();

        try {
            myInput = new BufferedReader(new FileReader("diabetes_df_preprocessed.csv"));

            while ((thisLine = myInput.readLine()) != null) {
                datatemp.add(thisLine.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        data = new double[datatemp.size()-1][datatemp.get(1).length-1];
        for (int i = 1; i < datatemp.size(); i++) {

            for (int j = 1; j < data[0].length + 1; j++) {
                data[i-1][j-1] = Double.parseDouble(datatemp.get(i)[j]);
            }

        }


        return data;
    }
}
