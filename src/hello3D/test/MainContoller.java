/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello3D.test;


public class MainContoller  {


    public static void main(String[] args) {
        //create 3D view
        Island island = new Island();
        
        //create application layout
        LayoutGenerator layout = new LayoutGenerator(island);
        layout.swingHandler();
    }

}
