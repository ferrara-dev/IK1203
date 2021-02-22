package src.util;

public class ArrayUtil{

    public enum BaseType {
        INT,BYTE,CHAR,DOUBLE
    }

    public static void add(byte [] arr, int pos, byte elem){
        if(arr.length >= pos)
            throw new IllegalArgumentException();
        arr[pos] = elem;
    }

    public static int [] resize(int size, int ...arr){
        int [] tempArr = new int[size];
        for(int i = 0; i < arr.length; i++) {
            tempArr[i] = arr[i];
        }
        return tempArr;
    }

    public static byte [] resize(int size, byte[] arr){
        byte [] tempArr = new byte[size];
        for(int i = 0; i < arr.length; i++) {
            tempArr[i] = arr [i];
        }
        return tempArr;
    }

    public static<T> T [] resize(int size,T [] arr){
        T [] tempArr = (T[]) new Object [size];
        for(int i = 0; i < arr.length; i++) {
           tempArr[i] = arr[i];
        }
       return tempArr;
    }

    private static class Test{
        public static void main(String...args){
            new Test().testPrimitiveTypes();
        }

        private void testPrimitiveTypes(){
            byte[] bytes = new byte[]{1,2,3};
            bytes = ArrayUtil.resize(5,bytes);
            System.out.println(bytes.length);
        }
    }
}
