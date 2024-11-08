import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HippodromeTest {

    @Test
    @DisplayName("Test constructor")
    void constructorTest(){
        List<Horse> list = new ArrayList<>();
        IllegalArgumentException exception;
        //test for cannotBeNull
        exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
        //test for cannotBeEmptyList
        exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String name = "hourse" + i;
            list.add(new Horse(name, 4, 32));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        assertEquals(list, hippodrome.getHorses());
        hippodrome = null;

    }

    @Test
    void move() {
        List<Horse> mockList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockList.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(mockList);
        hippodrome.move();
        for (Horse horse : mockList){
            Mockito.verify(horse, Mockito.times(1)).move();
        }


    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("Lida", 32, 32);
        Horse horse2 = new Horse("Anya", 1, 32);
        Hippodrome hippodrome = new Hippodrome(new ArrayList<>(){{
            add(horse1);
            add(horse2);
        }});
        assertEquals(horse1, hippodrome.getWinner());

    }
}