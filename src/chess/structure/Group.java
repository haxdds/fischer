package chess.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rahul on 7/19/2017.
 * <p>
 * This class represents the groups of translations that different pieces
 * are allowed to move under: Bishops move diagonally, rooks vertically and
 * horizontally, and so on. The group associated with a piece represents all
 * possible translations that the piece is allowed to move under.
 * <p>
 * Dependencies:
 *
 * @see Translation
 */
public class Group implements Iterable<Translation>{

    /**
     * The list of possible translations that define the group.
     *
     * @see Translation
     */

    List<Translation> group = new ArrayList<>();

    /**
     * A constructor for groups. No parameters are taken. This constructor generally
     * shouldn't be called directly, instead objects of the Group class should be created
     * with the GroupFactory Interface.
     *
     * @see ChessGroupFactory
     */
    public Group() {
    }

    /**
     * Adds a translation to the list of translations
     *
     * @param t a translation
     * @see Translation
     */
    public void add(Translation t) {
        group.add(t);
    }

    /**
     * Adds the list of translation to this object's list of translations.
     *
     * @param group a group of a translations
     * @see Translation
     */
    public void addAll(List<Translation> group) {
        this.group.addAll(group);
    }

    /**
     * Retrieves a translation from the list of translations given an index of the list.
     *
     * @param index position of translation in list
     * @return translation in the given position in the list
     * @see Translation
     */
    public Translation get(int index) {
        return group.get(index);
    }

    /**
     * Removes a translation from the list of translations at a given index.
     *
     * @param index the position of the translation intended to be removed.
     */
    public void remove(int index) {
        group.remove(index);
    }

    /**
     * Removes a translation from the list of translations given the translation to be removed.
     *
     * @param t the translation intended to be removed.
     * @see Translation
     */
    public void remove(Translation t) {
        group.remove(t);
    }

    /**
     * @return the list of translations that define the group.
     * @see Translation
     */
    public List<Translation> getGroup() {
        return group;
    }

    /**
     * @return the size of the list of translations that define the group.
     */
    public int size() {
        return group.size();
    }


    @Override
    public Iterator<Translation> iterator() {
        return group.iterator();
    }
}
