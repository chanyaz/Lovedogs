package br.com.tairoroberto.mypet.base

import br.com.tairoroberto.mypet.petshop.model.PetShop
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by tairo on 8/24/17.
 */
interface Api {

    @GET("/linhas")
    fun consultarLinhasMetro(): Call<List<PetShop>>

    @GET("/linhas/{linha}/estacoes")
    fun consultarEstacaoes(@Path("linha") linha: String): Observable<List<PetShop>>
}

