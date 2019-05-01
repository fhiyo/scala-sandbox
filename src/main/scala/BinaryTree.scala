package binarytree


object BinaryTree {

  sealed abstract class Tree
  case class Branch(value: Int, left: Tree, right: Tree) extends Tree
  case object Empty extends Tree


  // XXX: 本当はRuntimeExceptionをthrowすることをscaladocとかに書いた方がよい？
  def max(t: Tree): Int = t match {
    case Empty => throw new RuntimeException
    case Branch(v, Empty, Empty) => v
    case Branch(v, Empty, r) => math.max(v, max(r))
    case Branch(v, l, Empty) => math.max(v, max(l))
    case Branch(v, l, r) => math.max(v, math.max(max(l), max(r)))
  }


  // XXX: 本当はRuntimeExceptionをthrowすることをscaladocとかに書いた方がよい？
  def min(t: Tree): Int = t match {
    case Branch(v, Empty, Empty) => v
    case Branch(v, Empty, r) => math.min(v, min(r))
    case Branch(v, l, Empty) => math.min(v, min(l))
    case Branch(v, l, r) => math.min(v, math.min(min(l), min(r)))
    case Empty => throw new RuntimeException
  }


  def depth(t: Tree): Int = {
    t match {
      case Empty => 0
      case Branch(_, l, r) => 1 + math.max(depth(l), depth(r))
    }
  }


  def toString(t: Tree): String = {
    t match {
      case Empty => "nil"
      case Branch(v, l, r) => "(" + v.toString + " " + toString(l) + " " + toString(r) + ")"
    }
  }


  def toList(tree: Tree): List[Int] = tree match {
    case Empty => Nil
    case Branch(v, l, r) => toList(l) ++ List(v) ++ toList(r)
  }


  def insert(value: Int, tree: Tree): Tree = tree match {
    case Empty => Branch(value, Empty, Empty)
    case Branch(v, l, r) =>
      if (value <= v) Branch(v, insert(value, l), r)
      else Branch(v, l, insert(value, r))
  }


  def fromList(list: List[Int]): Tree = list match {
    case Nil => Empty
    case l => l.foldLeft(Empty: Tree)((t, v) => insert(v, t))
  }


  def sort(t: Tree): Tree = fromList(toList(t))

}

