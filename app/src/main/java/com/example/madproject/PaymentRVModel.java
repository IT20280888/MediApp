package com.example.madproject;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentRVModel implements Parcelable {

    private String cardNumber;
    private String paymentAmount;
    private String paymentCvv;
    private String paymentImg;
    private String paymentLink;
    private String paymentDescription;
    private String paymentID;

    public PaymentRVModel() {

    }

    protected PaymentRVModel(Parcel in) {
        cardNumber = in.readString();
        paymentAmount = in.readString();
        paymentCvv = in.readString();
        paymentImg = in.readString();
        paymentLink = in.readString();
        paymentDescription = in.readString();
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String nameOnCard) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String cardNumber) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentCvv() {
        return paymentCvv;
    }

    public void setPaymentCvv(String expDate) {
        this.paymentCvv = paymentCvv;
    }

    public String getPaymentImg() {
        return paymentImg;
    }

    public void setPaymentImg(String cvv) {
        this.paymentImg = paymentImg;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String amount) {
        this.paymentLink = paymentLink;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String rcptImg) {
        this.paymentDescription = paymentDescription;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public PaymentRVModel(String cardNumber, String paymentAmount, String paymentCvv, String paymentImg, String paymentLink, String paymentDescription, String paymentID) {
        this.cardNumber = cardNumber;
        this.paymentAmount = paymentAmount;
        this.paymentCvv = paymentCvv;
        this.paymentImg = paymentImg;
        this.paymentLink = paymentLink;
        this.paymentDescription = paymentDescription;
        this.paymentID = paymentID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardNumber);
        dest.writeString(paymentAmount);
        dest.writeString(paymentCvv);
        dest.writeString(paymentImg);
        dest.writeString(paymentLink);
        dest.writeString(paymentDescription);
        dest.writeString(paymentID);
    }
}
