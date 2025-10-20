import java.util.LinkedList;
import java.util.Queue;

public class Elevator{
    private int curFloor;
    private boolean doorOpen;
    private Queue<Integer> floorOrder;
    private Direction direction;

    public enum Direction{
        UP, DOWN, IDLE
    }

    //creating the elevator only requires the initial floor
    public Elevator(int initialFloor){
        this.curFloor = initialFloor;
        this.doorOpen = true;//door must be open initially so people can get on
        this.direction = Direction.IDLE;
        this.floorOrder = new LinkedList<>();
    }

    //basic gets
    public int getFloor(){
        return curFloor;
    }
    public Direction getDirection(){
        return direction;
    }
    public boolean isDoorOpen(){
        return doorOpen;
    }

    //Opening and closing the elevator door with a message for each
    public void openDoor(){//what floor the elevator is opening on
        doorOpen = true;;
        System.out.println("Floor " + getFloor());
    }
    public void closeDoor(){//telling people to be careful of the door
        doorOpen = false;
        System.out.println("Stand clear of the closing doors!");
    }

    public void emergencyButton(){//The red button on an elevator
        System.out.println("CALLING 911 AND ALERTING MAINTENANCE!!");
    }
}