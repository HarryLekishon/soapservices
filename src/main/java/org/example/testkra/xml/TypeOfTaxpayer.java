package org.example.testkra.xml;

public enum TypeOfTaxpayer {
    COMP("COMP"),
    KE("KE"),
    NKE("NKE"),
    NKENR("NKENR");
    String name;
    TypeOfTaxpayer(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
