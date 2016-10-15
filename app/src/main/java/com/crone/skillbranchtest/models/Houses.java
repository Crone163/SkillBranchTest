package com.crone.skillbranchtest.models;


import com.crone.skillbranchtest.network.models.HousesModelRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "HOUSES")
public class Houses {

    public Houses(Long remoteId, HousesModelRes model) {
        this.remoteId = remoteId;
        this.name = model.getHouseName();
        this.words = model.getWords();
    }
    @Generated(hash = 527873135)
    public Houses(Long id, @NotNull Long remoteId, @NotNull String name,
            String words) {
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.words = words;
    }
    @Generated(hash = 885499324)
    public Houses() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRemoteId() {
        return this.remoteId;
    }
    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWords() {
        return this.words;
    }
    public void setWords(String words) {
        this.words = words;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 565279404)
    public List<Persons> getPersons() {
        if (persons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PersonsDao targetDao = daoSession.getPersonsDao();
            List<Persons> personsNew = targetDao._queryHouses_Persons(remoteId);
            synchronized (this) {
                if (persons == null) {
                    persons = personsNew;
                }
            }
        }
        return persons;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 46980672)
    public synchronized void resetPersons() {
        persons = null;
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
    @Generated(hash = 1492314700)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHousesDao() : null;
    }

    @Id
    private Long id;

    @NotNull
    @Unique
    private Long remoteId;

    @NotNull
    @Unique
    private String name;

    private String words;
    @ToMany(joinProperties = {
            @JoinProperty(name = "remoteId", referencedName = "personHouseRemoteId")
    })
    private List<Persons> persons;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 807213361)
    private transient HousesDao myDao;

}
