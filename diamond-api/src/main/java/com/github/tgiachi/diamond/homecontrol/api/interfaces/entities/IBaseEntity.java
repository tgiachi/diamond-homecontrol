package com.github.tgiachi.diamond.homecontrol.api.interfaces.entities;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public interface IBaseEntity {

    void setId(ObjectId id);

    ObjectId getId();

    void setCreatedDateTime(LocalDate dateTime);

    LocalDate getCreatedDateTime();

    void setUpdatedDateTime(LocalDate dateTime);

    LocalDate getUpdatedDateTime();

}
