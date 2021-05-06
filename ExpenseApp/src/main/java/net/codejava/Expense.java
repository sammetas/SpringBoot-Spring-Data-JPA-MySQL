package net.codejava;

import javax.persistence.*;

@Entity
@Table(name = "expense")
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String item;
	private float amount;
	
	public Expense() {
	}

	protected Expense(String item, float amount) {
		this.item = item;
		this.amount = amount;
	}
	public Expense(Long id,String item, float amount) {
		this.id=id;
		this.item = item;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return id + ". " + item + " - " + amount + " USD";		
	}	
}
