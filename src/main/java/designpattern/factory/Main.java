package designpattern.factory;

import designpattern.factory.shapes.Shape;

/**
 * main method to test Factory design Pattern
 * @author hsahu
 *
 */
public class Main {

	public static void main(String[] args) {

		Shape shapeA = ShapeFactory.getInstance().getShape("circle");
		Shape shapeB = ShapeFactory.getInstance().getShape("parabola");

		System.out.println(shapeA.getShapeName());
		System.out.println(shapeB.getShapeName());
	}
}
