package com.github.tgiachi.diamond.homecontrol.api.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements IBaseEntity {


    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
