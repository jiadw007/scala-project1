

import akka.actor.Actor

object superboss{
  
  case object request
  var runningboss=0
  var index=0
  val workUnit=10000000
  
}


class superboss extends Actor{
  
  
  def receive={
    
    case superboss.request=>{
      
      superboss.index+=1
      sender ! boss.assign(superboss.index*superboss.workUnit,24,superboss.workUnit/5,5)
      
      
      
    }
    
    case boss.done=>{
      
      superboss.runningboss-=1
      if(superboss.index>10||superboss.runningboss==0){
        
        context.system.shutdown()
        
      }
      
    }
    
    
    
  }
  
  
  
  
  

}