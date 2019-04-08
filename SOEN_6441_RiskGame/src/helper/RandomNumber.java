package helper;


/**
 * This class generates a random number to be used for the Random Strategy
 * 
 * @author Jaiganesh
 * @version 1.0.0
 */
public class RandomNumber {

	/**
	 * Method to generate a random number 
	 * @param min minimum numbers
	 * @param max maximum numbers
	 * @return random value
	 */
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
