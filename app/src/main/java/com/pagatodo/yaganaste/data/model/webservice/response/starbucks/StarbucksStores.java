package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import java.util.List;

public class StarbucksStores {

    private String address, name;
    private long distance, idAlsea, idStore;
    private double latitude, longitude;
    private List<HourStore> hours;

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public long getDistance() {
        return distance;
    }

    public long getIdAlsea() {
        return idAlsea;
    }

    public long getIdStore() {
        return idStore;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<HourStore> getHours() {
        return hours;
    }

    class HourStore {
        private String closeHour, openHour;
        private int idDay;

        public String getCloseHour() {
            return closeHour;
        }

        public String getOpenHour() {
            return openHour;
        }

        public int getIdDay() {
            return idDay;
        }
    }
}
