package com.example.miniprojet;

import org.json.JSONObject;

public class Contacts {
    private String ID, Name, Address, Phone1, Phone2, Company;

    public Contacts(JSONObject jObject) {
        this.ID = jObject.optString("ID");
        this.Name = jObject.optString("Name");
        this.Address = jObject.optString("Address");
        this.Phone1 = jObject.optString("Phone1");
        this.Phone2 = jObject.optString("Phone2");
        this.Company = jObject.optString("Company");
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone1() {
        return Phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public String getCompany() {
        return Company;
    }
}
