package creatures;

import huglife.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Anthony Nguyen on 5/5/2017.
 */

public class Clorus extends Creature{
    private int r;
    private int g;
    private int b;

    public Clorus(double e){
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus(){
        this(1);
    }

    public Color color(){
        return color(34, 0, 231);
    }

    public void move(){
        energy -= .03;
    }

    public void attack(Creature x){
        energy += x.energy();
    }

    public Clorus replicate(){
        energy = energy / 2;
        return new Clorus(energy);
    }

    public void stay(){
        energy -= .01;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors){
        java.util.List<Direction> openSpace = getNeighborsOfType(neighbors, "empty");
        if (openSpace.size() <= 0) {
            return new Action(Action.ActionType.STAY);
        }

        List<Direction> prey = getNeighborsOfType(neighbors, "plip");
        if (prey.size() > 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(prey));
        }

        Direction direction = HugLifeUtils.randomEntry(openSpace);
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, direction);
        }

        return new Action(Action.ActionType.MOVE, direction);
    }

}
