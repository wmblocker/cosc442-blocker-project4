package edu.towson.cis.cosc442.project4.coffeemaker;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Starts the console UI for the CoffeeMaker
 * @author Josh
 * @version $Revision: 1.0 $
 */
public class Main {
    private static CoffeeMaker coffeeMaker;
    private static String name;
    private static int price;
    private static int amtCoffee;
    private static int amtMilk;
    private static int amtSugar;
    private static int amtChocolate;
    private static Scanner input = new Scanner(System.in);
    
    public static void mainMenu() {
        System.out.println("1. Add a recipe");
        System.out.println("2. Delete a recipe");
        System.out.println("3. Edit a recipe");
        System.out.println("4. Add inventory");
        System.out.println("5. Check inventory");
        System.out.println("6. Make coffee");
        System.out.println("0. Exit");
        System.getProperty("line.separator");
        
        //Get user input
        int userInput = Integer.parseInt((inputOutput("Please press the number that corresponds to what you would like the coffee maker to do.")));
        
        menuDecision(userInput);

    }
    
    public static void menuDecision(int userInput){
        switch(userInput){
        case 1:
        	addRecipe();
        	break;
        case 2:
        	deleteRecipe();
        	break;
        case 3:
        	editRecipe();
        	break;
        case 4:
        	addInventory();
        	break;
        case 5:
        	checkInventory();
        	break;
        case 6:
        	makeCoffee();
        	break;
        default:
        	System.exit(0);
        }
    }
    
    public static int getInput(){
    	int userInput = input.nextInt();
    	while(!input.hasNextInt()){
    		System.out.println();
    	}
    	return userInput;
    }
    
    public static void getAmounts(){
	    name = inputOutput(System.getProperty("line.separator")+"Please enter the recipe name: ");
	    
	    //Read in recipe price
	    System.out.println("Please enter the recipe price: $");
	    price = getInput();
	    
	    //Read in amt coffee
	    System.out.println("Please enter the units of coffee in the recipe: ");
	    amtCoffee = getInput();
	    
	    //Read in amt milk
	    System.out.println("Please enter the units of milk in the recipe: ");
	    amtMilk = getInput();
	    
	    //Read in amt sugar
	    System.out.println("Please enter the units of sugar in the recipe: ");
	    amtSugar = getInput();
	    
	    //Read in amt chocolate
	    System.out.println("Please enter the units of chocolate in the recipe: ");
	    amtChocolate = getInput();
    }
    
	public static void addRecipe() {    
	    getAmounts();
	    boolean recipeAdded = false;
		Recipe r = new Recipe();
		r.setName(name);
		r.setPrice(price);
		r.setAmtCoffee(amtCoffee);
		r.setAmtMilk(amtMilk);
		r.setAmtSugar(amtSugar);
		r.setAmtChocolate(amtChocolate);
		   
		recipeAdded = coffeeMaker.addRecipe(r);
	    
	    if(recipeAdded){
	    	System.out.println(name + " successfully added.");
	    }
	    else{
	    	System.out.println(name + "could not be added.");
	    }
	    
	    mainMenu();
    }
    
    public static void deleteRecipe() {
        Recipe [] recipes = coffeeMaker.getRecipes();
        for(int i = 0; i < recipes.length; i++) {
            System.out.println((i+1) + ". " + recipes[i].getName());
        }
        System.out.println("Please select the number of the recipe to delete.");
        int recipeToDelete = getInput() - 1; 
        
        boolean recipeDeleted = coffeeMaker.deleteRecipe(recipes[recipeToDelete]);
        
        if(recipeDeleted){
        	System.out.println(recipes[recipeToDelete].getName() + " successfully deleted.");
        }
	    else{
	    	System.out.println(recipes[recipeToDelete].getName() + "could not be deleted.");
	    }
        
        mainMenu();
    }
    
    public static void editRecipe() {
        Recipe [] recipes = coffeeMaker.getRecipes();
        for(int i = 0; i < recipes.length; i++) {
            System.out.println((i+1) + ". " + recipes[i].getName());
        }
        System.out.println("Please select the number of the recipe to edit.");
        int recipeToEdit = getInput();
        
        Recipe oldRecipe = recipes[recipeToEdit];        
        getAmounts();    
	    Recipe newRecipe = new Recipe();
	    newRecipe.setName(name);
	    newRecipe.setPrice(price);
	    newRecipe.setAmtCoffee(amtCoffee);
	    newRecipe.setAmtMilk(amtMilk);
	    newRecipe.setAmtSugar(amtSugar);
	    newRecipe.setAmtChocolate(amtChocolate);
        
        boolean recipeEdited = coffeeMaker.editRecipe(oldRecipe, newRecipe);
        
        if(recipeEdited){
        	System.out.println(oldRecipe.getName() + " successfully edited.");
        }
	    else{
	    	System.out.println(oldRecipe.getName() + "could not be edited.");
	    }
        
        mainMenu();
    }
    
    public static void addInventory() {
	    getAmounts();
	    
        coffeeMaker.addInventory(amtCoffee, amtMilk, amtSugar, amtChocolate);
        mainMenu();
    }
    
    public static void checkInventory() {
        Inventory inventory = coffeeMaker.checkInventory();
        System.out.println(inventory.toString());
        mainMenu();
    }
    
    public static void makeCoffee() {
        Recipe [] recipes = coffeeMaker.getRecipes();
        for(int i = 0; i < recipes.length; i++) {
            System.out.println((i+1) + ". " + recipes[i].getName());
        }
        System.out.println("Please select the number of the recipe to purchase.");
        int recipeToPurchase = getInput() - 1;
        
        System.out.println("Please enter the amount you wish to pay");
        int amountToPay = getInput();
        
        Recipe recipe = recipes[recipeToPurchase];
        int change = coffeeMaker.makeCoffee(recipe, amountToPay);
        
        System.out.println("Your change is: " + change + System.getProperty("line.separator"));
        mainMenu();
    }
    
    /**
     * Method inputOutput.
     * @param message String
     * @return String
     */
    public static String inputOutput(String message) {
        System.out.println(message);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String returnString = "";
	    try {
	        returnString = br.readLine();
	    }
	    catch (IOException e){
	        System.out.println("Error reading in value");
	        mainMenu();
	    }
	    return returnString;
    }
    
    /**
     * Method stringToInt.
     * @param value String
     * @return int
     */
    private static int stringToInt(String value) {
        int returnInt = -1;
        try {
            returnInt = Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            System.out.println("Please input an integer");
            System.getProperty("line.separator");
        }
        return returnInt;
    }
    
    /**
     * Method main.
     * @param args String[]
     */
    public static void main(String[] args) {
	    coffeeMaker = new CoffeeMaker();
	    System.out.println("Welcome to the CoffeeMaker!"+System.getProperty("line.separator"));
	    mainMenu();
	}
}
