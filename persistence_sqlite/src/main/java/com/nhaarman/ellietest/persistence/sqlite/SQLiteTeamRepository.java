package com.nhaarman.ellietest.persistence.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.sql_lib.query.Select;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SQLiteTeamRepository implements TeamRepository {

    private final SQLiteDatabase mDatabase;
    private final ClubRepository mClubRepository;

    @Inject
    SQLiteTeamRepository(final SQLiteDatabase database, final ClubRepository clubRepository) {
        mDatabase = database;
        mClubRepository = clubRepository;
    }

    @Override
    public Long create(final Team object) {
        if (object.getClub() != null) {
            mClubRepository.create(object.getClub());
        }

        ContentValues contentValues = createContentValues(object);
        long id = mDatabase.insert("teams", null, contentValues);
        if (id != -1) {
            object.setId(id);
        }
        return id;
    }

    @Override
    public boolean update(final Team object) {
        if (object.getClub() != null) {
            mClubRepository.create(object.getClub());
        }

        ContentValues contentValues = createContentValues(object);
        return mDatabase.update("teams", contentValues, "id", new String[]{String.valueOf(object.getId())}) == 1;
    }

    @Override
    public Team find(final Long id) {
        Team result = null;

        Cursor cursor = new Select().from("teams").where("id=" + id).fetchFrom(mDatabase);
        if (cursor.moveToFirst()) {
            result = read(cursor);
        }

        return result;
    }

    public final ContentValues createContentValues(final Team entity) {
        ContentValues values = new ContentValues();
        values.put("id", entity.getId());
        values.put("name", entity.getName());
        if (entity.getClub() != null) {
            values.put("club_id", entity.getClub().getId());
        }
        return values;
    }

    private List<Team> readTeams(final Cursor cursor) {
        List<Team> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                result.add(read(cursor));
            }
            while (cursor.moveToNext());
        }

        return result;
    }

    private Team read(final Cursor cursor) {
        Team team = new Team();

        team.setId(cursor.getLong(cursor.getColumnIndex("id")));
        team.setName(cursor.getString(cursor.getColumnIndex("name")));
        team.setClub(mClubRepository.find(cursor.getLong(cursor.getColumnIndex("club_id"))));

        return team;
    }
}
