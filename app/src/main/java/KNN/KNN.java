/**
 * K-Nearest Neighbor (KNN) method - Data training and testing
 *
 * Authors:         Jinpei Chen
 *                  Yuzhao Li
 *
 * Created data:    10/09/2019
 * Last modified:   30/09/2019
 */

import com.example.falldeteciton.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class KNN {

    String filename;
    String testfilename;

    public KNN(String filename, String testfilename){
        this.filename = filename;
        this.testfilename = testfilename;
    }

    // lists for storing training and testing datasets label and features.
    private List<Double[]> trainfeatures = new ArrayList<>();
    private List<String> trainlabel = new ArrayList<>();

    private List<Double[]> testfeatures = new ArrayList<>();
    private List<String> testlabel = new ArrayList<>();


    // Variables
    int determin_K_by_size = 0;        // 0 for manual assign, 1 for auto determine by size
    int knn_value = 7;
    int number_Of_Label = 0;
    int Distance_Metrics_Selction = 1; // 1 for Euclidean, 0 for Manhattan

    // loading training data and extracting features and label for training dataset
    private void loadtrainData(String filename) throws IOException {

        File file = new File(filename);

        try {
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String line;
            while ((line = readFile.readLine()) != null) {
                String[] split = line.split(",");
                Double[] values = new Double[split.length - 1];
                for (int i = 0; i < split.length-1; i++)
                    values[i] = Double.parseDouble(split[i+1]);
                trainfeatures.add(values);
                trainlabel.add(split[0]);
                number_Of_Label++;
            }
            readFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // loading training data and extracting features and label for training dataset
    private void loadtestData(String testfilename) throws IOException {

        File testfile = new File(testfilename);

        try {
            BufferedReader testreadFile = new BufferedReader(new FileReader(testfile));
            String testline;
            while ((testline = testreadFile.readLine()) != null) {

                String[] split = testline.split(",");
                Double[] feature = new Double[split.length];
                for (int i = 0; i < split.length; i++)
                    feature[i] = Double.parseDouble(split[i]);
                testfeatures.add(feature);
            }
            testreadFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

        private Double getDistance(Double[] a1, Double[] a2) {

            if (Distance_Metrics_Selction == 1) {
                return euclideanDistance(a1, a2);
                // calling accuracy method to show accuracy of model.
//                accuracy();
            }

            else {
                return manhattanDistance(a1, a2);
//                accuracy();
            }

        }


        private Double euclideanDistance(Double[] a1, Double[] a2) {
            double sum = 0;
            for (int i=0; i<a1.length; i++) {
                //applied Euclidean distance formula
                sum += Math.pow(a1[i] - a2[i], 2);
            }
            return Math.sqrt(sum);
        }

        private Double manhattanDistance(Double[] a1, Double[] a2) {
            KNN_Distance euclidean = new KNN_Distance();
            double sum = 0;
            for (int i = 0; i < a1.length; i++)
                //Applied Manhattan distance formula
                sum += Math.abs(a1[i] - a2[i]);
            return sum;
        }

        private int determineK () {
            Double root = Math.sqrt(number_Of_Label);
            Double rawK = root / 2 ;
            int num = (int) Math.round(rawK) ;
            if ( num%2 != 0 ) {
                return num ;
            }
            else {
                return num - 1 ;
            }
        }

        public int wrappingUp(){
            try{
            loadtrainData(filename);
            loadtestData(testfilename);}
            catch(IOException e){e.printStackTrace();}

            List<Double> distances = new ArrayList<>();
            List<Double> distancesClone = new ArrayList<>();
            int CLASS_1=0, CLASS_2=0;

//            System.out.println(trainfeatures.size()+", "+testfeatures.size());

            for (int i=0; i<trainfeatures.size(); i++){
                Double distance = getDistance(trainfeatures.get(i),testfeatures.get(0));
                distances.add(distance);
                distancesClone.add(distance);
            }
            Collections.sort(distances);

            if (determin_K_by_size==1)
                knn_value = determineK();

            List<Double> k_shortest = distances.subList(0,knn_value);

            for (Double element : k_shortest) {
                System.out.println(element);
                Integer indexOnClone = distancesClone.indexOf(element);
                String label = trainlabel.get(indexOnClone);
                System.out.println(label);
                if (Double.parseDouble(label) == 1.0) {
                    CLASS_1++;
                }
                else if (Double.parseDouble(label) == 0.0) {
                    CLASS_2++;
                }
            }
            System.out.println(CLASS_1+" "+CLASS_2);
            if (CLASS_1 > CLASS_2){
                return 1;
            }
            else {
                return 0;
            }

        }

        /*
         * Calculating accuracy for model based Euclidean and Manhattan distance.
         */
//        void accuracy() throws IOException {
//            int count = 0;
//            File file = null;
//            if (Distance_Metrics_Selction == 1) {
//                file = new File("EuclideanResult.txt");
//            }
//
//            else if (Distance_Metrics_Selction == 2) {
//                file = new File("ManhattanResult.txt");
//
//            }
//
//            BufferedReader rf = new BufferedReader(new FileReader(file));
//            BufferedReader label = new BufferedReader(new FileReader(new File("RealLabel.txt")));
//            String s = rf.readLine();
//            while (s != null) {
//                String lab = label.readLine();
//                if (s.equals(lab)) {
//
//                } else {
//                    count++;
//                }
//
//                s = rf.readLine();
//            }
//
//            System.out.println("Accuracy is: " + ((float) 100 - (((float) count / total_Number_Of_Label) * 100)) + "%");
//            rf.close();
//            label.close();
//        }

}
