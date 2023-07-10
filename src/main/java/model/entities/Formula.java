package model.entities;

import java.io.Serializable;

public class Formula implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String descformula;

    private Matter matter;

    public Formula(){
    }

    public Formula(Integer id, String name, String descformula){
        this.id = id;
        this.name = name;
        this.descformula = descformula;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescformula() {return descformula;}

    public void setDescformula(String descformula) {this.descformula = descformula;}

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Formula other = (Formula) o;
        if (id == null){
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descformula='" + descformula +
                '}';
    }
}
