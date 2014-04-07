
package seidelmethod;

import java.util.Arrays;

public class Main {

    private static final double eps = 0.00001;
    public static double[] multiply(double[][] matrix, double[] vector)
    {
        double[] result = new double[vector.length];
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                result[i] += matrix[i][j]*vector[j];
        return result;
    }
    public static double[] add(double[] vector1, double[] vector2)
    {
        double[] result = new double[vector1.length];
        for(int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] + vector2[i];
        return result;
    }
    public static double[] subtract(double[] vector1, double[] vector2)
    {
        double[] result = new double[vector1.length];
        for(int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] - vector2[i];
        return result;
    }
    public static double matrixNorm(double[][] matrix)
    {
        double norm = 0;
        for(int i = 0; i < matrix.length; i++)
        {
            double max = 0;
            for(int j = 0; j < matrix[i].length; j++)
                if(Math.abs(matrix[i][j]) > max)
                    max = Math.abs(matrix[i][j]);
            norm += max;
        }
        return norm;
    }
    public static double vectorNorm(double[] vector)
    {
        double norm = 0;
        for(int i = 0; i < vector.length; i++)
            if(Math.abs(vector[i]) > norm)
                norm = Math.abs(vector[i]);
        return norm;
    }
    public static void main(String[] args) {

        double[][] matrix = {{5, -1, 2},
                             {1, 4, -1},
                             {1, 1, 4}};
        double[] vector = {8, -4, 4};
        double[][] alpha = new double[matrix.length][matrix.length];
        double[] beta = new double[matrix.length];
        for(int i = 0; i < alpha.length; i++)
        {
            for(int j = 0; j < alpha[i].length; j++)
                alpha[i][j] = - matrix[i][j]/matrix[i][i];
            beta[i] = vector[i]/matrix[i][i];
            alpha[i][i] = 0;
        }

        double[] prevVector = new double[vector.length];
        Arrays.fill(prevVector, 0);
        double[] currVector = beta;
        while(vectorNorm(subtract(currVector, prevVector)) > eps)
        {
            prevVector = currVector;
            //currVector = add(beta,multiply(alpha,prevVector));
            for(int i = 0; i < alpha.length; i++)
                for(int j = 0; j < alpha[i].length; j++)
                    currVector[i] += alpha[i][j]*currVector[j];
        }
        System.out.println("Решение СЛАУ c точностью в 5 знаков");
        for(int i = 0; i < currVector.length; i++)
        {
            System.out.println(currVector[i]);
            currVector[i] = Math.floor(currVector[i]/eps)*eps;
        }
        double[] deltaVector = subtract(vector,multiply(matrix,currVector));
        System.out.println("Вектор погрешностей(Невязка)");
        for(int i = 0; i < deltaVector.length; i++)
            System.out.println(deltaVector[i]);
    }
}