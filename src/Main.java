import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Adventure advent = LinkParse.adventure;
    private static final int ITEM_SUBSTRING_SHIFT = 5;
    private static final int MOVE_SUBSTRING_SHIFT = 3;
    private static ArrayList<String> carryItems = new ArrayList<>();

    /**
     *
     * @param check the String that the user inputs
     * @param currentRoom the room the user is currently in
     * @return whether or not the game continues with a non-exit command
     */
    public static boolean goOn(String check, String currentRoom) {
        if (check.contains("exit") || check.contains("quit") || advent.getEndingRoom().equalsIgnoreCase(currentRoom)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param check the room that the user is in
     * @return special messages based on the room
     */
    public static String specialRoom(String check) {
        if (check.equalsIgnoreCase(advent.getStartingRoom())) {
            return "Your journey begins here";
        } else if (check.equalsIgnoreCase(advent.getEndingRoom())) {
            return "You have reached your final destination";
        }
        else {
            return "";
        }
    }

    /**
     *
     * @param currentRoom where the player currently is
     * @return the available valid moves
     */
    public static String movesAvailable(String currentRoom) {
        int index = getIndex(currentRoom);

        //get the directions you can move and append each to the string
        String moveOptions = "";
        int sizeLength = advent.getRooms()[index].getDirections().length;

        if(sizeLength == 1) {
            moveOptions += (advent.getRooms()[index].getDirections()[0].getDirectionName());
        } else {
            for (int i = 0; i < sizeLength - 1; i++) {
                moveOptions += (advent.getRooms()[index].getDirections()[i].getDirectionName() + ", ");
            }
            moveOptions += ("or " + advent.getRooms()[index].getDirections()[sizeLength].getDirectionName());
        }
        return "From here, you can go: " + moveOptions;
    }

    /**
     *
     * @param currentRoom the room the player is currently in
     * @return the items available in the room
     */
    public static String itemCheck(String currentRoom) {
        int index = getIndex(currentRoom);

        //get the items present and append each to the string depending on the size of the length of the arraylist
        StringBuilder itemsAvailable = new StringBuilder();
        int sizeLength = advent.getRooms()[index].getItems().size();

        if (sizeLength == 0) {
            itemsAvailable.append("Nothing");
        } else if (sizeLength == 1) {
            itemsAvailable.append(advent.getRooms()[index].getItems().get(0));
        } else {
            for (int i = 0; i < sizeLength - 1; i++) {
                itemsAvailable.append(advent.getRooms()[index].getItems().get(i) + ", ");
            }
            itemsAvailable.append("or " + advent.getRooms()[index].getItems().get(sizeLength - 1));
        }
        return " " + itemsAvailable;
    }

    /**
     *
     * @param currentRoom the current room the user is in
     * @return the index of the room in the Rooms[]
     */
    private static int getIndex(String currentRoom) {
        int index = 0;

        //find the index of the corresponding room to the rooms[]
        for (int i = 0; i < advent.getRooms().length; i++) {
            if(advent.getRooms()[i].getName().equalsIgnoreCase(currentRoom)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     *
     * @param input the item that the user wants to either drop or carry
     * @param currentRoom the current room of the user
     * @return a string of the updated items the user is carrying
     */
    public static String itemGetLeave(String input, String currentRoom) {
        int index = getIndex(currentRoom);
        String modified = input.toLowerCase().substring(ITEM_SUBSTRING_SHIFT);

        if(input.contains("take")) {
            for(int i = 0; i < advent.getRooms()[index].getItems().size(); i++) {
                if(advent.getRooms()[index].getItems().get(i).equalsIgnoreCase(input.substring(ITEM_SUBSTRING_SHIFT))) {
                    advent.getRooms()[index].getItems().remove(i);
                    carryItems.add(modified);
                    return "You are carrying: " + carryItems.toString();
                }
            }
        } else if(input.contains("drop")) {
            for(int i = 0; i < advent.getRooms()[index].getItems().size(); i++) {
                if(advent.getRooms()[index].getItems().get(i).equalsIgnoreCase(input.substring(ITEM_SUBSTRING_SHIFT))) {
                    advent.getRooms()[index].getItems().add(input);
                    carryItems.remove(modified);
                    return "You are carrying: " + carryItems.toString();
                }
            }
        } else {
            return "I can't" + input;
        }
        return null;
    }

    public static boolean validMove(String move, String currentRoom) {
        int index = getIndex(currentRoom);
        for(int i = 0; i < LinkParse.adventure.getRooms()[index].getDirections().length; i++) {
            if (LinkParse.adventure.getRooms()[index].getDirections()[i].getDirectionName().
                    equalsIgnoreCase(move.substring(MOVE_SUBSTRING_SHIFT))) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param move the place the user wants to move
     * @param currentRoom the room that the user is currently in
     * @return the new room the user is in
     */
    public static String moved(String move, String currentRoom) {
        int index = getIndex(currentRoom);
        for(int i = 0; i < LinkParse.adventure.getRooms()[index].getDirections().length; i++) {
            if (LinkParse.adventure.getRooms()[index].getDirections()[i].getDirectionName().
                    equalsIgnoreCase(move.substring(MOVE_SUBSTRING_SHIFT))) {
                return LinkParse.adventure.getRooms()[index].getDirections()[i].getRoom();
            }
        }
        return null;
    }

    /**
     *
     * @param currentRoom the room the user is in currently
     * @return the description of the room
     */
    public static String describe(String currentRoom) {
        int index = getIndex(currentRoom);
        return LinkParse.adventure.getRooms()[index].getDescription();
    }

    public static int decideNextFunction(String move) {
        String modified = move.toLowerCase();
        if (modified.contains("take ") || modified.contains("drop ")) {
            return 1;
        } else if(modified.contains("go ")) {
            return -1;
        }
        return 0;
    }

    //--------------------
    //--------------------
    //--MAIN METHOD HERE--
    //--------------------
    //--------------------

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
       // LinkParse.makeApiRequest("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
        String currentRoom = advent.getStartingRoom();

        String input = scan.nextLine();
        boolean canGoOn = goOn(input, currentRoom);

        advent.getRooms()[0].getDescription();

        while (canGoOn) {
            //check if you are in a special room
            specialRoom(currentRoom);

            //check for items
            itemCheck(currentRoom);

            //get directions for moves
            movesAvailable(currentRoom);

            //see what the person wants to do: something with items or moving?
            String move = scan.nextLine();
            int decision = decideNextFunction(move);

            if(decision == 1) {
                //get what the person wants to do with the items
                itemGetLeave(move, currentRoom);
            } else if (decision == -1) {
                boolean canMove = validMove(move, currentRoom);
                if(canMove) {
                    currentRoom = moved(move, currentRoom);
                }
            } else {
                System.out.println("I can't: " + move);
            }

            boolean canMove = validMove(move, currentRoom);
            if(canMove) {
                currentRoom = moved(move, currentRoom);
            }

            //check before the loop iterates again
            canGoOn = goOn(scan.nextLine(), currentRoom);
            describe(currentRoom);
        }
    }
}
