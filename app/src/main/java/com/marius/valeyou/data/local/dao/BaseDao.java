package com.marius.valeyou.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(T entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertAll(List<T> entityList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int update(T entity);

    @Update
    public abstract int updateAll(List<T> entityList);

    @Delete
    public abstract int delete(T entity);

    @Delete
    public abstract int deleteAll(List<T> entityList);
}
