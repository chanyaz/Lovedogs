package br.com.tairoroberto.lovedogs.base

import br.com.tairoroberto.lovedogs.login.model.LoginResponse
import br.com.tairoroberto.lovedogs.petshop.model.PetshopsResponse
import br.com.tairoroberto.lovedogs.register.model.UserRegisterRequest
import br.com.tairoroberto.lovedogs.register.model.UserRegisterResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Created by tairo on 8/24/17.
 */
interface Api {

    @GET("/login/{email}/{password}")
    fun login(@Path("email") email: String, @Path("password") password: String): Observable<LoginResponse>

    @POST("/users")
    fun registerUser(@Body userRegisterRequest: UserRegisterRequest) : Observable<UserRegisterResponse>

    @GET("/petshops")
    fun getPetshops(): Observable<PetshopsResponse>
}

