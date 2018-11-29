package model;

public abstract class ProductFactory {
	protected abstract Product createProduct(String productName, Object... parameters);
}
