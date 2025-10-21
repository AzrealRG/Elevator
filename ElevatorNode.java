public class ElevatorNode<T>{
    T data;
    ElevatorNode<T> next;
    ElevatorNode<T> prev;

    ElevatorNode(T data) {
        this.data = data;
    }
}