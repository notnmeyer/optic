package com.opticdev.core.sourcegear.graph
import com.opticdev.core.actorSystem
import better.files.File
import com.opticdev.core.Fixture.AkkaTestFixture
import com.opticdev.core.Fixture.compilerUtils.GearUtils
import com.opticdev.core.sourcegear.SourceGear
import com.opticdev.core.sourcegear.graph.model.BaseModelNode
import com.opticdev.core.sourcegear.project.{Project, StaticSGProject}
import com.opticdev.common.SchemaRef

class ProjectGraphWrapperSpec extends AkkaTestFixture("ProjectGraphWrapperTest") with GearUtils {

  it("can be initialized empty") {
    assert(ProjectGraphWrapper.empty()(project = null).projectGraph.isEmpty)
  }

  implicit lazy val project = new StaticSGProject("test", File(getCurrentDirectory + "/test-examples/resources/tmp/test_project/"), sourceGear)

  val testFilePath = getCurrentDirectory + "/test-examples/resources/example_source/ImportSource.js"
  val file = File(testFilePath)
  lazy val importResults = {
    val importGear = compiledLensFromDescription("test-examples/resources/example_packages/optic:ImportExample@0.1.0.json")
    sourceGear.lensSet.addLens(importGear)
    sourceGear.parseFile(File(testFilePath))
  }

  it("can add models from AstGraph") {
    val projectGraphWrapper = ProjectGraphWrapper.empty

    projectGraphWrapper.addFile(importResults.get.astGraph, file, importResults.get.fileTokenRegistry.exports)

    assert(projectGraphWrapper.projectGraph.nodes.size == 3)
    assert(projectGraphWrapper.projectGraph.edges.size == 2)
  }

  it("can apply queries to the project graph") {
    val projectGraphWrapper = ProjectGraphWrapper.empty
    projectGraphWrapper.addFile(importResults.get.astGraph, file, importResults.get.fileTokenRegistry.exports)
    val results = projectGraphWrapper.query((node)=> {
      node.value match {
        case mn: BaseModelNode => mn.schemaId == SchemaRef.fromString("optic:importexample@0.1.0/js-import").get
        case _ => false
      }
    })

    assert(results.size == 2)
  }

  it("gets the subgraph for a file") {
    val projectGraphWrapper = ProjectGraphWrapper.empty
    projectGraphWrapper.addFile(importResults.get.astGraph, file, importResults.get.fileTokenRegistry.exports)

    assert( projectGraphWrapper.subgraphForFile(file).get == projectGraphWrapper.projectGraph)
  }

  it("can remove file from AstGraph ") {

    val projectGraphWrapper = ProjectGraphWrapper.empty
    projectGraphWrapper.addFile(importResults.get.astGraph, file, importResults.get.fileTokenRegistry.exports)

    projectGraphWrapper.removeFile(file)

    assert(projectGraphWrapper.projectGraph.isEmpty)

  }
}
