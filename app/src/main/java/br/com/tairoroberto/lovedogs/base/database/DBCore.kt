package br.com.tairoroberto.lovedogs.base.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



/**
 * Created by tairo on 9/10/17.
 */

class DBCore(context: Context): SQLiteOpenHelper(context, NOME_DB, null, VERSAO_DB) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("create table config("
                + "_id integer primary key autoincrement,"
                + "user text,"
                + "password text,"
                + "notification integer default 0,"
                + "sound_notification text,"
                + "vibrate integer default 0,"
                + "share integer default 0);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table config")
        onCreate(db)
    }

    companion object {
        private val NOME_DB = "lovepets_db"
        private val VERSAO_DB = 1

        private var mInstance: DBCore? = null

        fun getInstance(ctx: Context): DBCore {

            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            // See this article for more information: http://bit.ly/6LRzfx
            if (mInstance == null) {
                mInstance = DBCore(ctx.applicationContext)
            }
            return mInstance as DBCore
        }
    }
}