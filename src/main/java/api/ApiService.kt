package api

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("krukada/qa_script/master/src/main/resources/text.json")
    fun getPinsInMap(): Call<SourceInJson>
}
