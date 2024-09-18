package application;

import db.DB;
import view.Menu;

public class Program {

    public static void main(String[] args) {
    	
        DB.createTables();   
        

        Menu menu = new Menu();   
        menu.mainMenu();
        
    }
}
