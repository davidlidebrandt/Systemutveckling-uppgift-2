package service;

public class GetWarehouse {
    public static Warehouse warehouse;

    public static Warehouse getWarehouse() {
        if(warehouse == null) {
            warehouse = new Warehouse();
            return warehouse;
        }
        return warehouse;
    }
}
