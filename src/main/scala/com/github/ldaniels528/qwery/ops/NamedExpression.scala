package com.github.ldaniels528.qwery.ops

/**
  * Represents a Named Expression
  * @author lawrence.daniels@gmail.com
  */
trait NamedExpression extends Expression {

  def name: String

  override def toSQL: String = if (name.contains(' ')) s"`$name`" else name

}

/**
  * Named Expression
  * @author lawrence.daniels@gmail.com
  */
object NamedExpression {

  /**
    * Returns a named alias for the given expression
    * @param name       the name of the alias
    * @param expression the expression
    * @return a named alias
    */
  def alias(name: String, expression: Expression): NamedExpression = expression match {
    case aggregate: Expression with Aggregation => AggregateAlias(name, aggregate)
    case vanilla => ExpressionAlias(name, vanilla)
  }

  /**
    * For pattern matching
    */
  def unapply(expression: NamedExpression): Option[String] = Some(expression.name)

  /**
    * Represents an alias for a field or expression
    * @author lawrence.daniels@gmail.com
    */
  case class AggregateAlias(name: String, aggregate: Expression with Aggregation) extends NamedExpression with Aggregation {

    override def evaluate(scope: Scope): Option[Any] = aggregate.evaluate(scope)

    override def update(scope: Scope): Unit = aggregate.update(scope)

    override def toSQL: String = s"$aggregate AS ${super.toSQL}"
  }

  /**
    * Represents an alias for a field or expression
    * @author lawrence.daniels@gmail.com
    */
  case class ExpressionAlias(name: String, expression: Expression) extends NamedExpression {

    override def evaluate(scope: Scope): Option[Any] = expression.evaluate(scope)

    override def toSQL: String = s"$expression AS ${super.toSQL}"
  }

}