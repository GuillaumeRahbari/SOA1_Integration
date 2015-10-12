package fr.unice.polytech.soa1.biko.entity;

import fr.unice.polytech.soa1.biko.entity.stuff.Stuff;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 10/5/2015.
 */
public class CustomBike extends ConnectedBike {

    private List<Stuff> customItem;

    public CustomBike() {
        this.customItem = new ArrayList<>();
    }

    public List<Stuff> getCustomItem() {
        return customItem;
    }

    public void setCustomItem(List<Stuff> customItem) {
        this.customItem = customItem;
    }
}
