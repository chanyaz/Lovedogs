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

    init {
        val dbCore = DBCore.getInstance(context.applicationContext)
        db = dbCore.writableDatabase
    }

    fun insert(configuracoes: Configuracoes) {
        val values = ContentValues()
        db.insert("config", null, values)
    }

    fun update(configuracoes: Configuracoes?) {
        val values = ContentValues()

        db.update("config", values, "_id = ?", arrayOf("" + configuracoes?.id))
    }

    fun getById(id: Long): Configuracoes? {
        val configuracoes = Configuracoes()

        val columns = arrayOf("_id", "usuario", "senha", "latitude", "longitude", "distancia", "notificacao_entrada", "notificacao_almoco", "notificacao_saida", "chave_sessao", "matricula_usuario", "cookie_sessao", "cookie_usuario_sessao", "vibrar_habilitado", "ringtone_habilitado", "ringtone")
        val where = "_id = ?"

        try {
            db.query("config", columns, where, arrayOf("" + id), null, null, null).use { cursor ->
                if (cursor.count > 0) {
                    cursor.moveToFirst()

                    configuracoes.id = cursor.getLong(0)

                    return configuracoes
                } else {

                    return configuracoes
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