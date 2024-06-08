package julielerche.capstone.converters;

import julielerche.capstone.dynamodb.models.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

public class DifficultyConverterTest {
    private DifficultyConverter difficultyConverter;

    @BeforeEach
    void setUp() {
        difficultyConverter = new DifficultyConverter();
    }

    @Test
    public void stringToDifficulty_givenEasyString_returnsCorrectDifficulty() {
        //given
        String easy = "EASY";

        //when
        Difficulty result = difficultyConverter.stringToDifficulty(easy);

        //then
        assertEquals(Difficulty.EASY, result);
    }

    @Test
    public void stringToDifficulty_givenMediumString_returnsCorrectDifficulty() {
        //given
        String medium = "MEDIUM";

        //when
        Difficulty result = difficultyConverter.stringToDifficulty(medium);

        //then
        assertEquals(Difficulty.MEDIUM, result);
    }

    @Test
    public void stringToDifficulty_givenHardString_returnsCorrectDifficulty() {
        //given
        String hard = "HARD";

        //when
        Difficulty result = difficultyConverter.stringToDifficulty(hard);

        //then
        assertEquals(Difficulty.HARD, result);
    }
}
