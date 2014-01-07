

import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory
import java.net.InetAddress
import akka.actor.ActorSystem
import akka.actor.Props

class superbossApplication extends Bootable{
  
		//get the host name
	val hostname=InetAddress.getLocalHost().getHostName()
	val config =ConfigFactory.parseString(
		s"""akka {
    	  		actor {
    	  			provider = "akka.remote.RemoteActorRefProvider"
    	  		}
    	  		remote {
    	  			enabled-transports = ["akka.remote.netty.tcp"]
    	  			netty.tcp {
    	  				hostname = "$hostname"
    	  				port = 2552
    	  			}
    	  		}
    }""")
    val system=ActorSystem("superbossApplication",ConfigFactory.load(config))
	val superbossInstance=system.actorOf(Props(new superboss()),"superbossInstance")
	
	def startup(){
	  
	  
	}
	def shutdown(){
	  
	  system.shutdown()
	}
  
}





object project1superboss {
  
  def main(args:Array[String]){
    
    new superbossApplication()
    println("superboss starts")
  }
  

}