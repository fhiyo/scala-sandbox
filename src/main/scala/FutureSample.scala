package futuresample

import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Random, Success, Try}

// the flollowing is equivalent to `implicit val ec = ExecutionContext.global`
import scala.concurrent.ExecutionContext.Implicits.global


object CountDownLatchSample extends App {

  // ↓いや、上界はやっぱり1000だろ。
  //val upperBoundMilliSec = 1001
  val maxWaitMilliSec = 1000

  val indexHolder = new AtomicInteger(0)
  val random = new Random
  val promises: Seq[Promise[Int]] = for {i <- 1 to 3} yield Promise[Int]
  val futures: Seq[Future[Int]] = for {i <- 1 to 8} yield Future[Int] {
    val waitMilliSec = random.nextInt(maxWaitMilliSec + 1)
    Thread.sleep(waitMilliSec)
    waitMilliSec
  }

  futures.foreach { f => f.foreach { case waitMilliSec =>
    val index = indexHolder.getAndIncrement
      if (index < promises.length) {
        // XXX: ここでpromiseとfutureの対応をさせているのか？？
        // 各々のfutureのforeach内の関数だからpromiseに対応させられる。。。。？？
        // わからん。
        // あー、いや、単純にfutureの結果、つまりIntをそのまま格納しているだけか。
        // 早い順にsuccessに入れている。successに値を入れる、という行為が
        // promiseのfutureが成功(?)したということと同義なんだ、多分
        promises(index).success(waitMilliSec)
      }
  }}
  promises.foreach { p => p.future.foreach { case waitMilliSec => println(waitMilliSec)}}
  Thread.sleep(5000)

}


class CallbackSomething {
  // doSomething()を行うcallback用のclassを定義
  // doSomething(onSuccess, onFailure)でUnitを返す感じ
  // いつsuccessでいつfailureなのかはここで決めちゃう

  val random = new Random

  def doSomething(onSuccess: Int => Unit, onFailure: Throwable => Unit): Unit = {
    val i = random.nextInt(10)
    if (i < 5) onSuccess(i) else onFailure(new RuntimeException(i.toString))
  }
}


class FutureSomething {
  // doSomethingはCallbackSomethingの同名の関数をラップする。
  // 戻り値はFuture[Int]

  val callbackSomething = new CallbackSomething

  def doSomething(): Future[Int] = {
    val promise = Promise[Int]
    callbackSomething.doSomething(i => promise.success(i), t => promise.failure(t))
    promise.future
  }
}


object CallbackFuture extends App {
  // iFuture, jFutureの中身を足し合わせて、resultという変数に束縛させて表示させる
  val futureSomething = new FutureSomething

  val iFuture: Future[Int] = futureSomething.doSomething
  val jFuture: Future[Int] = futureSomething.doSomething

  val iplusj: Future[Int] = for {
    i <- iFuture
    j <- jFuture
  } yield i + j

  val result: Int = Await.result(iplusj, Duration.Inf)

  println(result)

}


object PromiseSample extends App {

  // 初っ端からなぞ。newは？
  // Promiseの定義: trait Promise[T] extends AnyRef
  // traitなので少なくともnewは直接できない。 (new Promise[Int] {}みたいに継承した無名クラスを作らない限り)
  val promiseGetInt: Promise[Int] = Promise[Int]
  val futureByPromise: Future[Int] = promiseGetInt.future

  val mappedFuture = futureByPromise.map { i =>
    println(s"Success! i: ${i}")
  }

  // 別スレッドで何か重い処理をして、終わったらPromiseに値を渡す
  Future {
    Thread.sleep(300)
    promiseGetInt.success(1)
  }

  Await.ready(mappedFuture, 5000 millisecond)
  // ref: https://dwango.github.io/scala_text/future-and-promise.html
  // > この処理は必ずSuccess! i: 1という値を表示します。
  // > このようにPromiseに値を渡すことで（Promiseから生成した）
  // > Futureを完了させることができます。

}


object FutureSample3 extends App {
  val fs: Future[Int] = Future.successful(10)
  // fs: Future(Success(10))
  // fs.value: Some(Success(10))
  // fs.value.fold(throw new Exception)(x => x): Success(10)
  // fs.value.fold(throw new Exception)(x => x).get: 10
  println(fs.value.fold(throw new Exception)(x => x).get)

  val ff: Future[Int] = Future.failed{ new Exception("error in failure")}
  println(ff)
  val ft: Future[String] = Future.fromTry(Try{
    if (new Random() {}.nextInt(2) == 1) "hello" else throw new Exception("error in f")
  })
  println(ft)

  // Future[List[Future[Int]]]じゃないんだ。。。traverseが上手くやってくれてる？
  // awaitしなかったら、Future(<not completed>)という結果が出た
  // ↑あ、traverseじゃなくてsequenceの形にすればList[Future[Int]]になるのか！
  val f1: Future[List[Int]] = Future.traverse((1 to 10).toList) {i =>
    Future { Thread.sleep(i * 100); i }
  }

  val f2: Future[List[Int]] = Future.traverse((11 to 20).toList) {i =>
    Future { Thread.sleep(i * 100); i }
  }

  val f: Future[List[Int]] = Future.foldLeft(List(f1, f2))(List(10)) { (total, v) =>
    v ++: total
  }
  f.foreach { case result: List[Int] => println(result.sum)}
  Await.ready(f, Duration.Inf)

  // 一応、型は合ってた。でも全部completeするまでまつには？
  val fSeq: List[Future[Int]] = (1 to 10).toList.map { i =>
    Future {Thread.sleep(i * 100); i }
  }

  Await.ready(f1, Duration.Inf)
  println(f1)
  val f0: Future[List[Int]] = Future.sequence(fSeq)
  f0.foreach { case result => println(result.mkString) }
  Await.ready(f0, Duration.Inf)
  //println(fSeq)

}


object CompositeFutureSample extends App {
  // randomに待ち、その待った時間をmilli secondで返す関数を2つ用意する。
  // それらは共通の処理として、500 ms以下しか待たない場合は例外を投げる。
  // 両方が成功した場合はsuccessの文字列を返し、両方が返した数値を表示する。
  // 失敗した場合は例外の内容を投げる。

  val random = new Random()
  val maxMilliSec = 3000
  def waitRandom(futureName: String): Int = {
    val waitMilliSec = random.nextInt(maxMilliSec)
    if (waitMilliSec <= 500) throw new RuntimeException(s"${futureName} waitMilliSec is ${waitMilliSec}")
    Thread.sleep(waitMilliSec)
    waitMilliSec
  }

  val futureFirst: Future[Int] = Future {waitRandom("first")}
  val futureSecond: Future[Int] = Future {waitRandom("second")}

  // これ中々書けない。。
  val compositeFuture: Future[(Int, Int)] = for {
    first <- futureFirst
    second <- futureSecond
  } yield (first, second)

  compositeFuture onComplete {
    case Success((first, second)) => println(s"Success! first: ${first}, second: ${second}")
    case Failure(t) => println(s"Failure: ${t.getMessage}")
  }

  Thread.sleep(5000)

}

object FutureOptionUsageSample extends App {

  val random = new Random()
  val waitMaxMilliSec = 3000

  val futureMilliSec: Future[Int] = Future {
    val waitMilliSec = random.nextInt(waitMaxMilliSec)
    if (waitMilliSec < 1000) throw new RuntimeException(s"waitMilliSec is ${waitMilliSec}")
    Thread.sleep(waitMilliSec)
    waitMilliSec
  }

  //val futureSec: Future[Double] = futureMilliSec.map(i => i.toDouble / 1000)
  val futureSec: Future[Double] = futureMilliSec.flatMap(i => Future {
    Thread.sleep(100)
    i.toDouble / 1000
  })

  futureSec onComplete {
    case Success(waitSec) => println(s"Success! ${waitSec} sec")
    case Failure(t) => println(s"Failure: ${t.getMessage}")
  }

  // 待ってないと先に終了しちゃう？
  // Await.readyでfutureSecを指定するのはダメなのか？
  // まあこれは、onCompleteの処理を待つわけではなくfutureSecの終了を待つわけだから
  // onCompleteの動作は確認できないか。。
  Thread.sleep(waitMaxMilliSec)
  //Await.ready(futureSec, waitMaxMilliSec millisecond)
  //Await.ready(futureSec, 5000 millisecond)


}

object FutureSample2 extends App {

  val s = "Hello"
  val f: Future[String] = Future {
    Thread.sleep(1000)
    println(s"[ThreadName] In Future: ${Thread.currentThread.getName}")
    s + " future!"
  }

  f.foreach { case s: String =>
    println(s"[ThreadName] In Success: ${Thread.currentThread.getName}")
    println(s)
  }

  println(f.isCompleted)

  Await.ready(f, 5000 millisecond)

  println(s"[ThreadName] In App: ${Thread.currentThread.getName}")
  println(f.isCompleted)

}


// scala.App traitを継承することでmainがなくても実行できる
object FutureSample extends App {
  val s = "Hello"
  val f: Future[String] = Future {
    Thread.sleep(1000)
    s + " future!"
  }

  // import scala.concurrent.ExecutionContext.Implicits.global
  // がないとコンパイルエラーになる
  // カリー化された関数の引数が足りないから。globalはimplicitな変数なので
  // それをimportすればそれだけで暗黙的に渡ってくれる
  // ref: https://docs.scala-lang.org/overviews/core/futures.html
  f.foreach { case s: String =>
    println(s)
  }

  println(f.isCompleted)

  Thread.sleep(5000)
  println(f.isCompleted)
  val f2: Future[String] = Future {
    Thread.sleep(1000)
    throw new RuntimeException("わざと失敗")
  }

  f2.failed.foreach { case e: Throwable =>
    println(e.getMessage)
  }

  println(f2.isCompleted)

  Thread.sleep(5000)

  println(f2.isCompleted)

}
