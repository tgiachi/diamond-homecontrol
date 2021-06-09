package com.github.tgiachi.diamond.homecontrol.api.interfaces.entities;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IBaseEntity {

    void setId(ObjectId id);

    ObjectId getId();

    void setCreatedDateTime(LocalDateTime dateTime);

    LocalDateTime getCreatedDateTime();

    void setUpdatedDateTime(LocalDateTime dateTime);

    LocalDateTime getUpdatedDateTime();

}
