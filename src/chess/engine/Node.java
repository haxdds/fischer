package chess.engine;

import java.util.List;

/**
 * Created by Rahul on 6/26/2017.
 */
public class Node<T> {

    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    public Node(){

    }

    public Node(T value){
        this.data = value;
    }

    public boolean isRoot(){
        return parent == null;
    }

    public boolean isLeaf(){
        return children.size() == 0;
    }

    public void setData(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public void addChild(Node<T> child){
        children.add(child);
    }

    public void setParent(Node<T> parent){
        this.parent = parent;
    }

    public Node<T> getParent(){
        return parent;
    }

    public List<Node<T>> getChildren(){
        return children;
    }

    public Node<T> getChild(int index){
        return children.get(index);
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }


}
