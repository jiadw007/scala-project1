

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.actorRef2Scala
import scala.BigInt
import scala.math.BigInt.int2bigInt
object boss{
  
  case object start
  var runningWorker:Int=0
  case object done
  case class assign(end:BigInt,count:Int,nrOfElements:BigInt,nrOfWorkers:Int)
}
class boss() extends Actor {
  
  val remotepath="akka.tcp://superbossApplication@localhost:2552/user/superBossInstance"
  val selection=context.actorSelection(remotepath);
  val startTime:Long=System.currentTimeMillis()
  var end:BigInt=0
  var count:Int=0
  var nrOfElements:BigInt=0
  var nrOfWorkers:Int=0
  var lastAssign:BigInt=0
  boss.runningWorker=0
  var workers = new Array[ActorRef](nrOfWorkers)
  
  def getNextWork():(BigInt,BigInt)={
    
    val subStart = lastAssign+1
    var subEnd = subStart + count-1
    if( subEnd > end){
      
      subEnd= end
      
    }
    lastAssign=subEnd
    if(subStart<=subEnd){
      
      (subStart,subEnd)
      
    }else{
      
      (BigInt.apply(-1),BigInt.apply(-1))
      
    }
      
  }
  
  def Start()={
    
    //assign work to each workers
    
    for(workerInstance<-workers){
      
      val range=this.getNextWork()
      if(range._1 != -1){
        
        boss.runningWorker+=1
        val nrOfElements = range._2-range._1+1
        workerInstance ! worker.calculate(range._1,nrOfElements,count)
      
      }
    }   
  }  
  
  def receive = {
    
    case boss.start=>{
      
      selection ! superboss.request
      
    }
    
    case boss.assign(end,count,nrOfElements,nrOfWorkers)=>{
      
      this.lastAssign=end
      this.count=count
      this.nrOfElements=nrOfElements
      this.nrOfWorkers=nrOfWorkers
      this.end=end
      for (i<- 0 to nrOfWorkers-1){
    
    	  workers(i)=context.actorOf(Props[worker], s"workerInstance$i")
    
      }
      this.Start()
      
    }
    case worker.report(result)=>{
      
      println (result)
    }
    case worker.done=>{
      
      boss.runningWorker-=1
      val range=this.getNextWork()
      if(range._1 != -1){
        val nrOfElements=range._2-range._1
        sender ! worker.calculate(range._1,nrOfElements,count)
        boss.runningWorker+=1
      }
      if(boss.runningWorker==0){
        context.system.shutdown();
        val endTime:Long=System.currentTimeMillis()
        println("real time:"+(endTime-startTime))
      }
 
      
    }
    
  }
 
}