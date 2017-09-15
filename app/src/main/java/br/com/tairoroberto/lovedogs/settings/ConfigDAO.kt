package br.com.tairoroberto.lovedogs.settings

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.tairoroberto.lovedogs.base.database.DBCore

/**
 * Created by tairo on 9/10/17.
 */
class ConfigDAO(context: Context) {

    private val db: SQLiteDatabase
    private val columns = arrayOf("_id", "user", "password", "notification", "sound_notification", "vibrate", "share")
    private val table = "config"

    init {
        val dbCore = DBCore.getInstance(context.applicationContext)
        db = dbCore.writableDatabase
    }

    fun insert(configuracoes: Configuracoes?) {
        val values = ContentValues()
        values.put("user", configuracoes?.user)
        values.put("password", configuracoes?.password)
        values.put("notification", if(configuracoes?.notification == true) 1 else 0)
        values.put("sound_notification", configuracoes?.sound_notification)
        values.put("vibrate", if(configuracoes?.vibrate == true) 1 else 0)
        values.put("share", if(configuracoes?.share == true) 1 else 0)
        db.insert("config", null, values)
    }

    fun update(configuracoes: Configuracoes?) {
        val values = ContentValues()
        values.put("user", configuracoes?.user)
        values.put("password", configuracoes?.password)
        values.put("notification", if(configuracoes?.notification == true) 1 else 0)
        values.put("sound_notification", configuracoes?.sound_notification)
        values.put("vibrate", if(configuracoes?.vibrate == true) 1 else 0)
        values.put("share", if(configuracoes?.share == true) 1 else 0)
        db.update("config", values, "_id = ?", arrayOf("" + configuracoes?.id))
    }

    fun getById(id: Long): Configuracoes? {
        val configuracoes = Configuracoes()
        val where = "_id = ?"

        try {
            db.query(table, columns, where, arrayOf("" + id), null, null, null).use { cursor ->
                return if (cursor.count > 0) {
                    cursor.moveToFirst()

                    configuracoes.id = cursor.getLong(0)
                    configuracoes.user = cursor.getString(1)
                    configuracoes.password = cursor.getString(2)
                    configuracoes.notification = cursor.getInt(3) == 1
                    configuracoes.sound_notification = cursor.getString(4)
                    configuracoes.vibrate = cursor.getInt(5) == 1
                    configuracoes.share = cursor.getInt(6) == 1

                    configuracoes
                } else {

                    configuracoes
                }
            }
        } catch (e: Exception) {
            Log.i(TAG, "getById: " + e.message)
            return configuracoes
        }
    }

    companion object {
        private val TAG = "ConfigDAO"
    }
}