package designbook;

public interface CardSource {
    Card draw();
    boolean isEmpty();
}