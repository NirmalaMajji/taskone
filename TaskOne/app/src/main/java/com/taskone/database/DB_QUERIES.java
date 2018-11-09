package com.taskone.database;


public class DB_QUERIES {

    static String favListData() {
        return "create table " + DB_PARAMS.TABLES.favListTable +
                " (_id INTEGER PRIMARY KEY ," +
                DB_PARAMS.COLUMNS.favListColumn.name + " TEXT," +
                DB_PARAMS.COLUMNS.favListColumn.id + " TEXT)";
    }

}
