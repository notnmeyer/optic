package com.opticdev
import akka.actor.ActorSystem
import com.opticdev.common.PackageRef
import com.opticdev.common.SchemaRef

package object core {
    implicit val actorSystem = ActorSystem("opticActors")

    def BlankSchema(id: String = "BLANK") = SchemaRef(Some(PackageRef("none:none", "0.1.0")), id)

    val namedObjectRegex = "[a-zA-Z][a-zA-Z0-9-_ ]*".r

}
