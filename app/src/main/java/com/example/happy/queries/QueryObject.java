package com.example.happy.queries;

public class QueryObject {

    private String columns;
    private String table;
    private String whereStatement;
    private String condition;

    public QueryObject(String columns, String table, String whereStatement, String condition) {
        this.columns = columns;
        this.table = table;
        this.whereStatement = whereStatement;
        this.condition = condition;
    }
}
