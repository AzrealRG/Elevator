import java.util.PriorityQueue;
import java.util.Collections;

public class Elevator{
    private int curFloor;
    private boolean doorOpen;
    private PriorityQueue<Integer> upOrder;
    private PriorityQueue<Integer> downOrder;
    private Direction direction;

    public enum Direction{
        UP, DOWN, IDLE
    }

    //creating the elevator only requires the initial floor
    public Elevator(int initialFloor){
        this.curFloor = initialFloor;
        this.doorOpen = true;//door must be open initially so people can get on
        this.direction = Direction.IDLE;
        //max and min heaps to go next up or down floor.
        this.upOrder = new PriorityQueue<>();
        this.downOrder = new PriorityQueue<>(Collections.reverseOrder());
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
        System.out.println("CALLING 911 AND ALERTING MAINTENANCE!! MAYDAY! MAYDAY!");
    }

    public void requests(int floor){
        if(floor < getFloor()){
            downOrder.add(floor);
        }
        else if(floor > getFloor()){
            upOrder.add(floor);
        }
    }
}