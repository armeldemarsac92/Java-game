package GameLogic;

import java.util.*;

public class GlobalUnits {
    protected static List<Unit> units;

    public GlobalUnits() {
        GlobalUnits.units = new ArrayList<Unit>();
    }

    public void add(List<? extends Unit> units) {
        for (Unit unit : units) {
            GlobalUnits.units.add(unit);
        }
    }

    public void display() {
        for (Unit unit : GlobalUnits.units) {
            System.out.println(unit.getName());
        }
    }
}
