package com.opticdev.core.sourcegear.containers

import com.opticdev.common.graph.path.FlatWalkablePath
import com.opticdev.sdk.rules.Rule
import com.opticdev.sdk.descriptions.ChildrenRule
import com.opticdev.sdk.skills_sdk.OMRange
import com.opticdev.sdk.skills_sdk.compilerInputs.subcontainers.OMSubContainer
import com.opticdev.sdk.skills_sdk.lens.OMLensNodeFinder

class SubContainerManager(subcontainers: Vector[OMSubContainer], containerMapping: ContainerMapping) {


  def rules : Vector[Rule] = {

    subcontainers.flatMap(i=> {
      val hookMappingOption = containerMapping.find(_._1.name == i.name)

      if (hookMappingOption.isEmpty) throw new Exception("No Container Hook defined for "+ i.name)

      val hook = hookMappingOption.get

      Vector(ChildrenRule(OMLensNodeFinder(hook._2.node.nodeType.name, OMRange(hook._2.node.range)), i.childrenRule))
    })

  }

  def containerPaths: Map[FlatWalkablePath, OMSubContainer] = {
    subcontainers.map(i=> {
      val hookMappingOption = containerMapping.find(_._1.name == i.name)

      if (hookMappingOption.isEmpty) throw new Exception("No Container Hook defined for " + i.name)

      val hook = hookMappingOption.get

      (hook._2.path.toFlatPath, i)
    }).toMap
  }

}

object SubContainerManager {
  def empty : SubContainerManager = new SubContainerManager(Vector(), Map())
}