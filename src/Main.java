import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Adventure advent = LinkParse.adventure;
    private static final int ITEM_SUBSTRING_SHIFT = 5;
    private static ArrayList<String> carryItems = new ArrayList<>();

    /**
     *
     * @param check the String that the user inputs
     * @return whether or not the game continues with a non-exit command
     */
    public static boolean goOn(String check) {
        if (check.contains("exit") || check.contains("quit")) {
            return false;
        }
        return true;
    }

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
        StringBuilder moveOptions = new StringBuilder();
        int sizeLength = advent.getRooms()[index].getDirections().length;

        if(sizeLength == 1) {
            moveOptions.append(advent.getRooms()[index].getDirections()[0]);
        } else {
            for (int i = 0; i < sizeLength - 1; i++) {
                moveOptions.append(advent.getRooms()[index].getDirections()[i].getDirectionName() + ", ");
            }
            moveOptions.append("or " + advent.getRooms()[index].getDirections()[sizeLength].getDirectionName());
        }
        return "From here, you can go:" + moveOptions;
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
        return "This room contains:" + itemsAvailable;
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
     * @param item that we want to either add or remove from the list
     * @param decide if positive, then add to the list, else, remove from the list
     * @return
     */
    private static ArrayList<String> carryItemUpdate(String item, int decide) {
        if (decide > 0) {
            carryItems.add(item);
        } else {
            carryItems.remove(item);
        }
        return carryItems;
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
                    return "You are carrying: " + carryItemUpdate(modified, 1);
                }
            }
        }

        if(input.contains("drop")) {
            for(int i = 0; i < advent.getRooms()[index].getItems().size(); i++) {
                if(advent.getRooms()[index].getItems().get(i).equalsIgnoreCase(input.substring(ITEM_SUBSTRING_SHIFT))) {
                    advent.getRooms()[index].getItems().add(input);
                    return "You are carrying: " + carryItemUpdate(modified, -1);
                }
            }
        }
    }

    //--------------------
    //--------------------
    //--MAIN METHOD HERE--
    //--------------------
    //--------------------

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();
        boolean canGoOn = goOn(input);

        String currentRoom = advent.getStartingRoom();
        advent.getRooms()[0].getDescription();

        while (canGoOn) {
            //check if you are in a special room
            specialRoom(currentRoom);
            //check for items
            itemCheck(currentRoom);
            //get directions
            itemGetLeave(scan.nextLine(), currentRoom);
            movesAvailable(currentRoom);
            //check before the loop iterates again
            canGoOn = goOn(scan.nextLine());
        }
    }
}
