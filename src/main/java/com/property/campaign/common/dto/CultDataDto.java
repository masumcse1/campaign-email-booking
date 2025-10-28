package com.property.campaign.common.dto;

public class CultDataDto {

    private Boolean had_invoices;

    public CultDataDto() {
    }

    public CultDataDto(Boolean had_invoices) {
        this.had_invoices = had_invoices;
    }

    public Boolean isHad_invoices() {
        return had_invoices;
    }

    public void setHad_invoices(boolean had_invoices) {
        this.had_invoices = had_invoices;
    }
}
