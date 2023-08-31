/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room parking, entry, restrooms, square, cinema;
      
        // create the rooms
        parking = new Room("in the parking lot"); // Estacionamento
        entry = new Room("At the main entrance, a large and extensive space!"); // Entrada Principal
        restrooms = new Room("near the restrooms, east of the main entrance"); // Banheiros
        square = new Room("in the food court, south of the main entrance!"); // Praça de alimentação
        cinema = new Room("West towards the food court, where you saw the cinema!"); // Cinema
        
        // initialise room exits
        parking.setExit("east", entry);
        entry.setExit("east", restrooms);
        restrooms.setExit("west", entry);
        entry.setExit("west", parking);
        entry.setExit("south", square);
        square.setExit("west", cinema);
        square.setExit("north", entry);
        cinema.setExit("east", square);


        Item car1 = new Item("car blue",100000); //Carro 1
        parking.addItem(car1);
        Item car2 = new Item("car red",20000); // Carro 2
        parking.addItem(car2);

        Item table = new Item("table",30); // Mesa
        entry.addItem(table);

        Item faucet = new Item("faucet", 3); //Torneira para mãos
        restrooms.addItem(faucet);

        Item cash_registero = new Item("cash register",8); // caixa registradora
        square.addItem(cash_registero);

        Item icecream_machine = new Item("ice cream machine", 400); // Maquina de sorvete
        square.addItem(icecream_machine);

        Item popcorn_machine = new Item("popcorn machine",200); // Maquina de pipoca
        cinema.addItem(popcorn_machine);




        currentRoom = parking;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if(commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("eat")){
            eat();
        }
        else if(commandWord.equals("back")){
            if(command.hasSecondWord()){
                System.out.println("no two words are needed for the back command");
            }
            else back();
        }


        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        previousRoom = currentRoom;

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());

        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void printLocation(){
        System.out.println("You are " + currentRoom.getDescription());
        System.out.println(currentRoom.getExitString());
    }
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat(){
        System.out.println("You have eaten now and you are not hungry any more.");
    }

    private void back(){
        if(previousRoom !=null){
            currentRoom = previousRoom;
            previousRoom = null;
            System.out.println(currentRoom.getLongDescription());
        }
        else{
            System.out.println("There are no more exits");
            System.out.println(currentRoom.getLongDescription());
        }
    }
}
