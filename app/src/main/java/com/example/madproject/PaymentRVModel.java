package com.example.madproject;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentRVModel implements Parcelable {

    private String nameOnCard;
    private String cardNumber;
    private String expDate;
    private String cvv;
    private String amount;
    private String rcptImg;
    private String paymentID;

    public PaymentRVModel(String nameOnCard, String cardNumber, String expDate, String cvv, String amount, String paymentID) {

    }

    protected PaymentRVModel(Parcel in) {
        nameOnCard = in.readString();
        cardNumber = in.readString();
        expDate = in.readString();
        cvv = in.readString();
        amount = in.readString();
        rcptImg = in.readString();
        paymentID = in.readString();
    }

    public static final Creator<PaymentRVModel> CREATOR = new Creator<PaymentRVModel>() {
        @Override
        public PaymentRVModel createFromParcel(Parcel in) {
            return new PaymentRVModel(in);
        }

        @Override
        public PaymentRVModel[] newArray(int size) {
            return new PaymentRVModel[size];
        }
    };

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRcptImg() {
        return rcptImg;
    }

    public void setRcptImg(String rcptImg) {
        this.rcptImg = rcptImg;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public PaymentRVModel(String nameOnCard, String cardNumber, String expDate, String cvv, String amount, String rcptImg, String paymentID) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cvv = cvv;
        this.amount = amount;
        this.rcptImg = rcptImg;
        this.paymentID = paymentID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameOnCard);
        dest.writeString(cardNumber);
        dest.writeString(expDate);
        dest.writeString(cvv);
        dest.writeString(amount);
        dest.writeString(rcptImg);
        dest.writeString(paymentID);
    }
}
