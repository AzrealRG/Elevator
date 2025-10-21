import java.util.PriorityQueue;
import java.util.Collections;

public class Elevator{
    private ElevatorNode<Integer> curFloor;
    private ElevatorNode<Integer> topFloor;
    private ElevatorNode<Integer> bottomFloor;
    private boolean doorOpen;
    private PriorityQueue<Integer> upOrder;
    private PriorityQueue<Integer> downOrder;
    private Direction direction;

    public enum Direction{
        UP, DOWN, IDLE
    }

    //creating the elevator only requires the initial floor
    public Elevator(int numFloors){
        buildElevator(numFloors);
        this.doorOpen = true;//door must be open initially so people can get on
        this.direction = Direction.IDLE;
        //max and min heaps to go next up or down floor.
        this.upOrder = new PriorityQueue<>();
        this.downOrder = new PriorityQueue<>(Collections.reverseOrder());
    }

    private void buildElevator(int numFloors){
        if (numFloors <= 1){//attempted to create a negative floor elevator
            System.out.println("Number of Floors isn't positive");
            return;
        }
        bottomFloor = curFloor = new ElevatorNode<Integer>(1);//holds bottom floor
        ElevatorNode<Integer> prev = curFloor;
        for(int i = 2; i <= numFloors; i++){
            ElevatorNode<Integer> nextFloor = new ElevatorNode<Integer>(i);
            prev.next = nextFloor;
            nextFloor.prev = prev;
            prev = prev.next;
        }
        topFloor = prev;//holds very top floor
    }

    //basic gets
    public int getFloor(){
        return curFloor.data;
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

    public void move(){
        if(upOrder.isEmpty() && downOrder.isEmpty()){//trying to run move without any requests
            System.out.println("No requested Floor");
            direction = Direction.IDLE;
            return;
        }
        else if(upOrder.isEmpty()){//switch to going down if no up requests
            System.out.println("Going down");
            direction = Direction.DOWN;
        }
        else{//switch to going up if no down requests
            System.out.println("Going up");
            direction = Direction.UP;
        }

        if(direction == Direction.UP){
            int nextFloor = upOrder.poll();

            if(topFloor.data < nextFloor){//next floor in queue doesn't exist
                upOrder.clear();
                System.out.println("No such floor!");
            }

            while (nextFloor > curFloor.data){
                curFloor = curFloor.next;
            }
        }
        else if(direction == Direction.DOWN){
            int nextFloor = downOrder.poll();

            if(bottomFloor.data > nextFloor){//next floor in queue doesn't exist
                downOrder.clear();
                System.out.println("No such floor!");
                return;
            }

            while(nextFloor < curFloor.data){
                curFloor = curFloor.prev;
            }
            System.out.println("Floor " + curFloor.data);
        }
    }
}