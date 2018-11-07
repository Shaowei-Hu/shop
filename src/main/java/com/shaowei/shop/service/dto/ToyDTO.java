package com.shaowei.shop.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Toy entity.
 */
public class ToyDTO implements Serializable {

    private String id;

    private String recommendedAge;

    private String gender;

    private Instant purchaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecommendedAge() {
        return recommendedAge;
    }

    public void setRecommendedAge(String recommendedAge) {
        this.recommendedAge = recommendedAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ToyDTO toyDTO = (ToyDTO) o;
        if (toyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), toyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ToyDTO{" +
            "id=" + getId() +
            ", recommendedAge='" + getRecommendedAge() + "'" +
            ", gender='" + getGender() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            "}";
    }
}
