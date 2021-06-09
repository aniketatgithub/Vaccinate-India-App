package aniket.tikariha.vaccinateindia;

public class Vaccines {
    String available_capacity_dose1;
    String available_capacity_dose2;
    String min_age_limit;
    String vaccine;
    String fee;
    String name;
    String address;

    public Vaccines(String available_capacity_dose1, String available_capacity_dose2, String min_age_limit, String vaccine, String fee, String name, String address) {

        this.available_capacity_dose1 = available_capacity_dose1;
        this.available_capacity_dose2 = available_capacity_dose2;
        this.min_age_limit = min_age_limit;
        this.vaccine = vaccine;
        this.fee = fee;
        this.name = name;
        this.address = address;
    }
}
