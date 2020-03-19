package testStructure

data class TestsStorage (
        var precondition: String,
        var preconditionGeneral: String,
        var testsE2E:ArrayList<StructureE2E>,
        var testsComponent:ArrayList<StructureComponent>,
        var steps: ArrayList<String>

)