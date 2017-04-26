package io.cloudslang.content.gcloud.services.compute.networks

import com.google.api.services.compute.model.Network

/**
  * Created by victor on 26.04.2017.
  */
object NetworkController {
  def createNetwork(networkName: String, networkDescription: String, autoCreateSubnetworks: Boolean, ipV4Range: Option[String]): Network = {
    val network = new Network()
      .setName(networkName)
      .setDescription(networkDescription)
      .setAutoCreateSubnetworks(autoCreateSubnetworks)
      .setIPv4Range(ipV4Range.get)
    network
  }
}