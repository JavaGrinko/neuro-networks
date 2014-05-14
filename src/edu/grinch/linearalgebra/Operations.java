package edu.grinch.linearalgebra;

public class Operations{

	public static Matrix multiplication(Matrix A, Matrix B){
        int newI = A.getHeight();
        int newJ = B.getWidth();
        Matrix result = new Matrix(newI,newJ);
        Matrix T = transpose(B);
        for (int i=0; i<newI; i++){
            for (int j=0; j<newJ;j++){
                Vector V1 = A.getVector(i);
                Vector V2 = T.getVector(j);
                Vector M = multiplication(V1,V2);
                result.setData(i,j,M.toDouble());
            }
        }
        return result;
	}

    public static Matrix multiplication(Matrix A, double b){
        Matrix result = new Matrix(A.getHeight(),A.getWidth());
        for (int i = 0; i < result.getHeight(); i++){
            for (int j = 0; j < result.getWidth(); j++){
                result.setData(i,j,A.getData(i,j) / b);
            }
        }
        return result;
    }

    public static Matrix transpose(Matrix A){
        Matrix T = new Matrix(A.getWidth(),A.getHeight());
        for (int i=0; i < T.getHeight(); i++)
            for (int j=0; j<T.getWidth(); j++)
                T.setData(i,j,A.getData(j,i));
        return T;
    }

    public static Vector multiplication(Vector A, Vector B){
        Vector v = new Vector(A.getCount());
        for (int i=0; i < v.getCount(); i++)
            v.setData(i,A.getData(i)*B.getData(i));
        return v;
    }

    public static Vector multiplication(Vector A, double b){
        Vector v = new Vector(A.getCount());
        for (int i=0; i < v.getCount(); i++)
            v.setData(i,A.getData(i)*b);
        return v;
    }

    public static Matrix addition(Matrix A, Matrix B){
        Matrix S = new Matrix(A.getHeight(),A.getWidth());
        for (int i = 0; i < S.getHeight(); i++){
            for (int j = 0; j < S.getWidth(); j++){
                S.setData(i,j,A.getData(i,j)+B.getData(i,j));
            }
        }
        return S;
    }

    public static Vector addition(Vector v1, Vector v2){
        Vector s = new Vector(v1.getCount());
        for (int i = 0; i < s.getCount(); i++){
            s.setData(i,v1.getData(i)+v2.getData(i));
        }
        return s;
    }
}