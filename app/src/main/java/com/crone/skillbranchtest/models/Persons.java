package com.crone.skillbranchtest.models;


import com.crone.skillbranchtest.network.models.PersonsModelRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.ArrayList;
import java.util.List;

@Entity(active = true, nameInDb = "PERSONS")
public class Persons {


    public Persons(Long houseRemoteId, Long personRemoteId, PersonsModelRes model) {
        this.personHouseRemoteId = houseRemoteId;
        this.personRemoteId = personRemoteId;
        this.name = model.getName();
        this.sex = null;
        if (model.getGender().equals("Female")) {
            sex = false;
        } else if (model.getGender().equals("Male")) {
            sex = true;
        }
        this.born = model.getBorn();
        this.died = model.getDied();
        this.father = model.getFather();
        this.mother = model.getMother();
    }

    @Generated(hash = 466816475)
    public Persons(Long id, @NotNull Long personRemoteId, @NotNull Long personHouseRemoteId,
            @NotNull String name, Boolean sex, String born, String died, Long father, Long mother) {
        this.id = id;
        this.personRemoteId = personRemoteId;
        this.personHouseRemoteId = personHouseRemoteId;
        this.name = name;
        this.sex = sex;
        this.born = born;
        this.died = died;
        this.father = father;
        this.mother = mother;
    }

    @Generated(hash = 1519000671)
    public Persons() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonRemoteId() {
        return this.personRemoteId;
    }

    public void setPersonRemoteId(Long personRemoteId) {
        this.personRemoteId = personRemoteId;
    }

    public Long getPersonHouseRemoteId() {
        return this.personHouseRemoteId;
    }

    public void setPersonHouseRemoteId(Long personHouseRemoteId) {
        this.personHouseRemoteId = personHouseRemoteId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return this.sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getBorn() {
        return this.born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public Long getFather() {
        return this.father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public Long getMother() {
        return this.mother;
    }

    public void setMother(Long mother) {
        this.mother = mother;
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
    @Generated(hash = 605428333)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPersonsDao() : null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1153208296)
    public List<Titles> getCharacteristics() {
        if (characteristics == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TitlesDao targetDao = daoSession.getTitlesDao();
            List<Titles> characteristicsNew = targetDao
                    ._queryPersons_Characteristics(personRemoteId);
            synchronized (this) {
                if (characteristics == null) {
                    characteristics = characteristicsNew;
                }
            }
        }
        return characteristics;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 146028633)
    public synchronized void resetCharacteristics() {
        characteristics = null;
    }

    public String getDied() {
        return this.died;
    }

    public void setDied(String died) {
        this.died = died;
    }

    @Id
    private Long id;

    @NotNull
    @Unique
    private Long personRemoteId;

    @NotNull
    private Long personHouseRemoteId;

    @NotNull
    private String name;

    private Boolean sex;

    private String born;

    private String died;

    private Long father;

    private Long mother;

    @ToMany(joinProperties = {
            @JoinProperty(name = "personRemoteId", referencedName = "titlePersonRemoteId")
    })
    private List<Titles> characteristics;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 244700660)
    private transient PersonsDao myDao;

}
