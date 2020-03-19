package api

import java.util.*

data class SourceInJson(
         val description: String,
         val objects: ArrayList<Vertex>,
         val edges: ArrayList<Edges>
 )