package poc.demo.product.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import poc.demo.product.query.Product;

public class ProductCreatedEvent {

	private Product product;
	
	@JsonCreator
	public ProductCreatedEvent(@JsonProperty("product") Product product) {
		this.product = product;
	}
	
	public Product getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return String.format("ProductCreatedEvent [product=%s]", product);
	}
	
}
