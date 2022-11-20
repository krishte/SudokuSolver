/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author krishte
 */
public class sudokuGenerator{
    int[][] grid = new int[9][9];
    Random rand = new Random();
    
    
  
    
    public int[][] create()
    {
        int storedValue;

        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 9; i++)
        {
            values.add(i);
        }
        for (int i = 0; i <= 8; i++)
        {
            storedValue = rand.nextInt(values.size());
            grid[0][i] = values.get(storedValue);
            values.remove(storedValue);
            
        }
        
        
        return grid;
    }
    
}
