package designpattern.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import designpattern.factory.shapes.Circle;
import designpattern.factory.shapes.Parabola;
import designpattern.factory.shapes.Shape;

/**
 * This class implements Factory method design pattern using Java Reflection.
 * Factory is also a singleton class. This factory follows open/close principle.
 * 
 * @author hsahu
 *
 */
public class ShapeFactory {

	/**
	 * This way we can register a new Object without modifying Factory but there
	 * could be other way to do it efficiently.
	 */
	static {
		ShapeFactory.getInstance().registerShape("circle", Circle.class);
		ShapeFactory.getInstance().registerShape("parabola", Parabola.class);
	}

	private static ShapeFactory shapeFactory;

	private ShapeFactory() {
		// disable default constructor
	}

	public static ShapeFactory getInstance() {
		if (shapeFactory == null) {
			synchronized (ShapeFactory.class) {
				shapeFactory = new ShapeFactory();
			}
		}
		return shapeFactory;
	}

	Map<String, Class> instanceFactory = new HashMap<String, Class>();

	/**
	 * This method should be accessible to those who are registering product
	 * 
	 * @param shapeId
	 * @param shapeClass
	 */
	public void registerShape(String shapeId, Class shapeClass) {
		instanceFactory.put(shapeId, shapeClass);
	}

	public Shape getShape(String shapeId) {
		Shape shape = null;
		try {
			Class shapeClass = instanceFactory.get(shapeId);
			Constructor<Shape> shapeConstructor = shapeClass.getConstructor();
			shape = shapeConstructor.newInstance(new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shape;
	}
}