package com.example.shivamscart.Models;

import java.io.Serializable;

public class MobileModel implements Serializable {
    private String mobileImage,mobileDescription,mobileRam,mobileActualPrice,mobilePrice,mobileDicount,mobileColor,mobileStorage,mobileCompany,addedBy;
    private long productAddedAt;

    public MobileModel() {
    }

    public String getMobileImage() {
        return mobileImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    public String getMobileDescription() {
        return mobileDescription;
    }

    public void setMobileDescription(String mobileDescription) {
        this.mobileDescription = mobileDescription;
    }

    public String getMobileRam() {
        return mobileRam;
    }

    public void setMobileRam(String mobileRam) {
        this.mobileRam = mobileRam;
    }

    public String getMobileActualPrice() {
        return mobileActualPrice;
    }

    public void setMobileActualPrice(String mobileActualPrice) {
        this.mobileActualPrice = mobileActualPrice;
    }

    public String getMobilePrice() {
        return mobilePrice;
    }

    public void setMobilePrice(String mobilePrice) {
        this.mobilePrice = mobilePrice;
    }

    public String getMobileDicount() {
        return mobileDicount;
    }

    public void setMobileDicount(String mobileDicount) {
        this.mobileDicount = mobileDicount;
    }

    public long getProductAddedAt() {
        return productAddedAt;
    }

    public void setProductAddedAt(long productAddedAt) {
        this.productAddedAt = productAddedAt;
    }

    public String getMobileColor() {
        return mobileColor;
    }

    public void setMobileColor(String mobileColor) {
        this.mobileColor = mobileColor;
    }

    public String getMobileStorage() {
        return mobileStorage;
    }

    public void setMobileStorage(String mobileStorage) {
        this.mobileStorage = mobileStorage;
    }

    public String getMobileCompany() {
        return mobileCompany;
    }

    public void setMobileCompany(String mobileCompany) {
        this.mobileCompany = mobileCompany;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
