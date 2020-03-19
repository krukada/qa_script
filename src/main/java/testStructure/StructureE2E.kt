package testStructure

data class StructureE2E (
        var  precondition: String = "",
        var  description: String = "",
        val  steps: ArrayList<String> =  ArrayList(),
        val  assert: ArrayList<String> =  ArrayList()
)