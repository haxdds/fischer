package chess.transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 6/21/2017.
 */
public class Group {
    private List<Translation> group = new ArrayList<>();


    public void add(Translation t){
        group.add(t);
    }

    public void addAll(List<Translation> translations){
        group.addAll(translations);
    }

    public boolean contains(Translation t){
        for(Translation tr: group)
            if(tr.equals(t)) return true;
        return false;
    }

    public List<Translation> getGroup(){
        return group;
    }
}
