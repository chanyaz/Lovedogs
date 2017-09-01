package br.com.tairoroberto.mypet.base

import br.com.tairoroberto.mypet.login.model.LoginResponse
import br.com.tairoroberto.mypet.petshop.model.PetShop
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by tairo on 8/24/17.
 */
interface Api {

    @GET("/login/{email}/{password}")
    fun login(@Path("email") email: String, @Path("password") password: String): Observable<LoginResponse>

    @GET("/petshops")
    fun getPetshops(): Observable<List<PetShop>>
}

