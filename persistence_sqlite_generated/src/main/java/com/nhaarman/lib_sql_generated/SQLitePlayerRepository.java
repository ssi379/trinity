package com.nhaarman.lib_sql_generated;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhaarman.ellietest.core.players.Player;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.sql_lib.query.Select;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SQLitePlayerRepository implements PlayerRepository {

    private final SQLiteDatabase mDatabase;
    private final TeamRepository mTeamRepository;

    @Inject
    SQLitePlayerRepository(final SQLiteDatabase database, final TeamRepository teamRepository) {
        mDatabase = database;
        mTeamRepository = teamRepository;
    }

    @Override
    public Long create(final Player object) {
        if (object.getTeam() != null) {
            mTeamRepository.create(object.getTeam());
        }

        ContentValues contentValues = createContentValues(object);
        long id = mDatabase.insert("players", null, contentValues);
        if (id != -1) {
            object.setId(id);
        }
        return id;
    }

    @Override
    public boolean update(final Player object) {
        if (object.getTeam() != null) {
            mTeamRepository.create(object.getTeam());
        }

        ContentValues contentValues = createContentValues(object);
        return mDatabase.update("players", contentValues, "id", new String[]{String.valueOf(object.getId())}) == 1;
    }

    @Override
    public Player find(final Long id) {
        Player result = null;

        Cursor cursor = new Select().from("players").where("id=" + id).fetchFrom(mDatabase);
        if (cursor.moveToFirst()) {
            result = read(cursor);
        }

        return result;
    }

    public final ContentValues createContentValues(final Player entity) {
        ContentValues values = new ContentValues();
        values.put("id", entity.getId());
        values.put("name", entity.getName());
        values.put("team_id", entity.getTeam().getId());
        return values;
    }

    private List<Player> readPlayers(final Cursor cursor) {
        List<Player> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                result.add(read(cursor));
            }
            while (cursor.moveToNext());
        }

        return result;
    }

    private Player read(final Cursor cursor) {
        Player player = new Player();

        player.setId(cursor.getLong(cursor.getColumnIndex("id")));
        player.setName(cursor.getString(cursor.getColumnIndex("name")));
        player.setTeam(mTeamRepository.find(cursor.getLong(cursor.getColumnIndex("team_id"))));

        return player;
    }
}
