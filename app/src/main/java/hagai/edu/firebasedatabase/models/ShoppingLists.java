package hagai.edu.firebasedatabase.models;

/**
 * A shopping list title and owner
 * POJO:
 */

public class ShoppingLists {
    private  String ownerUID;
    private  String listUID;
    private String name;

    public ShoppingLists() {
    }
    //POJO constructor

    public ShoppingLists(String ownerUID, String listUID, String name) {
        this.ownerUID = ownerUID;
        this.listUID = listUID;
        this.name = name;
    }
    //getters and setters

    public String getOwnerUID() {
        return ownerUID;
    }

    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }

    public String getListUID() {
        return listUID;
    }

    public void setListUID(String listUID) {
        this.listUID = listUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ShoppingLists{" +
                "ownerUID='" + ownerUID + '\'' +
                ", listUID='" + listUID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
