package chess.engine;

/**
 * Created by Rahul on 6/26/2017.
 */
public class Test {

    public static void main(String[] args){




    }

    public Node createTree(){
        Node<Integer> root = new Node<Integer>(1);

        for(int k = 0; k < 3; k++){
            root.addChild(new Node<Integer>(k));
        }

        for(Node<Integer> child: root.getChildren()){
            for(int k = 4; k < 6; k++){
                child.addChild(new Node<Integer>(k));
            }
        }
        return root;
    }

    public Node<Integer> findMaxNode(Node<Integer> root){
        Node<Integer> max = null;


        return max;
    }






}
