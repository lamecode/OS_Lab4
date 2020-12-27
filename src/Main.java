import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    static ArrayList<ArrayList<Integer>> tasksAndResources = new ArrayList<>();

    public static void main(String[] args) {
        int[][] matrix = new int[5][5];
        taskMatrix(matrix);
        printMatrix(matrix);
        while (!matrixFilledWithZeroes(matrix)) {
            checkRowsAndCols(matrix);
        }
        check();
        printTasks();
    }

    public static void checkRowsAndCols(int[][] matrix) {
        boolean rows = false;
        boolean cols = false;
        int[] indexes = new int[2];
        for (int i = 0; i < matrix.length; i++) {
            if (getSumByRow(matrix[i]) == 1) {
                indexes[0] = i;
                indexes[1] = colIndex(matrix[i]);
                tasksAndResources.get(i).set(1, colIndex(matrix[i]) + 1);
                reduceMatrix(matrix, indexes);
                printMatrix(matrix);
                rows = true;
                cols = true;
                break;
            }
        }
        if (!rows) {
            for (int i = 0; i < matrix.length; i++) {
                if (getSumByCol(matrix, i) == 1) {
                    indexes[0] = colIndex(matrix[i]);
                    indexes[1] = i;
                    tasksAndResources.get(colIndex(matrix[i])).set(1, i + 1);
                    reduceMatrix(matrix, indexes);
                    printMatrix(matrix);
                    cols = true;
                    break;
                }
            }
        }
        if (!cols) {
            randomExecute(matrix);
        }
    }

    public static int colIndex(int[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 1) {
                return i;
            }
        }
        return 0;
    }

    public static int getSumByRow(int[] row) {
        int sum = 0;
        for (int i = 0; i < row.length; i++) {
            sum += row[i];
        }
        return sum;
    }

    public static int getSumByCol(int[][] matrix, int col) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][col];
        }
        return sum;
    }

    public static void taskMatrix(int[][] matrix) {
        int[] row;
        for (int i = 0; i < matrix.length; i++) {
            row = matrix[i];
            tasksAndResources.add(new ArrayList<>());
            tasksAndResources.get(i).add(i + 1);
            tasksAndResources.get(i).add(-999);
            for (int j = 0; j < matrix[i].length; j++) {
                row[j] = new Random().nextInt(2);
            }
        }
    }

    public static void printMatrix(int[][] matrix) {
        System.out.println("Стан матриці задач:");
        int[] row;
        for (int i = 0; i < matrix.length; i++) {
            row = matrix[i];
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println();
        }
    }

    public static void randomExecute(int[][] matrix) {
        int[] indexes = new int[2];
        indexes[0] = new Random().nextInt(5);
        indexes[1] = new Random().nextInt(5);
        while (matrix[indexes[0]][indexes[1]] != 1) {
            indexes[0] = new Random().nextInt(5);
            indexes[1] = new Random().nextInt(5);
        }
        tasksAndResources.get(indexes[0]).set(1, indexes[1] + 1);
        reduceMatrix(matrix, indexes);
        printMatrix(matrix);
    }

    public static void reduceMatrix(int[][] matrix, int[] indexes) {
        Arrays.fill(matrix[indexes[0]], 0);
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][indexes[1]] = 0;
        }
    }

    public static void printTasks() {
        for (int i = 0; i < tasksAndResources.size(); i++) {
            System.out.println("Task " + tasksAndResources.get(i).get(0) +
                    " -> Resource " + tasksAndResources.get(i).get(1));
        }
    }

    public static boolean matrixFilledWithZeroes(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void check() {
        ArrayList<Integer> resources = new ArrayList<>();
        int missing = 0;
        for (int i = 0; i < tasksAndResources.size(); i++) {
            for (int j = 0; j < tasksAndResources.size(); j++) {
                if (tasksAndResources.get(j).get(1) != -999) {
                    resources.add(tasksAndResources.get(j).get(1));
                }
            }
        }
        for (int i = 0; i < tasksAndResources.size(); i++) {
            if (!resources.contains(i + 1)) {
                missing = i + 1;
                break;
            }
        }
        for (int i = 0; i < tasksAndResources.size(); i++) {
            if (tasksAndResources.get(i).get(1) == -999) {
                tasksAndResources.get(i).set(1, missing);
            }
        }
    }
}
