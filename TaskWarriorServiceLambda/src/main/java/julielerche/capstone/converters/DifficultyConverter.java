package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Difficulty;

public class DifficultyConverter {
    /**
     * Converts a string to the difficulty enum.
     * @param difficultyString the string to convert.
     * @return the difficulty enum.
     */
    public Difficulty stringToDifficulty(String difficultyString) {
        switch (difficultyString) {
            case "EASY":
                return Difficulty.EASY;
            case "MEDIUM":
                return Difficulty.MEDIUM;
            case "HARD":
                return Difficulty.HARD;
            default:
                return null;
        }
    }
}
