import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.*;
import java.lang.*;

public class Game {

    private static Adventure advent = LinkParse.parse("siebel.json");
    private static final int ITEM_SUBSTRING_SHIFT = 5;
    private static final int MOVE_SUBSTRING_SHIFT = 3;
    private static final int ATTACK_WITH_SUBSTRING_SHIFT = 13;
    private static final int NUMBER_OF_CHARS_IN_STATUS = 20;
    private static final int DUEL_SUBSTRING_SHIFT = 5;
    private static ArrayList<Item> carryItems = new ArrayList<>();
    private static double experienceGained = 0;
    private static boolean continueDuel;


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

        if (input.indexOf("take") == 0) {
            for (int i = 0; i < advent.getRooms()[index].getItems().size(); i++) {
                if (advent.getRooms()[index].getMonstersInRoom().size() != 0) {
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
        } else if (input.indexOf("drop") == 0) {
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
        for (int i = 0; i < advent.getRooms()[index].getDirections().length; i++) {
            if (advent.getRooms()[index].getDirections()[i].getDirectionName().
                    equalsIgnoreCase(move.substring(MOVE_SUBSTRING_SHIFT)) && advent.getRooms()[index]
                    .getMonstersInRoom().size() == 0) {
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
        for (int i = 0; i < advent.getRooms()[index].getDirections().length; i++) {
            if (advent.getRooms()[index].getDirections()[i].getDirectionName().
                    equalsIgnoreCase(move.substring(MOVE_SUBSTRING_SHIFT))) {
                return advent.getRooms()[index].getDirections()[i].getRoom();
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
        return advent.getRooms()[index].getDescription();
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

    /**
     * @param currentRoom the room that the user currently is in
     * @return the names of the monsters in the room
     */
    public static String monstersPresent(String currentRoom) {
        int index = getIndex(currentRoom);
        return "Monsters in " + currentRoom + " : " + advent.getRooms()[index].getMonstersInRoom();
    }


    public static void duel(String move, String monsterName, String currentRoom) {
        int index = getIndex(currentRoom);
        int monsterIndex = -1;
        for (int i = 0; i < advent.getRooms()[index].getMonstersInRoom().size(); i++) {
            if (advent.getRooms()[index].getMonstersInRoom().get(i)
                    .equalsIgnoreCase(monsterName)) {
                monsterIndex = i;
            }
        }

        if (monsterIndex == -1) {
            System.out.println("I can't duel " + monsterName);
        }

        if (move.indexOf("attack") == 0) {
            int indexOfMonsterInArray = 0;
            for (int i = 0; i < advent.getMonsters().length; i++) {
                if (advent.getMonsters()[i].equals(
                        advent.getRooms()[index].getMonstersInRoom().get(0))) {
                    indexOfMonsterInArray = i;
                }
            }
            attack(move, currentRoom, monsterIndex, index, advent.getMonsters()[indexOfMonsterInArray].getHealth());
        } else if (move.indexOf("status") == 0) {
            System.out.println(status(monsterIndex, index));
        } else if (move.indexOf("list") == 0) {
            System.out.println(list());
        } else if (move.indexOf("playerinfo") == 0) {
            System.out.println(displayPlayerInfo());
        } else if (move.indexOf("exit") == 0 || move.indexOf("quit") == 0) {
            System.out.println(goOn(move, currentRoom));
        } else {
            System.out.println("I can't duel " + monsterName);
        }
    }


    public static String status(int monsterIndex, int index) {
        String playerStatus = "Player: ";
        for (int i = 0; i < NUMBER_OF_CHARS_IN_STATUS; i++) {
            if (i * 5 < advent.getPlayer().getHealth()) {
                playerStatus += "#";
            } else {
                playerStatus += "-";
            }
        }
        int indexOfMonsterInArray = 0;

        for (int i = 0; i < advent.getMonsters().length; i++) {
            if (advent.getRooms()[index].getMonstersInRoom().get(monsterIndex)
                    .equalsIgnoreCase(advent.getMonsters()[i].getName())) {
                indexOfMonsterInArray = i;
            }
        }

        String monsterStatus = "Monster: ";
        for (int i = 0; i < NUMBER_OF_CHARS_IN_STATUS; i++) {
            if (i * 5 < advent.getMonsters()[indexOfMonsterInArray].getHealth()) {
                monsterStatus += "#";
            } else {
                monsterStatus += "-";
            }
        }
        return playerStatus + "\n" + monsterStatus;
    }

    public static void attack(String move, String currentRoom, int monsterIndex, int index, double monsterInitialHealth) {
        //find the monster in Monster[]
        int indexOfMonsterInArray = 0;

        for (int i = 0; i < advent.getMonsters().length; i++) {
            if (advent.getRooms()[index].getMonstersInRoom().get(monsterIndex)
                    .equalsIgnoreCase(advent.getMonsters()[i].getName())) {
                indexOfMonsterInArray = i;
            }
        }
        Monster fighter = new Monster(advent.getMonsters()[indexOfMonsterInArray].getName(),
                advent.getMonsters()[indexOfMonsterInArray].getAttack(),
                advent.getMonsters()[indexOfMonsterInArray].getDefense(),
                advent.getMonsters()[indexOfMonsterInArray].getHealth());

        if (move.contains("with")) {
            if (carryItems.contains(move.substring(ATTACK_WITH_SUBSTRING_SHIFT))) {
                for (int i = 0; i < carryItems.size(); i++) {
                    if (carryItems.get(i).getName().equalsIgnoreCase(move.substring(ATTACK_WITH_SUBSTRING_SHIFT))) {
                        double damage = carryItems.get(i).getDamage();
                        Item attackWith = new Item(move.substring(ATTACK_WITH_SUBSTRING_SHIFT), damage);

                        double damageOnMonster = advent.getPlayer().getAttack() + attackWith.getDamage()
                                - fighter.getDefense();
                        fighter.setHealth(fighter.getHealth() - damageOnMonster);
                    }
                }
            } else {
                System.out.println("Don't have that item");
            }
        } else {
            double damageOnMonster = advent.getPlayer().getAttack() - fighter.getDefense();
            fighter.setHealth(fighter.getHealth() - damageOnMonster);
        }

        if (fighter.getHealth() <= 0) {
            advent.getRooms()[index].getMonstersInRoom().remove(monsterIndex);
            System.out.println(winDuel(monsterInitialHealth, fighter));
        } else {
            double damageOnPlayer = fighter.getAttack() - advent.getPlayer().getDefense();
            advent.getPlayer().setHealth(advent.getPlayer().getHealth() - damageOnPlayer);
            if (advent.getPlayer().getHealth() < 0) {
                System.out.println("You died.");
                goOn("exit", currentRoom);
            }
        }
    }

    private static String winDuel(double monsterInitialHealth, Monster fighter) {
        advent.getPlayer().setHealth(advent.getPlayer().getHealth() * 1.3);
        advent.getPlayer().setAttack(advent.getPlayer().getAttack() * 1.5);
        advent.getPlayer().setDefense(advent.getPlayer().getDefense() * 1.5);
        experienceGained += ((fighter.getAttack() + fighter.getDefense()) / 2 + monsterInitialHealth);
        if (advent.getPlayer().getLevel() < experienceLevel(advent.getPlayer().getLevel())) {
            advent.getPlayer().setLevel((int) experienceLevel(advent.getPlayer().getLevel()));
        }
        continueDuel = false;
        return "Congrats on defeating " + fighter.getName();
    }

    private static double experienceLevel(int level) {
        if (level == 1) {
            return 25;
        } else if (level == 2) {
            return 50;
        } else {
            return ((experienceLevel(level - 1) + experienceLevel(level - 2)) * 1.1);
        }
    }

    private static String displayPlayerInfo() {
        String name = advent.getPlayer().getName();
        double attack = advent.getPlayer().getAttack();
        double defense = advent.getPlayer().getDefense();
        double health = advent.getPlayer().getHealth();
        ArrayList<Item> items = advent.getPlayer().getItems();
        return ("Name: " + name + "\nHealth: " + health + "\nDefense: " + defense + "\nAttack: " + attack + "\nItems: " + items);
    }

    ////////////////////////
    ////MAIN METHOD HERE////
    ////////////////////////


    public static void main(String[] args) {

        advent = LinkParse.parse("siebel.json");
        Scanner scan = new Scanner(System.in);
        //String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

        /*try {
            LinkParse.makeApiRequest(url);
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }*/

        System.out.println(displayPlayerInfo());
        String currentRoom = advent.getStartingRoom();

        //String input = scan.nextLine();
        boolean canGoOn = true;

        while (canGoOn) {
            System.out.println(describe(currentRoom));

            //check if you are in a special room
            System.out.println(specialRoom(currentRoom));

            //check for items
            System.out.println(itemCheck(currentRoom));

            //get directions for moves
            System.out.println(movesAvailable(currentRoom));

            //get the monsters present in the room
            System.out.println(monstersPresent(currentRoom));

            //see what the person wants to do: something with items or moving?
            // -1 = go, 0 = playerInfo, 1 = take/drop, -2 = exit/quit, 3 = list, duel = 2
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
            } else if (decision == 2 && monstersPresent(currentRoom).contains(move.substring(DUEL_SUBSTRING_SHIFT))) {
                System.out.println("Duel Begins");
                // String nextMove = scan.nextLine();
                continueDuel = true;
                //System.out.println(duel(nextMove, move.substring(DUEL_SUBSTRING_SHIFT), currentRoom));

                while (continueDuel) {
                    String nextMove = scan.nextLine();
                    if (nextMove.equalsIgnoreCase("disengage") || advent.getPlayer().getHealth() < 0) {
                        break;
                    } else {
                        duel(nextMove, move.substring(DUEL_SUBSTRING_SHIFT), currentRoom);
                    }
                }
            } else if (decision == -2) {
                break;
            } else {
                System.out.println("I can't: " + move);
            }

            //boolean canMove = validMove(move, currentRoom);
            //if (canMove) {
            //     currentRoom = moved(move, currentRoom);
            //}

            //check before the loop iterates again
            // canGoOn = goOn(scan.nextLine(), currentRoom);
            // System.out.println(describe(currentRoom));
        }
    }
}