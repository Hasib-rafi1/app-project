package helper;

public class RandomNumber {

    public static int getRandomNumberInRange(int min, int max) {
        if(min == max)
            return min;

        if (min > max) {
            throw new IllegalArgumentException("Max value must be greater than Min value!");
        }
        java.util.Random r = new java.util.Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
