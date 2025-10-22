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
    private Status status;

    public enum Direction{
        UP, DOWN, IDLE
    }
    public enum Status{
        WORKING, BROKEN
    }

    //creating the elevator only requires the initial floor
    public Elevator(int numFloors){
        buildElevator(numFloors);
        this.doorOpen = true;//door must be open initially so people can get on
        this.direction = Direction.IDLE;
        //max and min heaps to go next up or down floor.
        this.upOrder = new PriorityQueue<>();
        this.downOrder = new PriorityQueue<>(Collections.reverseOrder());
        //Elevator status
        this.status = Status.WORKING;
    }

    private void buildElevator(int numFloors){
        if (numFloors <= 1){//attempted to create a negative floor or 1 floor elevator
            System.out.println("Number of Floors isn't positive or building too small");
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
        if(curFloor == null) {
            return -1; // Error state
        }
        return curFloor.data;
    }
    public Direction getDirection(){
        return direction;
    }
    public boolean isDoorOpen(){
        return doorOpen;
    }
    public Status getStatus(){
        return status;
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
        status = Status.BROKEN;
        System.out.println("GET ELEVATOR FIXED BEFORE IT CAN MOVE");
    }

    public void requests(int floor){
        if(floor < getFloor()){
            downOrder.add(floor);
        }
        else if(floor > getFloor()){
            upOrder.add(floor);
        }
    }
    
    public void fixElevator(){
        System.out.println("Elevator being fixed");
        try{
            Thread.sleep(10000);//technician fixing the Elevator
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("Elevator fixed");
        status = Status.WORKING;
    }

    public void move(){
        if(getStatus() == Status.BROKEN){//if elevator broken, it wont move until it is fixed
            System.out.println("Elevator broken/emergency brake, get maintenance to fix elevator before continuing");
            return;
        }
        if(upOrder.isEmpty() && downOrder.isEmpty()){//trying to run move without any requests
            System.out.println("No requested Floor");
            direction = Direction.IDLE;
            return;
        }
        else if(upOrder.isEmpty()){//switch to going down if no up requests
            direction = Direction.DOWN;
        }
        else if(downOrder.isEmpty()){//switch to going up if no down requests
            direction = Direction.UP;
        }

        if(getDirection() == Direction.UP){
            int nextFloor = upOrder.poll();

            if(topFloor.data < nextFloor){//next floor in queue doesn't exist
                upOrder.clear();
                System.out.println("No such floor!");
            }
            System.out.println("Going up");
            while(nextFloor > curFloor.data){
                System.out.println(getFloor());
                curFloor = curFloor.next;
            }
            openDoor();
        }
        else if(getDirection() == Direction.DOWN){
            int nextFloor = downOrder.poll();

            if(bottomFloor.data > nextFloor){//next floor in queue doesn't exist
                downOrder.clear();
                System.out.println("No such floor!");
                return;
            }
            System.out.println("Going down");
            while(nextFloor < curFloor.data){
                System.out.println(getFloor());
                curFloor = curFloor.prev;
            }
            openDoor();
        }

        try{
            Thread.sleep(3000);//stays open for 3 seconds
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        closeDoor();
    }
}