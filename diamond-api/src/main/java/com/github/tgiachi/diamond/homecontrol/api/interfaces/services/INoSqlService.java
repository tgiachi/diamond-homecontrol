package com.github.tgiachi.diamond.homecontrol.api.interfaces.services;

import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.base.IDiamondService;

import java.util.List;

public interface INoSqlService extends IDiamondService {

    <TEntity extends IBaseEntity> TEntity insert(TEntity entity, Class<TEntity> entityClass);

    <TEntity extends IBaseEntity> TEntity update(TEntity entity, Class<TEntity> entityClass);

    <TEntity extends IBaseEntity> List<TEntity> findAll(Class<TEntity> entityClass);

}
