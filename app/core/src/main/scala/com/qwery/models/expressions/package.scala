package com.qwery.models

/**
  * expressions package object
  * @author lawrence.daniels@gmail.com
  */
package object expressions {

  /**
    * importable implicits
    */
  object implicits {

    /**
      * Boolean to Expression conversion
      * @param value the given Boolean value
      * @return the equivalent [[Literal]]
      */
    final implicit def boolean2Expr(value: Boolean): Literal = Literal(value)

    /**
      * Byte to Expression conversion
      * @param value the given Byte value
      * @return the equivalent [[Literal]]
      */
    final implicit def byte2Expr(value: Byte): Literal = Literal(value)

    /**
      * Char to Expression conversion
      * @param value the given Char value
      * @return the equivalent [[Literal]]
      */
    final implicit def char2Expr(value: Char): Literal = Literal(value)

    /**
      * Double to Expression conversion
      * @param value the given Double value
      * @return the equivalent [[Literal]]
      */
    final implicit def double2Expr(value: Double): Literal = Literal(value)

    /**
      * Float to Expression conversion
      * @param value the given Float value
      * @return the equivalent [[Literal]]
      */
    final implicit def float2Expr(value: Float): Literal = Literal(value)

    /**
      * Integer to Expression conversion
      * @param value the given Integer value
      * @return the equivalent [[Literal]]
      */
    final implicit def int2Expr(value: Int): Literal = Literal(value)

    /**
      * Long to Expression conversion
      * @param value the given Long value
      * @return the equivalent [[Literal]]
      */
    final implicit def long2Expr(value: Long): Literal = Literal(value)

    /**
      * Short Integer to Expression conversion
      * @param value the given Short integer value
      * @return the equivalent [[Literal]]
      */
    final implicit def short2Expr(value: Short): Literal = Literal(value)

    /**
      * String to Expression conversion
      * @param value the given String value
      * @return the equivalent [[Literal]]
      */
    final implicit def string2Expr(value: String): Literal = Literal(value)

    /**
      * Symbol to Field conversion
      * @param symbol the given field name
      * @return the equivalent [[Field]]
      */
    final implicit def symbolToField(symbol: Symbol): Field = Field(symbol.name)

    /**
      * Symbol to Ordered Column conversion
      * @param symbol the given column name
      * @return the equivalent [[OrderColumn]]
      */
    final implicit def symbolToOrderColumn(symbol: Symbol): OrderColumn = OrderColumn(symbol.name, isAscending = true)

    /**
      * Condition Extensions
      * @param cond0 the given [[Condition condition]]
      */
    final implicit class ConditionExtensions(val cond0: Condition) extends AnyVal {

      @inline def ! = NOT(cond0)

      @inline def &&(cond1: Condition) = AND(cond0, cond1)

      @inline def ||(cond1: Condition) = OR(cond0, cond1)

    }

    /**
      * Expression Extensions
      * @param expr0 the given [[Expression value]]
      */
    final implicit class ExpressionExtensions(val expr0: Expression) extends AnyVal {

      import SQLFunction._

      @inline def ===(expr1: Expression) = EQ(expr0, expr1)

      @inline def !==(expr1: Expression) = NE(expr0, expr1)

      @inline def >(expr1: Expression) = GT(expr0, expr1)

      @inline def >=(expr1: Expression) = GE(expr0, expr1)

      @inline def <(expr1: Expression) = LT(expr0, expr1)

      @inline def <=(expr1: Expression) = LE(expr0, expr1)

      @inline def +(expr1: Expression) = Add(expr0, expr1)

      @inline def ||(expr1: Expression) = Concat(List(expr0, expr1))

      @inline def -(expr1: Expression) = Subtract(expr0, expr1)

      @inline def *(expr1: Expression) = Multiply(expr0, expr1)

      @inline def **(expr1: Expression) = Pow(expr0, expr1)

      @inline def /(expr1: Expression) = Divide(expr0, expr1)

      @inline def isNotNull: Condition = IsNotNull(expr0)

      @inline def isNull: Condition = IsNull(expr0)

    }

    /**
      * Typed Expression compiler
      * @param expression the given [[Expression]]
      */
    final implicit class TypedExpressionCompiler(val expression: Expression) extends AnyVal {

      @inline def asAny: Any = expression match {
        case Literal(value) => value
        case unknown =>
          throw new IllegalArgumentException(s"Unsupported expression '$unknown' primitive value expected.")
      }

      @inline def asInt: Int = expression.asNumber.toInt

      @inline def asNumber: Double = expression match {
        case Literal(value: Byte) => value
        case Literal(value: Char) => value
        case Literal(value: Double) => value
        case Literal(value: Float) => value
        case Literal(value: Int) => value
        case Literal(value: Long) => value
        case Literal(value: Short) => value
        case unknown =>
          throw new IllegalArgumentException(s"Unsupported expression '$unknown' numeric value expected.")
      }

      @inline def asString: String = expression match {
        case Literal(value: String) => value
        case unknown =>
          throw new IllegalArgumentException(s"Unsupported expression '$unknown' string expected.")
      }
    }

  }

}