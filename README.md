scala-project1
==============

actor model and remote actor model
README
Dawei Jia UFID:91365931
1. The size of work unit is 1/5 of toal numbers. Because I have 5 workers, I assign 1/5part
work to each worker for one time to complete this work.
2. The result of running my program for scala project1.scala 1000000 4 is no result.
3. The running time is
the ratio is (sys+user)/real=2.77
4. The largest problem I manage to solve is scala project1.scala 1000000000 24
5. remote actors:
In this program, we have one super boss in this 10 machines. This super boss is responsible for
assigning the job to each machine. Each machine is a boss. When it gets a job, it will assign the job
to its workers. And workers will be responsible for the calculation part.
