import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.exceptions.base.MockitoException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HorseTest {
    Horse horse;
    @BeforeEach
    void setUp(){
        horse = new Horse("Denis", 14, 43);
    }

    @AfterEach
    void close(){
        horse = null;
    }

    @ParameterizedTest
    @DisplayName("Constructor test")
    @MethodSource("data")
    void constructorTest(String name){
        IllegalArgumentException exception;
        //test for name nullName
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 14, 32));
        assertEquals("Name cannot be null.", exception.getMessage());
        //test for blankName
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 13, 25));
        assertEquals("Name cannot be blank.", exception.getMessage());
        //test for negativeSpeed
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse("dasda", -3, 13));
        assertEquals("Speed cannot be negative.", exception.getMessage());
        //test for negativeDistance
        exception = assertThrows(IllegalArgumentException.class, () -> new Horse("dasda", 3, -13));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    Stream<Arguments> data(){
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("  ")
        );
    }


    @Test
    void getName() {
        assertEquals("Denis", horse.getName());
    }


    @Test
    void getSpeed() {
        assertEquals(14, horse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(43, horse.getDistance());
        assertEquals(0, new Horse("vova", 13).getDistance());
    }

    @ParameterizedTest
    @CsvSource({
            "0.2, 0.9"
    })
    void move(Double a, Double b) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
            mockedStatic.when(() -> Horse.getRandomDouble(a, b)).thenReturn(3.0);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            assertEquals(85.0, horse.getDistance());
        }

    }
}