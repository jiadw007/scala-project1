
import akka.kernel.Bootable
import java.net.InetAddress
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props

class bossApplication extends Bootable{
  
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
    	  				port = 2553
    	  			}
    	  		}
    }""")
    val system=ActorSystem("bossApplication",ConfigFactory.load(config))
	val bossInstance=system.actorOf(Props(new boss()),"bossInstance")
	
	def startup(){
    
	 bossInstance ! boss.start
    
  	}
  	def shutdown(){
  	  
  	  system.shutdown()
  	  
  	}
}

object project1 {

  def main(args:Array[String]){
    
    
    new bossApplication()
    println("boss starts")
    
    
  }
  
}