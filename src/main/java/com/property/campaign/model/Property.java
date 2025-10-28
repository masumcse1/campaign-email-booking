package com.property.campaign.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Property {

    @Id
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer propertyId;

    @Column(name = "cultswitch_id")
    private String cultSwitchId;

    @Column(name = "email_id")
    private Integer emailId;

    private String email;

    @Basic
    @Column(name = "name", length = -1)
    private String name;

    @Basic
    @Column(name = "google_property_name", length = -1)
    private String googlePropertyName;

    @Column(length = 65000, columnDefinition = "TEXT")
    private String googleAddress;

    @Column(length = 65000, columnDefinition = "TEXT")
    private String googleBusinessID;

    private String googlePhoneNumber;

    @Column(length = 65000, columnDefinition = "TEXT")
    private String websiteURL;

    @Column(length = 65000, columnDefinition = "TEXT")
    private String propertyWebsiteURL;

    private String usedBookingEngine;

    private Boolean emailAddressUnsubscribe;

    private Boolean freeGoogleBookingLinks;

    private Integer googleRating;

    private String cmsUsedInWebsite;

    private String googleCategory;

    private String topic;

    private String sentAt;

    private Boolean isDeleted;

    private Boolean forTesting;

    private Boolean googleParser;

    private Double latitude;

    private Double longitude;

    private String googleMetaSearchLinksUrl;

    private String googleMapsWithGoogleBusinessPlacesIdScreenshotUrl;

    private String bookingDotComId;

    private String bookingDotComUrl;

    @Column(name = "ehotel_identifier")
    private String eHotelIdIdentifier;

    @Basic
    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = true)
    private Country country;

    private Boolean urlForAiSystemsFound;

    private Timestamp urlForAiSystemsFoundLastUpdate;

    @Column(name = "screenshotOfFooterUrl")
    private String screenshotOfFooterUrl;

}
