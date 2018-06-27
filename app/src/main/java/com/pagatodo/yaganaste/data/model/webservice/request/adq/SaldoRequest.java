package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaldoRequest extends AdqRequest implements Serializable{

    private List<PetroNum> data;

    public SaldoRequest() {
        this.data = new ArrayList<>();
    }

    public void addPetroNum(PetroNum petroNum){
        data.add(petroNum);
    }

    public List<PetroNum> getData() {
        return data;
    }

    public void setData(List<PetroNum> data) {
        this.data = data;
    }

    public static class PetroNum implements Serializable{

        private String PetroNumero = "";

        public PetroNum(String petroNumero) {
            PetroNumero = petroNumero;
        }

        public String getPetroNumero() {
            return PetroNumero;
        }

        public void setPetroNumero(String petroNumero) {
            PetroNumero = petroNumero;
        }
    }
}

