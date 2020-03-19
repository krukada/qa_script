import api.ApiService
import api.SourceInJson
import api.Vertex
import com.google.gson.GsonBuilder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import testStructure.ComponentTest
import testStructure.StructureComponent
import testStructure.StructureE2E
import testStructure.TestsStorage

var source:SourceInJson? = null

fun createApiService(): ApiService {
    val gson = GsonBuilder().create()
    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    return retrofit.create(ApiService::class.java)
}

fun getPinsInMap(){
    val apiService: ApiService = createApiService()

    apiService.getPinsInMap().enqueue(object : Callback<SourceInJson> {
        override fun onResponse(call: Call<SourceInJson>, response: Response<SourceInJson>) {
            if (response.isSuccessful) {
                source = response.body()
                println(source!!.pDescription)
                createE2eAndComponentTests(source!!)

            } else {
                println("****")
            }
        }
        override fun onFailure(call: Call<SourceInJson>, t: Throwable) {
            t.printStackTrace()
        }

    })

}
val testsStorage = TestsStorage("", "")

fun createE2eAndComponentTests(source:SourceInJson){


    var i = 0
    while( i < source.objects.size){
        when(source.objects[i].color) {

            "blue" -> {
                if (source.objects[i].name.contains("preconditionGeneral")){
                    if  (testsStorage.preconditionGeneral == "") {
                        testsStorage.preconditionGeneral = source.objects[i].label

                    }
                    else {
                        testsStorage.preconditionGeneral = testsStorage.preconditionGeneral + "\n" + source.objects[i].label

                    }
                } else if (source.objects[i].name.contains("step")){
                    testsStorage.steps.add(source.objects[i].label)

                } else if (source.objects[i].name.contains("precondition")) {

                    if  (testsStorage.precondition == "") {
                        testsStorage.precondition = source.objects[i].label

                    }
                    else {
                        testsStorage.precondition = testsStorage.precondition + "\n" + source.objects[i].label

                    }
                }

            }
            "red" -> {
                if  (testsStorage.preconditionGeneral == ""){
                    println("У вас невалидная схема!! Введите precondition!")
                } else {
                    testsStorage.assert.add(source.objects[i].label)

                    val structureE2E: StructureE2E = StructureE2E(testsStorage.preconditionGeneral +  "\n" + testsStorage.precondition,source.pDescription,
                            testsStorage.steps,testsStorage.assert)
                    testsStorage.testsE2E.add(structureE2E)


                    testsStorage.precondition = ""
                    testsStorage.assert.clear()
                    testsStorage.steps.clear()

                }

            }

            "darkgreen" -> {
                testsStorage.assert.add(source.objects[i].label)

                val componentTest: ArrayList<ComponentTest> = ArrayList()
                source.edges.forEach {
                    edges ->
                    if (edges.tail== source.objects[i]._gvid){
                       source.objects.forEach {
                           if (it._gvid == edges.head && it.color == "orange"){
                               componentTest.add(ComponentTest(it.label))
                             }
                        }

                    }
                }
                testsStorage.testsComponent.add(StructureComponent(componentTest))
            }
        }
        i++
    }


}

fun main(args: Array<String>) {
    getPinsInMap()
}