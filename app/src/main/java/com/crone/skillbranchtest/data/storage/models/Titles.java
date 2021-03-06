package com.crone.skillbranchtest.data.storage.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by CRN_soft on 14.10.2016.
 */

@Entity(active = true, nameInDb = "PERSON_TITLES")
public class Titles {

    public Titles(Long remotePersonId, boolean isTitle, String text) {
        this.titlePersonRemoteId = remotePersonId;
        this.isTitle = isTitle;
        this.characteristic = text;
    }

    @Generated(hash = 718602066)
    public Titles(Long id, @NotNull Long titlePersonRemoteId,
            @NotNull Boolean isTitle, @NotNull String characteristic) {
        this.id = id;
        this.titlePersonRemoteId = titlePersonRemoteId;
        this.isTitle = isTitle;
        this.characteristic = characteristic;
    }

    @Generated(hash = 2082159104)
    public Titles() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTitlePersonRemoteId() {
        return this.titlePersonRemoteId;
    }

    public void setTitlePersonRemoteId(Long titlePersonRemoteId) {
        this.titlePersonRemoteId = titlePersonRemoteId;
    }

    public Boolean getIsTitle() {
        return this.isTitle;
    }

    public void setIsTitle(Boolean isTitle) {
        this.isTitle = isTitle;
    }

    public String getCharacteristic() {
        return this.characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1913718169)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTitlesDao() : null;
    }

    @Id
    private Long id;

    @NotNull
    private Long titlePersonRemoteId;

    @NotNull
    private Boolean isTitle;

    @NotNull
    @Unique
    private String characteristic;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 658445912)
    private transient TitlesDao myDao;

}
