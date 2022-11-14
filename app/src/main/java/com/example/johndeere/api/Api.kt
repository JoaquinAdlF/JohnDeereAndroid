package com.example.johndeere.api

import com.example.johndeere.models.employeeInfo
import com.example.johndeere.models.insertResponse
import com.example.johndeere.models.organization
import com.example.johndeere.models.progress
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/*
Definición de las rutas con las que la aplicación interactúa con
el API del proyecto
 */

interface Api {

    // Registro de un empleado
    @Headers("Content-Type: application/json")
    @POST("employees/add")
    fun addEmployee(
        @Body employeeData: employeeInfo
    ) : Call<insertResponse>

    // Verificación del registro de la organización solicitada
    @POST("organizations/verifyorg")
    fun verifyOrg(
        @Body orgData: organization
    ) : Call<List<organization>>

    // Autenticación para el inicio de sesión de usuario
    @POST("employees/auth")
    fun authEmployee(
        @Body employeeData: employeeInfo
    ) : Call<List<employeeInfo>>

    // Creación del registro de progreso para el usuario recién registrado
    @POST("progress/create")
    fun createProgress(
        @Body progressData: progress
    ) : Call<insertResponse>
}