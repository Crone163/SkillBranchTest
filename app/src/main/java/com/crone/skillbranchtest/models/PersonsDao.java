package com.crone.skillbranchtest.models;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PERSONS".
*/
public class PersonsDao extends AbstractDao<Persons, Long> {

    public static final String TABLENAME = "PERSONS";

    /**
     * Properties of entity Persons.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PersonRemoteId = new Property(1, Long.class, "personRemoteId", false, "PERSON_REMOTE_ID");
        public final static Property PersonHouseRemoteId = new Property(2, Long.class, "personHouseRemoteId", false, "PERSON_HOUSE_REMOTE_ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Sex = new Property(4, Boolean.class, "sex", false, "SEX");
        public final static Property Born = new Property(5, String.class, "born", false, "BORN");
        public final static Property Died = new Property(6, String.class, "died", false, "DIED");
        public final static Property Father = new Property(7, Long.class, "father", false, "FATHER");
        public final static Property Mother = new Property(8, Long.class, "mother", false, "MOTHER");
    }

    private DaoSession daoSession;

    private Query<Persons> houses_PersonsQuery;

    public PersonsDao(DaoConfig config) {
        super(config);
    }
    
    public PersonsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PERSONS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PERSON_REMOTE_ID\" INTEGER NOT NULL UNIQUE ," + // 1: personRemoteId
                "\"PERSON_HOUSE_REMOTE_ID\" INTEGER NOT NULL ," + // 2: personHouseRemoteId
                "\"NAME\" TEXT NOT NULL ," + // 3: name
                "\"SEX\" INTEGER," + // 4: sex
                "\"BORN\" TEXT," + // 5: born
                "\"DIED\" TEXT," + // 6: died
                "\"FATHER\" INTEGER," + // 7: father
                "\"MOTHER\" INTEGER);"); // 8: mother
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PERSONS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Persons entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getPersonRemoteId());
        stmt.bindLong(3, entity.getPersonHouseRemoteId());
        stmt.bindString(4, entity.getName());
 
        Boolean sex = entity.getSex();
        if (sex != null) {
            stmt.bindLong(5, sex ? 1L: 0L);
        }
 
        String born = entity.getBorn();
        if (born != null) {
            stmt.bindString(6, born);
        }
 
        String died = entity.getDied();
        if (died != null) {
            stmt.bindString(7, died);
        }
 
        Long father = entity.getFather();
        if (father != null) {
            stmt.bindLong(8, father);
        }
 
        Long mother = entity.getMother();
        if (mother != null) {
            stmt.bindLong(9, mother);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Persons entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getPersonRemoteId());
        stmt.bindLong(3, entity.getPersonHouseRemoteId());
        stmt.bindString(4, entity.getName());
 
        Boolean sex = entity.getSex();
        if (sex != null) {
            stmt.bindLong(5, sex ? 1L: 0L);
        }
 
        String born = entity.getBorn();
        if (born != null) {
            stmt.bindString(6, born);
        }
 
        String died = entity.getDied();
        if (died != null) {
            stmt.bindString(7, died);
        }
 
        Long father = entity.getFather();
        if (father != null) {
            stmt.bindLong(8, father);
        }
 
        Long mother = entity.getMother();
        if (mother != null) {
            stmt.bindLong(9, mother);
        }
    }

    @Override
    protected final void attachEntity(Persons entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Persons readEntity(Cursor cursor, int offset) {
        Persons entity = new Persons( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // personRemoteId
            cursor.getLong(offset + 2), // personHouseRemoteId
            cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0, // sex
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // born
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // died
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // father
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8) // mother
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Persons entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPersonRemoteId(cursor.getLong(offset + 1));
        entity.setPersonHouseRemoteId(cursor.getLong(offset + 2));
        entity.setName(cursor.getString(offset + 3));
        entity.setSex(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
        entity.setBorn(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDied(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFather(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setMother(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Persons entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Persons entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Persons entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "persons" to-many relationship of Houses. */
    public List<Persons> _queryHouses_Persons(Long personHouseRemoteId) {
        synchronized (this) {
            if (houses_PersonsQuery == null) {
                QueryBuilder<Persons> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.PersonHouseRemoteId.eq(null));
                houses_PersonsQuery = queryBuilder.build();
            }
        }
        Query<Persons> query = houses_PersonsQuery.forCurrentThread();
        query.setParameter(0, personHouseRemoteId);
        return query.list();
    }

}
