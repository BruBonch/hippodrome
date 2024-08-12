import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @Test
    public void throwException_whenNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));
    }

    // подумать о целесообразности
    @Test
    public void checkMessage_whenThrowException_forNullName() {
        try {
            new Horse(null, 1);
        } catch (RuntimeException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t", "\n"})
    public void throwException_whenNameEmptyString(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t", "\n"})
    public void checkMessage_whenThrowException_forEmptyName(String name) {
        try {
            new Horse(name, 1);
        } catch (RuntimeException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void throwException_whenSpeedNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("BoJack", -1));
    }

    @Test
    public void checkMessage_whenThrowException_forNegativeSpeed() {
        try {
            new Horse("BoJack", -1);
        } catch (RuntimeException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void throwException_whenDistanceNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("BoJack", 1, -1));
    }

    @Test
    public void checkMessage_whenThrowException_forNegativeDistance() {
        try {
            new Horse("BoJack", 1, -1);
        } catch (RuntimeException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName_returnNamePassedConstructor() {
        String expectedName = "BoJack";
        Horse horse = new Horse(expectedName, 1);
        String actualName = horse.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    public void getSpeed_returnSpeedPassedConstructor() {
        double expectedSpeed = 1;
        Horse horse = new Horse("BoJack", expectedSpeed);
        double actualSpeed = horse.getSpeed();

        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    public void getDistance_returnDistancePassedConstructor() {
        double expectedDistance = 4;
        Horse horse = new Horse("BoBoJackb", 1, expectedDistance);
        double actualDistance = horse.getDistance();

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void getDistance_returnZero_whenConstructorWithTwoArg() {
        Horse horse = new Horse("BoJack", 1);

        assertEquals(0, horse.getDistance());
    }

    @Test
    public void InvokingGetRandomDouble_into_moveMethod() {

        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("BoJack", 2);
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5})
    public void checkDistanceCalculation(double arg) {
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("BoJack", 2);
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(arg);
            double expectedDistance = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);

            horse.move();

            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
