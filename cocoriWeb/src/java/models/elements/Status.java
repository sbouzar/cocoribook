package models.elements;

public enum Status {
    
  DONE (1, "Commande effectuée"),
  PAYED (2, "Commande payée"),
  PREPARED (3, "En cours de préparation"),
  SHIPPED (4, "Commande expédiée"),
  DELIVERY (5, "En cours de livraison"),
  CLOSED (6, "Commande fermée"),
  CANCELLED (7, "Commande annulée");
  
  private int id;
  private String name;

    private Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    
}
