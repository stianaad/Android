package com.example.tetris;

public class TetrisPieces {

    public TetrisPieces(){

    }

    public static int[][] I(){
        int[][] i = {{1},
                     {1},
                     {1},
                     {1}};
        return i;
    }

    public static int[][] ITransponert(){
        int[][] i = {{1,1,1,1}};
        return i;
    }

    public static int[][] S(){
        int[][] i = {{0,1,1},
                     {1,1,0}};
        return i;
    }

    public static int[][] SRot90(){
        int[][] i = {{1,0},
                     {1,1},
                     {0,1}};
        return i;
    }

    public static int[][] L(){
        int[][] i = {{1,0},
                     {1,0},
                     {1,1}};
        return i;
    }

    public static int[][] LRot90(){
        int[][] i = {{0,0,1},
                     {1,1,1}};
        return i;
    }

    public static int[][] LRot180(){
        int[][] i = {{1,1},
                    {0,1},
                    {0,1}};
        return i;
    }

    public static int[][] LRot270(){
        int[][] i = {{1,1,1},
                    {1,0,0}};
        return i;
    }

    public static int[][] firkant(){
        int[][] i = {{1,1},
                     {1,1}};
        return i;
    }

}
