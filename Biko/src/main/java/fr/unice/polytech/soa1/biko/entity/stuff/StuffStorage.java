package fr.unice.polytech.soa1.biko.entity.stuff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Quentin on 9/28/2015.
 */
public class StuffStorage {

    private static HashMap<String, Stuff> stuffDB = new HashMap<>();

    public static void create(String name, int price, int type) {
        Stuff stuff = null;
        switch (type) {
            case 1:
                stuff = new Handlebar(name,price);
                break;
            case 2 :
                stuff = new Wheel(name, price);
                break;
        }
        stuffDB.put(name,stuff);
    }

    public static Stuff read(String name) {
        return stuffDB.get(name);
    }

    public static void delete(String name) {
        stuffDB.remove(name);
    }

    public static Collection<Stuff> findAll() {
        return stuffDB.values();
    }

    public static Collection<Stuff> findFromType(Type type){
        ArrayList<Stuff> list = new ArrayList<Stuff>();
        for(Stuff s : stuffDB.values()){
            if(s.getType() == type)
                list.add(s);
        }
        return list;
    }

    public static void addStuff(Stuff stuff) {
        stuffDB.put(stuff.getName(), stuff);
    }

    public static Type[] getTypes() {
        return Type.values();
    }

    static {
        stuffDB.put("MAXI-HandleBar", new Handlebar("Maxi-Handlebar", 100));
        stuffDB.put("SUPER-Wheel", new Wheel("Super-Wheel", 200));
    }
}
