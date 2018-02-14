import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.*;
import java.lang.*;

public class Game {

    private static Adventure advent;
    private static final int ITEM_SUBSTRING_SHIFT = 5;
    private static final int MOVE_SUBSTRING_SHIFT = 3;
    private static final int ATTACK_WITH_SUBSTRING_SHIFT = 13;
    private static final int NUMBER_OF_CHARS_IN_STATUS = 20;
    //private static HashMap<Integer, Item> carryItems = new HashMap();
    private static ArrayList<Item> carryItems = new ArrayList<>();

    /**
     * @param check       the String that the user inputs
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
     * @param check the room that the user is in
     * @return special messages based on the room
     */
    public static String specialRoom(String check) {
        if (check.equalsIgnoreCase(advent.getStartingRoom())) {
            return "Your journey to reclaim your honor begins here";
        } else if (check.equalsIgnoreCase(advent.getEndingRoom())) {
            return "You have reached your final destination and have finally reclaimed your honor";
        } else {
            return "";
        }
    }

    /**
     * @param currentRoom where the player currently is
     * @return the available valid moves
     */
    public static String movesAvailable(String currentRoom) {
        int index = getIndex(currentRoom);

        //get the directions you can move and append each to the string
        String moveOptions = "";
        int sizeLength = advent.getRooms()[index].getDirections().length;

        if (sizeLength == 1) {
            moveOptions += (advent.getRooms()[index].getDirections()[0].getDirectionName());
        } else {
            for (int i = 0; i < sizeLength - 1; i++) {
                moveOptions += (advent.getRooms()[index].getDirections()[i].getDirectionName() + ", ");
            }
            moveOptions += ("or " + advent.getRooms()[index].getDirections()[sizeLength - 1].getDirectionName());
        }
        return "From here, you can go: " + moveOptions;
    }

    /**
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
     * @param currentRoom the current room the user is in
     * @return the index of the room in the Rooms[]
     */
    private static int getIndex(String currentRoom) {
        int index = 0;

        //find the index of the corresponding room to the rooms[]
        for (int i = 0; i < advent.getRooms().length; i++) {
            if (advent.getRooms()[i].getName().equalsIgnoreCase(currentRoom)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static String list() {
        return "You are carrying: " + carryItems.toString();
    }

    /**
     * @param input       the item that the user wants to either drop or carry
     * @param currentRoom the current room of the user
     * @return a string of the updated items the user is carrying
     */
    public static String itemTakeOrDrop(String input, String currentRoom) {
        int index = getIndex(currentRoom);
        String modified = input.toLowerCase().substring(ITEM_SUBSTRING_SHIFT);

        if (input.contains("take")) {
            for (int i = 0; i < advent.getRooms()[index].getItems().size(); i++) {
                if (LinkParse.adventure.getRooms()[index].getMonstersInRoom().size() != 0) {
                    return "There still are monsters in the room, you cannot pick up the item";
                }
                if (advent.getRooms()[index].getItems().get(i).getName().equalsIgnoreCase(input.substring(ITEM_SUBSTRING_SHIFT))) {
                    //find damage
                    double damage = advent.getRooms()[index].getItems().get(i).getDamage();
                    advent.getRooms()[index].getItems().remove(i);
                    Item toTake = new Item(input.substring(ITEM_SUBSTRING_SHIFT), damage);
                    carryItems.add(toTake);
                }
            }
        } else if (input.contains("drop")) {
            for (int i = 0; i < carryItems.size(); i++) {
                if (carryItems.get(i).getName().equalsIgnoreCase(input.substring(ITEM_SUBSTRING_SHIFT))) {
                    double damage = carryItems.get(i).getDamage();
                    Item toDrop = new Item(input.substring(ITEM_SUBSTRING_SHIFT), damage);
                    advent.getRooms()[index].getItems().add(toDrop);
                    carryItems.remove(toDrop);
                }
            }
        } else {
            return "I can't" + input;
        }
        return null;
    }

    public static boolean validMove(String move, String currentRoom) {
        int index = getIndex(currentRoom);
        for (int i = 0; i < LinkParse.adventure.getRooms()[index].getDirections().length; i++) {
            if (LinkParse.adventure.getRooms()[index].getDirections()[i].getDirectionName().
                    equalsIgnoreCase(move.substring(MOVE_SUBSTRING_SHIFT))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param move        the place the user wants to move
     * @param currentRoom the room that the user is currently in
     * @return the new room the user is in
     */
    public static String moved(String move, String currentRoom) {
        int index = getIndex(currentRoom);
        for (int i = 0; i < LinkParse.adventure.getRooms()[index].getDirections().length; i++) {
            if (LinkParse.adventure.getRooms()[index].getDirections()[i].getDirectionName().
                    equalsIgnoreCase(move.substring(MOVE_SUBSTRING_SHIFT))) {
                return LinkParse.adventure.getRooms()[index].getDirections()[i].getRoom();
            }
        }
        return null;
    }

    /**
     * @param currentRoom the room the user is in currently
     * @return the description of the room
     */
    public static String describe(String currentRoom) {
        int index = getIndex(currentRoom);
        return LinkParse.adventure.getRooms()[index].getDescription();
    }

    /**
     * @param move will be the order
     * @return an int: 1 for items and -1 for directions
     */
    public static int decideNextFunction(String move) {
        String modified = move.toLowerCase();
        if (modified.indexOf("take ") == 0 || modified.indexOf("drop ") == 0) {
            return 1;
        } else if (modified.indexOf("go ") == 0) {
            return -1;
        } else if (modified.indexOf("playerinfo") == 0) {
            return 0;
        } else if (modified.indexOf("duel ") == 0) {
            return 2;
        } else if (modified.indexOf("quit") == 0 || modified.indexOf("exit") == 0) {
            return -2;
        } else if (modified.indexOf("list") == 0) {
            return 3;
        }
        return 4;
    }

    public static String monstersPresent(String currentRoom) {
        int index = getIndex(currentRoom);
        return "Monsters in " + currentRoom + " : " + advent.getRooms()[index].getMonstersInRoom();
    }

    public static String duel(String move, String currentRoom) {
        int index = getIndex(currentRoom);
        int monsterIndex = -1;
        for (int i = 0; i < LinkParse.adventure.getRooms()[index].getMonstersInRoom().size(); i++) {
            if (LinkParse.adventure.getRooms()[index].getMonstersInRoom().get(i)
                    .equalsIgnoreCase(move.substring(ITEM_SUBSTRING_SHIFT))) {
                monsterIndex = i;
            }
        }

        if (move.indexOf("attack") == 0) {
            attack(move, currentRoom, monsterIndex, index);
        } else if (move.indexOf("status") == 0) {
            status();
        } else if (move.indexOf("list") == 0) {
            list();
        } else if (move.indexOf("playerinfo") == 0) {
            displayPlayerInfo();
        } else if (move.indexOf("exit") == 0 || move.indexOf("quit") == 0) {
            goOn(move, currentRoom);
        } else {
            return "I can't duel " + move.substring(ITEM_SUBSTRING_SHIFT);
        }
        return null;
    }


    public static String status() {
        StringBuilder playerStatus = new StringBuilder();
        playerStatus.append("Player: ");
        for(int i = 0; i < NUMBER_OF_CHARS_IN_STATUS; i+=5) {
            if(i < LinkParse.adventure.getPlayer().getHealth()) {
                playerStatus.append("#");
            } else {
                playerStatus.append("_");
            }
        }

        StringBuilder monsterStatus = new StringBuilder();
        monsterStatus.append("Monster: ");
        for(int i = 0; i < NUMBER_OF_CHARS_IN_STATUS; i+=5) {
            if(i < LinkParse.adventure.getPlayer().getHealth()) {
                monsterStatus.append("#");
            } else {
                monsterStatus.append("_");
            }
        }
        return null;
    }

    public static void attack(String move, String currentRoom, int monsterIndex, int index) {
        //find the monster in Monster[]
        int indexOfMonsterInArray = 0;

        for (int i = 0; i < LinkParse.adventure.getMonsters().length; i++) {
            if (LinkParse.adventure.getMonsters()[i].equals(
                    LinkParse.adventure.getRooms()[index].getMonstersInRoom().get(monsterIndex))) {
                indexOfMonsterInArray = i;
            }
        }
        Monster fighter = new Monster(LinkParse.adventure.getMonsters()[indexOfMonsterInArray].getName(),
                LinkParse.adventure.getMonsters()[indexOfMonsterInArray].getAttack(),
                LinkParse.adventure.getMonsters()[indexOfMonsterInArray].getDefense(),
                LinkParse.adventure.getMonsters()[indexOfMonsterInArray].getHealth());

        if (move.contains("with")) {
            if (carryItems.contains(move.substring(ATTACK_WITH_SUBSTRING_SHIFT))) {

                for (int i = 0; i < carryItems.size(); i++) {
                    if (carryItems.get(i).getName().equalsIgnoreCase(move.substring(ATTACK_WITH_SUBSTRING_SHIFT))) {
                        double damage = carryItems.get(i).getDamage();
                        Item attackWith = new Item(move.substring(ATTACK_WITH_SUBSTRING_SHIFT), damage);

                        double damageOnMonster = LinkParse.adventure.getPlayer().getAttack() + attackWith.getDamage()
                                - fighter.getDefense();
                        fighter.setHealth(fighter.getHealth() - damageOnMonster);
                    }
                }
            }
        } else {
            double damageOnMonster = LinkParse.adventure.getPlayer().getAttack() - fighter.getDefense();
            fighter.setHealth(fighter.getHealth() - damageOnMonster);
        }
        if (fighter.getHealth() <= 0) {
            LinkParse.adventure.getRooms()[index].getMonstersInRoom().remove(indexOfMonsterInArray);
        } else {
            double damageOnPlayer = fighter.getAttack() - LinkParse.adventure.getPlayer().getDefense();
            LinkParse.adventure.getPlayer().setHealth(LinkParse.adventure.getPlayer().getHealth() - damageOnPlayer);
        }
    }

    private static String displayPlayerInfo() {
        String name = LinkParse.adventure.getPlayer().getName();
        double attack = LinkParse.adventure.getPlayer().getAttack();
        double defense = LinkParse.adventure.getPlayer().getDefense();
        double health = LinkParse.adventure.getPlayer().getHealth();
        return ("Name: " + name + "\nHealth: " + health + "\nDefense: " + defense + "\nAttack: " + attack);
    }

    ////////////////////////
    ////MAIN METHOD HERE////
    ////////////////////////


    public static void main(String[] args) {
        LinkParse.parse("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
        advent = LinkParse.adventure;
        Scanner scan = new Scanner(System.in);
        String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

        try {
            LinkParse.makeApiRequest(url);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }


        String currentRoom = advent.getStartingRoom();

        //String input = scan.nextLine();
        boolean canGoOn = true;

        System.out.println(describe(currentRoom));

        while (canGoOn) {
            //check if you are in a special room
            System.out.println(specialRoom(currentRoom));

            //check for items
            System.out.println(itemCheck(currentRoom));

            //get directions for moves
            System.out.println(movesAvailable(currentRoom));

            //get the monsters present in the room
            System.out.println(monstersPresent(currentRoom));

            //see what the person wants to do: something with items or moving?
            String move = scan.nextLine();
            int decision = decideNextFunction(move);

            if (decision == 1) {
                //get what the person wants to do with the items
                System.out.println(itemTakeOrDrop(move, currentRoom));
            } else if (decision == -1) {
                boolean canMove = validMove(move, currentRoom);
                if (canMove) {
                    currentRoom = moved(move, currentRoom);
                }
            } else if (decision == 0) {
                System.out.println(displayPlayerInfo());
            } else if (decision == 3) {
                System.out.println(list());
            } else if (decision == 2) {
                System.out.println(duel(move, currentRoom));
                boolean continueDuel = true;

                while (continueDuel) {
                    String nextMove = scan.nextLine();
                    if (nextMove.equalsIgnoreCase("disengage")) {
                        continueDuel = false;
                        break;
                    } else {
                        System.out.println(duel(nextMove, currentRoom));
                    }
                }
            } else if (decision == -2) {
                System.out.println(goOn(move, currentRoom));
            } else {
                System.out.println("I can't: " + move);
            }

            boolean canMove = validMove(move, currentRoom);
            if (canMove) {
                currentRoom = moved(move, currentRoom);
            }

            //check before the loop iterates again
            canGoOn = goOn(scan.nextLine(), currentRoom);
            System.out.println(describe(currentRoom));
        }
    }
}