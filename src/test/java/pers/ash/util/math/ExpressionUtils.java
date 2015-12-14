package pers.ash.util.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import pers.ash.util.exception.InvalidOperatorException;

public class ExpressionUtils {
	
	private static Map<String, Integer> operatorMap = new HashMap<String, Integer>();
	
	static{
		operatorMap.put("+", 0);
		operatorMap.put("-", 0);
		operatorMap.put("*", 1);
		operatorMap.put("/", 1);
		operatorMap.put("%", 1);
		operatorMap.put("(", -1);
		operatorMap.put(")", -1);
	}

	public static Stack<String> toNifixExpression(String[] expression) throws InvalidOperatorException {
		validateExpression(expression);
		Stack<String> nifixExpr = new Stack<String>();
		Stack<String> operatorStack = new Stack<String>();
		for (int i = 0; i < expression.length; i++) {
			String str = expression[i];
			switch (str) {
			case "+":
			case "-":
			case "*":
			case "/":
			case "%":
			case ")":
				pushOperatorStack(str, operatorStack, nifixExpr);
				break;
			case "(":
				operatorStack.push(str);
				break;
			default:
				nifixExpr.push(str);
				break;
			}
		}
		//将operatorStack剩余中的元素全部push到nifixExpr
		while(!operatorStack.isEmpty()){
			nifixExpr.push(operatorStack.pop());
		}
		return nifixExpr;
	}
	
	public static Stack<String> toNifixExpression(String expression) throws InvalidOperatorException{
		return toNifixExpression(expression.split(" "));
	}
	
	public static String toNifixExpressionString(String[] expression) throws InvalidOperatorException{
		Stack<String> expr = toNifixExpression(expression);
		String result = "";
		for(int i = 0; i < expr.size(); i++){
			result += expr.get(i) + " ";
		}
		return result;
	}
	
	public static String toNifixExpressionString(String expression) throws InvalidOperatorException{
		return toNifixExpressionString(expression.split(" "));
	}
	
	private static void pushOperatorStack(String operator, Stack<String> operatorStack, Stack<String> nifixExpr){
		// 1.情况一：操作符是右括号，则符号栈一直出栈直到寻找到与之匹配的左括号
		if (operator.equals(")")) {
			while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")){
				nifixExpr.push(operatorStack.pop());
			}
			if (operatorStack.peek().equals("(")) {
				operatorStack.pop();
			}
		}
		// 2.情况二：操作符是普通运算符，比较运算符与栈顶运算符的优先级
		else {
			//运算符栈为空的话就没有栈顶元素，不再比较优先级，直接push运算符
			if (operatorStack.isEmpty()) {
				operatorStack.push(operator);
			} else {
				while (!operatorStack.isEmpty()) {
					Integer priority = operatorMap.get(operator);
					Integer peekPriority = operatorMap
							.get(operatorStack.peek());
					if (peekPriority >= priority) {
						nifixExpr.push(operatorStack.pop());
					}else{
						break;
					}
				}
				//所有低于新添加元素优先级的元素出栈，出栈完后，新元素入栈
				operatorStack.push(operator);
			}
		}
	}
	
	private static String validateExpression(String[] expression) throws InvalidOperatorException{
		String expr = "";
		for(int i = 0; i < expression.length; i++){
			validateOperator(expression[i]);
			expr += expression[i];
		}
		return expr;
	}
	
	private static void validateOperator(String operator) throws InvalidOperatorException{
		if(!StringUtils.isAlpha(operator) && !StringUtils.isNumeric(operator) && !operatorMap.containsKey(operator)){
			throw new InvalidOperatorException("输入的表达式不合法");
		}
	}
	
	@Test
	public void testToNifixPression() throws InvalidOperatorException{
		String expression = "9 + ( 3 - 1 ) * 3 + 10 / 2";
		String expression2 = "( 3 + 4 ) * 6 + 4 / ( 5 * ( 1 + 3 ) + 4 )";
		String expr = toNifixExpression(expression.split(" ")).toString();
		String expr2 = toNifixExpression(expression2.split(" ")).toString();
		System.out.println(expr);
		System.out.println(expr2);
	}
	
	@Test
	public void testToNifixPression2() throws InvalidOperatorException{
		String expression = "a + ( b - c ) * e + f / g";
		String expression2 = "( a + b ) * c + d / ( e * ( f + g ) + h )";
		String expr = toNifixExpression(expression).toString();
		String expr2 = toNifixExpression(expression2).toString();
		System.out.println(expr);
		System.out.println(expr2);
	}
	
	@Test
	public void testToNifixPressionString() throws InvalidOperatorException{
		String expression = "9 + ( 3 - 1 ) * 3 + 10 / 2";
		String expression2 = "( 3 + 4 ) * 6 + 4 / ( 5 * ( 1 + 3 ) + 4 )";
		String expr = toNifixExpressionString(expression.split(" "));
		String expr2 = toNifixExpressionString(expression2.split(" "));
		System.out.println(expr);
		System.out.println(expr2);
	}
	
	@Test
	public void testToNifixPressionString2() throws InvalidOperatorException{
		String expression = "a + ( b - c ) * e + f / g";
		String expression2 = "( a + b ) * c + d / ( e * ( f + g ) + h )";
		String expr = toNifixExpressionString(expression);
		String expr2 = toNifixExpressionString(expression2);
		System.out.println(expr);
		System.out.println(expr2);
	}
}
