

import akka.actor._
import scala.BigInt
import scala.math.BigInt.int2bigInt

object project1 {
  
  def main(args:Array[String]){
    
    implicit val system=ActorSystem()
    var end:BigInt=BigInt.apply(args(0))
    var count:Int=args(1).toInt
    var nrOfElements:BigInt=1000000
    var nrOfWorkers:Int=6
    val bossInstance = system.actorOf(Props(new boss(end, count, nrOfElements, nrOfWorkers)), "bossInstance")
    bossInstance ! boss.start
    
    
    
   
    
  }
  
}
//project1.main(args)