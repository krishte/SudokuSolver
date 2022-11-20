/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author krishte
 */
public class Sudoku extends Application {
    
        
    
       TextField[][] sudoku = new TextField[9][9];
       int[][] solvingSudoku = new int[9][9];

       int[][][] solvingBox = new int[9][9][10];
        private int za = 0;
        private int currentNode = 0;
        ArrayList <int[][][]> heapStorage = new ArrayList<>();
        int[][][] useless = new int[1][1][1];
        private int guesses = 0;



       
       

        
        GridPane gridPane = new GridPane();
    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Sudoku Solver");

        for(int i = 0;i <= 8; i ++)
        {

            for(int z = 0; z <= 8; z++)
            {   

                TextField t = new TextField();
                t.setTooltip(new Tooltip(Arrays.toString(solvingBox[i][z])));
                sudoku[z][i] = t;
                t.setAlignment(Pos.CENTER);
                t.setPrefHeight(50);
                gridPane.add(t, i+1, z+1, 1, 1);
            }
        }

        for (int i = 0; i <= 100000; i++)
        {
            heapStorage.add(useless);
        }

        Button c = new Button("solve");
        c.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gridPane.add(c, 1, 11, 9, 10);
        
        
        
        
        
        String a = "";
        c.setOnAction((ActionEvent event) -> {
            
            int ay = 0;
            
            for(int i = 0;i <= 8; i ++)
            {

                for(int z = 0; z <= 8; z++)
                {   
                    if (!"".equals(sudoku[i][z].getText()))
                    {
                        ay += 1;
                        solvingSudoku[i][z] = Integer.parseInt(sudoku[i][z].getText());
                    }


                }
            }

            if (ay < 17)
            {
                System.out.println("Your puzzle has multiple answers");
            }
            

            
            if (ay >= 17)
            {
                populate3D();
            }
            

       });
       

        
        
        

       

        gridPane.getColumnConstraints().add(new ColumnConstraints(0)); 
        for (int b = 0; b <= 8; b++)
        {
            gridPane.getColumnConstraints().add(new ColumnConstraints(50)); 
        }
        

        
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Scene scene = new Scene(gridPane, 550, 670);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void populate3D()
    {
        for(int i = 0;i <= 8; i ++)
        {

            for(int z = 0; z <= 8; z++)
            {   
                if (solvingSudoku[i][z] != 0)
                {
                    solvingBox[i][z][0] = solvingSudoku[i][z];
                }
                else
                {
                    for (int q = 1; q <= 9; q++)
                    {
                        solvingBox[i][z][q] = q;
                    }
                }


            }
        }
        initialCheckRowColumn();
    }

    public void initialCheckRowColumn()
    {
        
        int c;
        int d;
        for (int i = 0; i <= 80; i++)
        {
            for (int z = 0; z<=80;z++)
            {
                if ((z-(z%9))/9 != i % 9)
                {
                    if (solvingBox[(i-(i%9))/9][(z-(z%9))/9][0] != 0)
                    {
                        c = solvingBox[(i-(i%9))/9][(z-(z%9))/9][0];
                        if (solvingBox[(i-(i%9))/9][i%9][(z%9)+1] == c)
                        {
                            solvingBox[(i-(i%9))/9][i%9][(z%9)+1] = 0;
                        }
                    }
                }
                if ((z-(z%9))/9 != (i-(i%9))/9)
                {
                    if (solvingBox[(z-(z%9))/9][i%9][0] != 0)
                    {
                        d = solvingBox[(z-(z%9))/9][i%9][0];
                        if (solvingBox[(i-(i%9))/9][i%9][(z%9)+1] == d)
                        {
                            solvingBox[(i-(i%9))/9][i%9][(z%9)+1] = 0;
                        }
                    }
                }

                
            }
        }
        initialCheckBox();

    }
    
    public void initialCheckBox()
    {
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 0; i <= 2;i++)
        {
            for (int z = 0; z <= 2; z++)
            {
                for (int a = 0; a <= 2; a++)
                {
                    for (int q = 0; q <= 2; q++)
                    {
                        if (solvingBox[3*i + a][3*z + q][0] != 0)
                        {
                            d.add(solvingBox[3*i + a][3*z + q][0]);
                        }
                    }
                }
                for (int a = 0; a <= 2; a++)
                {
                    for (int q = 0; q <= 2; q++)
                    {
                        for (int x = 1; x <= 9; x++)
                        {

                            for (Integer s : d) 
                            {
                                if (solvingBox[3*i + a][3*z + q][x] == s)
                                {
                                    solvingBox[3*i + a][3*z + q][x] = 0;
                                }
                            }
                        }

                    }
                }
                d.clear();
                
            }
        }
        
        checkBox();

       
    }

    
    public void display()
    {
        for(int i = 0; i <= 8; i++)
        {
            for(int z = 0; z <= 8; z++)
            {
                
               if (solvingBox[i][z][0] != 0)
               {
                  sudoku[i][z].setText(String.valueOf(solvingBox[i][z][0]));   
               }

            }
        }
    }
    
    public void finalCheck()
    {
        int numberCounter;
        ArrayList <Integer> row = new ArrayList<>();
        ArrayList <Integer> column = new ArrayList<>();
        ArrayList <Integer> boxValue = new ArrayList<>();
        ArrayList <Integer> preSet = new ArrayList<>();
        
        
        for (int a = 0; a <= 2; a++)
        {
            for (int i = 0; i <= 2 ; i++)
            {
                preSet.clear();
                boxValue.clear();
                for (int g = 0; g <= 2; g ++)
                {
                    for (int c = 0; c <= 2; c ++)
                    {
                        if (solvingBox[3*a + g][3*i + c][0] != 0)
                        {
                            boxValue.add(solvingBox[3*a + g][3*i + c][0]);

                        }
                        
                    }
                }

                for (int g = 1; g <= 9; g++)
                {
                    preSet.add(g);

                }

                for (int k = 0; k < boxValue.size(); k++)
                {
                    for (int g = 0; g < preSet.size(); g++)
                    {
                        if (Objects.equals(boxValue.get(k), preSet.get(g)))
                        {
                            preSet.remove(g);
                        }
                    }
                }

                    
                for (int k = 0; k < preSet.size(); k++)
                {
                    row.clear();
                    column.clear();
                    numberCounter = 0;
                    
                    for (int g = 0; g <= 2; g ++)
                    {
                        for (int c = 0; c <= 2; c ++)
                        {
                            for (int j = 1; j <= 9; j++)
                            {
                                if(preSet.get(k) == solvingBox[3*a+g][3*i+c][j])
                                {
                                    numberCounter += 1;
                                    row.add(3*a+g);
                                    column.add(3*i+c);
                                    
                                }
                            }

                        }
                    }
                    if (numberCounter <= 3)
                    {
                        if (row.size() == 2)
                        {
                            if (Objects.equals(row.get(0), row.get(1)))
                            {
                                for (int q = 0; q <= 8; q++)
                                {
                                    if (q != 3*i && q != 3*i + 1 && q != 3*i + 2)
                                    {
                                        solvingBox[row.get(0)][q][preSet.get(k)] = 0;
                                        
                                    }
                                    
                                }
                            }
                        }
                        else if (row.size() == 3)
                        {
                            if (Objects.equals(row.get(0), row.get(1))&& Objects.equals(row.get(1), row.get(2)))
                            {
                                for (int q = 0; q <= 8; q++)
                                {
                                    if (q != 3*i && q != 3*i + 1 && q != 3*i + 2)
                                    {
                                        solvingBox[row.get(0)][q][preSet.get(k)] = 0;
                                        
                                    }
                                    
                                }
                            }
                        }
                        if (column.size() == 2)
                        {
                            if (Objects.equals(column.get(0), column.get(1)))
                            {
                                for (int q = 0; q <= 8; q++)
                                {
                                    if (q != 3*a && q != 3*a + 1 && q != 3*a + 2)
                                    {
                                        solvingBox[q][column.get(0)][preSet.get(k)] = 0;
                                        
                                    }
                                    
                                }
                            }
                        }
                        else if (column.size() == 3)
                        {
                            if (Objects.equals(column.get(0), column.get(1))&& Objects.equals(column.get(1), column.get(2)))
                            {
                                for (int q = 0; q <= 8; q++)
                                {
                                    if (q != 3*a && q != 3*a + 1 && q != 3*a + 2)
                                    {
                                        solvingBox[q][column.get(0)][preSet.get(k)] = 0;
                                        
                                    }
                                    
                                }  
                            }
                        }
                        
                    }
                }
            }
        }
        enterValue();
    }
    
    
    
    
    public void enterValue()
    {
        int c;
        int d ;

        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z++)
            {
                c = 0;
                d = 0;
                for (int k = 1; k <= 9; k++)
                {
                    if (solvingBox[i][z][k] != 0)
                    {
                        c += 1;
                        d = k;
                    }
                }
                if (c == 1)
                {
                    za += 1;
                    solvingBox[i][z][0] = solvingBox[i][z][d];
                    solvingBox[i][z][d] = 0;
                }
            }
        }

        if (za >= 1)
        {
            initialCheckRowColumn();

        }
        else if (za == 0)
        {
            if (testRules() == true)
            {


                    if (checkFinish() == true)
                    {
                        display();
                        printOptions();
                    }
                    else if (checkFinish() == false)
                    {
                        guess();

                        for(int i = 0;i <= 8; i ++)
                        {

                            for(int z = 0; z <= 8; z++)
                            {   

                                sudoku[i][z].setTooltip(new Tooltip(Arrays.toString(solvingBox[i][z])));
                            }
                        }
                        
                        
                    }
                    
                    
                    
                    
                    
                
            }
            else if (testRules() == false)
            {
                fixMistake();



            }
        }
    }
    
    public boolean checkFinish()
    {
        int da = 0;
        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z ++)
            {
                if (solvingBox[i][z][0] != 0)
                {
                    da += 1;
                }
            }
        }
        return da == 81;
    
    }
    
        

    public boolean testRules()
    {
        int g;
        for (int i = 0; i <= 8; i++)
        {

            for (int z = 1; z <= 9; z++)
            {
                g = 0;
                for (int a = 0; a <= 8; a++ )
                {
                    if (solvingBox[i][a][0] == z)
                    {
                        g += 1;
                    }
                    
                }
                if (g >= 2)
                {
                    return false;
                }
            }
        }
        for (int i = 0; i <= 8; i++)
        {

            for (int z = 1; z <= 9; z++)
            {
                g = 0;
                for (int a = 0; a <= 8; a++ )
                {
                    if (solvingBox[a][i][0] == z)
                    {
                        g += 1;
                    }
                    
                }
                if (g >= 2)
                {
                    return false;
                }
            }
        }
        int counter;
        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z++)
            {
                counter = 0;
                for (int k = 0; k <= 9; k++)
                {
                    if (solvingBox[i][z][k] == 0)
                    {
                        counter += 1;
                    }
                    
                }
                if (counter == 10)
                {
                    return false;
                }
            }
        }
        
        
        
        
        return true;
    }
    
    public void guess()
    {
        guesses += 1;
        int rowValue = 0;
        int columnValue = 0;
        int zValue = 0;
        int counter;
        int[][][] storedBox = new int[9][9][10];


        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z++)
            {
                for (int k = 0; k <= 9; k++)
                {
                    storedBox[i][z][k]=solvingBox[i][z][k];
                }
            }
        }
        heapStorage.set(currentNode, storedBox);

        currentNode = 2*currentNode +2;
        


        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z++)
            {
                if (solvingBox[i][z][0] == 0)
                {
                    counter = 0;
                    for (int q = 1; q<=9; q++)
                    {
                        if (solvingBox[i][z][q] != 0)
                        {
                            counter += 1;
                        }
                    }
                    if (counter == 2)
                    {
                        rowValue = i;
                        columnValue = z;  
                    }
                }
            }
        }

        for (int i = 1; i <= 9; i++)
        {
            if(solvingBox[rowValue][columnValue][i] != 0)
            {
                zValue = i;
            }
        }


        solvingBox[rowValue][columnValue][0] = zValue;
        for (int i  = 1; i <= 9; i++)
        {
            solvingBox[rowValue][columnValue][i] = 0;
        }
        initialCheckRowColumn();
        
    }
    public void fixMistake()
    {
        int counter;
        int rowValue = 0;
        int columnValue = 0;
        int zValue = 0;
        int[][][] stored2Box = new int[9][9][10];

        
        
        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z++)
            {
                for (int k = 0; k <= 9; k++)
                {
                    stored2Box[i][z][k]=solvingBox[i][z][k];
                }
            }
        }
        heapStorage.set(currentNode, stored2Box);

        
        currentNode = (int) ((currentNode -1)/2);
        while (heapStorage.get(currentNode*2 + 1) != useless && heapStorage.get(currentNode*2 + 2) != useless)
        {
                    
            currentNode = (int) ((currentNode -1)/2);
        }

        solvingBox = heapStorage.get(currentNode);

        currentNode = 2*currentNode + 1;

        for (int i = 0; i <= 8; i++)
        {
            for (int z = 0; z <= 8; z++)
            {
                if (solvingBox[i][z][0] == 0)
                {
                    counter = 0;
                    for (int q = 1; q<=9; q++)
                    {
                        if (solvingBox[i][z][q] != 0)
                        {
                            counter += 1;
                        }
                    }
                    if (counter == 2)
                    {
                        rowValue = i;
                        columnValue = z;  
                    }
                }
            }
        }

        for (int i = 9; i >= 1; i--)
        {
            if(solvingBox[rowValue][columnValue][i] != 0)
            {
                zValue = i;
            }
        }
        solvingBox[rowValue][columnValue][0] = zValue;
        for (int i  = 1; i <= 9; i++)
        {
            solvingBox[rowValue][columnValue][i] = 0;
        }

        initialCheckRowColumn();
        
    }

    public void checkBox()
    {
       
        za = 0;
        ArrayList <Integer> ab = new ArrayList<>();
        ArrayList <Integer> ac = new ArrayList<>();

        int Integer = 0;
        int locationX = 0;
        int locationY = 0;
        for (int i = 0; i <= 2;i++)
        {
            for (int z = 0; z <= 2; z++)
            {
                ac.clear();
                for (int n = 1;n <= 9; n++)
                {
                    ac.add(n);
                }
                for (int a = 0; a <= 2; a++)
                {
                    for (int q = 0; q <= 2; q++)
                    {
                        if (solvingBox[3*i + a][3*z + q][0] != 0)
                        {
                            ab.add(solvingBox[3*i + a][3*z + q][0]);
                        }
                    }
                }

                for (int r = 0; r < ab.size(); r++)
                {
                    for (int u = 0; u < ac.size(); u++ )
                    {
                        if (Objects.equals(ab.get(r), ac.get(u)))
                        {
                            ac.remove(u);
                           
                        }
                    }
                }

                for (int y = 0; y < ac.size(); y++)
                {                              
                    for (int c = 0; c <= 2; c++)
                    {
                        for (int q = 0; q <= 2; q++)
                        {
                            if (solvingBox[3*i + c][3*z + q][ac.get(y)] == ac.get(y))
                            {
                                Integer += 1;
                                locationX = 3*i + c;
                                locationY = 3*z + q;
                            }
                        }
                    }
                    if (Integer == 1)
                    {
                        za+=1;
                        solvingBox[locationX][locationY][0] = ac.get(y);
                        for (int p = 1; p <= 9; p++)
                        {
                            solvingBox[locationX][locationY][p] = 0;
                        }
                    }
                    Integer = 0;
                }

                
                
                ab.clear();
            }
        }
        finalCheck();
    }

    public void printOptions()
    {
        for(int i = 0; i <= 8; i++)
        {
            for(int z = 0; z <= 8; z++)
            {
               System.out.println(Arrays.toString(solvingBox[i][z]));
            }
        }
        System.out.println("Guesses"+ guesses);
    }

            
}
