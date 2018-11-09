package com.taskone.database;


public class DB_PARAMS {
    interface ids {
        int DB_VERSION = 1;
        String DB_NAME = "taskone_db";
    }

    interface TABLES {
        String favListTable = "favListTable";
    }

    public interface COLUMNS {
        interface favListColumn {
            String name = "name";
            String id = "id";
        }
    }
}

