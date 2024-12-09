/*-
 * #%L
 * GameOfLife
 * %%
 * Copyright (C) 2024 Zespol1
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.team1.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {
    GameOfLifeCell cell;
    List<GameOfLifeCell> exampleNeighbors = Arrays.asList(new GameOfLifeCell[8]);

    @BeforeEach
    void createGameOfLifeCell() {
        cell = new GameOfLifeCell(true);

        exampleNeighbors.set(0, new GameOfLifeCell(false));
        exampleNeighbors.set(1, new GameOfLifeCell(false));
        exampleNeighbors.set(2, new GameOfLifeCell(false));
        exampleNeighbors.set(3, new GameOfLifeCell(false));
        exampleNeighbors.set(4, new GameOfLifeCell(false));
        exampleNeighbors.set(5, new GameOfLifeCell(false));
        exampleNeighbors.set(6, new GameOfLifeCell(false));
        exampleNeighbors.set(7, new GameOfLifeCell(false));
    }

    @Test
    void returnValueTest() {
        assertTrue(cell.getValue());
    }

    @Test
    void setValuesTest() {
        cell.updateState(false);
        assertFalse(cell.getValue());
    }

    @Test
    void setNeighborsTest() {

        cell.setNeighbors(exampleNeighbors);

        // Porówanie czy sąsiedzi są ustawieni poprawnie
        for (int i = 0; i < exampleNeighbors.size(); i++) {
            assertEquals(cell.getNeighbors().get(i).getValue(), exampleNeighbors.get(i).getValue());
        }

    }

    @Test
    void nextStateTest1() {
        cell.setNeighbors(exampleNeighbors);
        assertFalse(cell.nextState());
    }

    @Test
    void nextStateTest2() {
        exampleNeighbors.set(0, new GameOfLifeCell(true));
        exampleNeighbors.set(1, new GameOfLifeCell(true));
        exampleNeighbors.set(2, new GameOfLifeCell(true));
        exampleNeighbors.set(3, new GameOfLifeCell(true));
        exampleNeighbors.set(4, new GameOfLifeCell(true));
        exampleNeighbors.set(5, new GameOfLifeCell(true));
        exampleNeighbors.set(6, new GameOfLifeCell(true));
        exampleNeighbors.set(7, new GameOfLifeCell(true));
        cell.setNeighbors(exampleNeighbors);
        assertFalse(cell.nextState());
    }

    @Test
    void nextStateTest3() {
        exampleNeighbors.set(0, new GameOfLifeCell(true));
        exampleNeighbors.set(1, new GameOfLifeCell(true));
        exampleNeighbors.set(2, new GameOfLifeCell(false));
        exampleNeighbors.set(3, new GameOfLifeCell(false));
        exampleNeighbors.set(4, new GameOfLifeCell(false));
        exampleNeighbors.set(5, new GameOfLifeCell(false));
        exampleNeighbors.set(6, new GameOfLifeCell(false));
        exampleNeighbors.set(7, new GameOfLifeCell(false));
        cell.setNeighbors(exampleNeighbors);
        assertTrue(cell.nextState());
    }

    @Test
    void nextStateTest4() {
        cell.updateState(false);

        exampleNeighbors.set(0, new GameOfLifeCell(true));
        exampleNeighbors.set(1, new GameOfLifeCell(true));
        exampleNeighbors.set(2, new GameOfLifeCell(true));
        exampleNeighbors.set(3, new GameOfLifeCell(false));
        exampleNeighbors.set(4, new GameOfLifeCell(false));
        exampleNeighbors.set(5, new GameOfLifeCell(false));
        exampleNeighbors.set(6, new GameOfLifeCell(false));
        exampleNeighbors.set(7, new GameOfLifeCell(false));
        cell.setNeighbors(exampleNeighbors);
        assertTrue(cell.nextState());
    }

    @Test
    void hashCodeTest(){
        cell.setNeighbors(exampleNeighbors);
        int output = cell.hashCode();
        int expectedOutput = cell.hashCode();
        assertEquals(output, expectedOutput);
    }

    @Test
    void hashCodeTest2(){
        cell.setNeighbors(exampleNeighbors);
        int output = cell.hashCode();
        cell.updateState(false);
        int expectedOutput = cell.hashCode();
        assertNotEquals(output, expectedOutput);
    }

    @Test
    void equalsTest(){
        cell.setNeighbors(exampleNeighbors);
        GameOfLifeCell Cell2 = new GameOfLifeCell(true);
        Cell2.setNeighbors(exampleNeighbors);
        assertTrue(cell.equals(Cell2));
    }

    @Test
    void equalsTest2(){
        cell.setNeighbors(exampleNeighbors);
        GameOfLifeCell Cell2 = new GameOfLifeCell(true);
        Cell2.setNeighbors(exampleNeighbors);
        Cell2.updateState(false);
        assertFalse(cell.equals(Cell2));
    }

    @Test
    void equalsTest3(){
        cell.setNeighbors(exampleNeighbors);
        GameOfLifeCell Cell2 = new GameOfLifeCell(true);
        exampleNeighbors.set(0, new GameOfLifeCell(true));
        Cell2.setNeighbors(exampleNeighbors);
        assertFalse(cell.equals(Cell2));
    }

    @Test
    void equalsTest4(){
        cell.setNeighbors(exampleNeighbors);
        assertFalse(cell.equals(new Object()));
    }

    @Test
    void toStringTest(){
        cell.setNeighbors(exampleNeighbors);
        String output = cell.toString();
        String expectedOutput = "GameOfLifeCell{Stan komorki=true, Lista sasiadow=[false, false, false, false, false, false, false, false]}";
        assertEquals(output, expectedOutput);
    }

    @Test
    void toStringTest2(){
        exampleNeighbors.set(0, null);
        cell.setNeighbors(exampleNeighbors);
        String output = cell.toString();
        String expectedOutput = "GameOfLifeCell{Stan komorki=true, Lista sasiadow=[null, false, false, false, false, false, false, false]}";
        assertEquals(output, expectedOutput);
    }

    @Test
    void cloneTest() {
        GameOfLifeCell clone = cell.clone();
        assertEquals(cell.getValue(), clone.getValue());
    }

    @Test
    void cloneTest2() {
        GameOfLifeCell clone = cell.clone();
        assertEquals(cell.getNeighbors(), clone.getNeighbors());
    }

    @Test
    void compareToTest() {
        GameOfLifeCell cell1 = new GameOfLifeCell(true);
        assertEquals(0, this.cell.compareTo(cell1));
    }

    @Test
    void compareToTest2() {
        GameOfLifeCell cell1 = new GameOfLifeCell(false);
        assertEquals(1, this.cell.compareTo(cell1));
    }

    @Test
    void compareToTest3() {
        GameOfLifeCell cell1 = new GameOfLifeCell(false);
        assertEquals(-1, cell1.compareTo(this.cell));
    }

}
