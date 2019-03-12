package com.pagatodo.view_manager.wallet.data;

public class YaGanasteCardData{

    private String NumberCard;
    private String nameOwner;

    private YaGanasteCardData(String numberCard, String nameOwner) {
        NumberCard = numberCard;
        this.nameOwner = nameOwner;
    }

    public static YaGanasteCardData create(String numberCard, String nameOwner){
        return new YaGanasteCardData(numberCard,numberCard);
    }

    public static YaGanasteCardData createDeafult(){
        return new YaGanasteCardData("0000000001234","Ismael Cruz");
    }

    public String getNumberCard() {
        return NumberCard;
    }

    public void setNumberCard(String numberCard) {
        NumberCard = numberCard;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

}
