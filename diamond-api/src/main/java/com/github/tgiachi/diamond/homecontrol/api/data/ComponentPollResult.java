package com.github.tgiachi.diamond.homecontrol.api.data;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentPollResult<TEntity extends IBaseEntity> implements Serializable {
    private TEntity data;
    private ComponentPollResultType status;
    private Exception exception;
    private Class<TEntity> entityClass;
}
