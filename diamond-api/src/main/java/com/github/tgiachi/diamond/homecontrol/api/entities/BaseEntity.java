package com.github.tgiachi.diamond.homecontrol.api.entities;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
public class BaseEntity implements IBaseEntity {

    private ObjectId id;
    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
}
