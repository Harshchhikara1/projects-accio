/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Demogame;

import javax.swing.JFrame;

/**
 *
 * @author LENOVO
 */
public class MainClass {
 
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setTitle("Brick Breaker");
        f.setSize(700,600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        
        Gameplay gameplay= new Gameplay();
         f.add(gameplay);
        
    }
}
