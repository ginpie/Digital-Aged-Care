import java.io.File;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new File(".").getAbsolutePath());
        KNN knn = new KNN("./app/src/main/java/KNN/fall_data_101.csv", "./app/src/main/java/KNN/test.csv");
        int a = knn.wrappingUp();
        System.out.println(a);
    }
}
