package testStructure

data class TestsStorage (
        var precondition: String,
        var preconditionGeneral: String,
        val testsE2E:ArrayList<StructureE2E> = ArrayList(),
        val testsComponent:ArrayList<StructureComponent> = ArrayList(),
        val steps: ArrayList<String> = ArrayList(),
        val assert: ArrayList<String> = ArrayList()

)