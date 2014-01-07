


import akka.actor._
import scala.BigInt
import scala.math.BigInt.int2bigInt
object worker{
  
  case class calculate(start:BigInt,nrOfElements:BigInt,count:Int)
  case class report(result:BigInt)
  case object done
  
}
class worker extends Actor{
  
  def receive={
    
    case worker.calculate(start,nrOfElements,count)=>{
      
      var end= start + nrOfElements-1
      for (i <-start to end ){
        
    	var subend=i+count-1
        var sum = (i to subend).map(x => x*x).sum
        
        val root=math.sqrt(sum.toLong).toLong
        if(sum==root*root){
        
        	sender ! worker.report(i)
        }
        
      }
      
      sender ! worker.done
      
    }
       
  }
}