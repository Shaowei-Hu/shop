package com.shaowei.shop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Toy.
 */
@Document(collection = "toy")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "toy")
public class Toy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("recommended_age")
    private String recommendedAge;

    @Field("gender")
    private String gender;

    @Field("purchase_date")
    private Instant purchaseDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecommendedAge() {
        return recommendedAge;
    }

    public Toy recommendedAge(String recommendedAge) {
        this.recommendedAge = recommendedAge;
        return this;
    }

    public void setRecommendedAge(String recommendedAge) {
        this.recommendedAge = recommendedAge;
    }

    public String getGender() {
        return gender;
    }

    public Toy gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public Toy purchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Toy toy = (Toy) o;
        if (toy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), toy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Toy{" +
            "id=" + getId() +
            ", recommendedAge='" + getRecommendedAge() + "'" +
            ", gender='" + getGender() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            "}";
    }
}
