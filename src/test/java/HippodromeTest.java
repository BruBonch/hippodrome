import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HippodromeTest {
    @Test
    public void throwException_whenHorsesNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void checkMessage_whenThrowException_forNullHorses() {
        try {
            Hippodrome hippodrome = new Hippodrome(null);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    public void throwException_whenHorsesListEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void checkMessage_whenThrowException_forEmptyHorsesList() {
        try {
            Hippodrome hippodrome = new Hippodrome(new ArrayList<>());
        } catch (RuntimeException e) {
            Assertions.assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    // подумать над названием
    public void getHorses() {
        List<Horse> horsesList = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            horsesList.add(new Horse("Horse N_" + i, Horse.getRandomDouble(0.2, 0.9)));
        }

        Hippodrome hippodrome = new Hippodrome(horsesList);
        // теперь сравнение будет по спискам находящимся в разных областях в памяти
        List<Horse> expectedList = new ArrayList<>(horsesList);

        Assertions.assertEquals(expectedList, hippodrome.getHorses());
    }

    @Test
    public void checkMoveMethodCall() {
        List<Horse> mockedHorsesList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            Horse mockedHorse = Mockito.
                    spy(new Horse("Horse N_" + i, Horse.getRandomDouble(0.2, 0.9)));

            mockedHorsesList.add(mockedHorse);
        }

        Hippodrome hippodrome = new Hippodrome(mockedHorsesList);
        hippodrome.move();

        for(Horse mockedHorse : mockedHorsesList) {
            Mockito.verify(mockedHorse).move();
        }
    }

    @Test
    void returnHorseWithLongestDistance() {
        List<Horse> horses = List.of(
                new Horse("BoJack", 2.1, 13),
                new Horse("igogo", 2.3, 8.2),
                new Horse("Zephyr", 3.5, 3.5),
                new Horse("Theo", 1.2, 2.3)
        );

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse expectedHorse = horses.stream().max(Comparator.comparing(horse -> horse.getDistance())).get();
        Horse actualHorse = hippodrome.getWinner();

        Assertions.assertEquals(expectedHorse, actualHorse);
    }
}
