import java.util.Scanner;

public class Main {

    private static Adventure advent = LinkParse.adventure;

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
        int index = 0;

        //find the index of the corresponding room to the rooms[]
        for (int i = 0; i < advent.getRooms().length; i++) {
            if(advent.getRooms()[i].getName().equalsIgnoreCase(currentRoom)) {
                index = i;
                break;
            }
        }

        //get the directions you can move and append each to the string
        StringBuilder moveOptions = new StringBuilder();
        for (int i = 0; i <advent.getRooms()[index].getDirections().length - 1; i++) {
            moveOptions.append(advent.getRooms()[index].getDirections()[i].getDirectionName() + ", ");
        }
        moveOptions.append("or " + advent.getRooms()[index].getDirections()[advent.getRooms()[index].
                getDirections().length].getDirectionName());

        return "From here, you can go:" + moveOptions;
    }

    public static String itemCheck(String currentRoom) {
        int index = 0;

        //find the index of the corresponding room to the rooms[]
        for (int i = 0; i < advent.getRooms().length; i++) {
            if(advent.getRooms()[i].getName().equalsIgnoreCase(currentRoom)) {
                index = i;
                break;
            }
        }

        //get the items present and append each to the string
        StringBuilder itemsAvailable = new StringBuilder();
        for (int i = 0; i <advent.getRooms()[index].getItems().length - 1; i++) {
            itemsAvailable.append(advent.getRooms()[index].getDirections()[i].getDirectionName() + ", ");
        }
        itemsAvailable.append("or " + advent.getRooms()[index].getDirections()[advent.getRooms()[index].
                getDirections().length].getDirectionName());

        return "This room contains:" + itemsAvailable;
    }

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
            movesAvailable(currentRoom);
            //check before the loop iterates again
            canGoOn = goOn(scan.nextLine());
        }
    }
}
