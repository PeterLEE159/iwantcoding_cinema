package com.client.vo;

import java.util.Date;

import com.client.sql.BaseVO;
import com.client.sql.Column;
import com.client.sql.Table;
@Table("CUSTOMERS")
public class Customer extends BaseVO{
	@Column("ID")
	private int id;
	@Column("USERNAME")
	private String username;
	@Column("PASSWORD")
	private String password;
	@Column("NAME")
	private String name;
	@Column("PHONE")
	private String phone;
	@Column("EMAIL")
	private String email;
	@Column("GENDER")
	private int gender;
	@Column("MILIEGE")
	private int miliege;
	@Column("IMAGE_URI")
	private String imageURI;
	@Column("THIRD_PARTY")
	private String thirdParty;
	@Column("BIRTH")
	private Date birth;
	@Column("CREATED_AT")
	private Date createdAt;
	@Column("UPDATED_AT")
	private Date updatedAt;
	@Column("CUSTOMER_TYPE_ID")
	private int customerTypeId;
	@Column("CUSTOMER_RANK_ID")
	private int customerRankId;
	
	private String thirdPartyValidationId;
	
	private CustomerRank rank;
	private CustomerType type;
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", phone=" + phone + ", email=" + email + ", gender=" + gender + ", miliege=" + miliege
				+ ", imageURI=" + imageURI + ", thirdParty=" + thirdParty + ", birth=" + birth + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", customerTypeId=" + customerTypeId + ", customerRankId="
				+ customerRankId + ", thirdPartyValidationId=" + thirdPartyValidationId + ", rank=" + rank + ", type="
				+ type + "]";
	}

	public CustomerRank getRank() {
		return rank;
	}

	public void setRank(CustomerRank rank) {
		this.rank = rank;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getThirdPartyValidationId() {
		return thirdPartyValidationId;
	}

	public void setThirdPartyValidationId(String thirdPartyValidationId) {
		this.thirdPartyValidationId = thirdPartyValidationId;
	}

	public String getThirdParty() {
		return thirdParty;
	}

	public int getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public int getCustomerRankId() {
		return customerRankId;
	}

	public void setCustomerRankId(int customerRankId) {
		this.customerRankId = customerRankId;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getMiliege() {
		return miliege;
	}
	public void setMiliege(int miliege) {
		this.miliege = miliege;
	}
	public String getImageURI() {
		return imageURI;
	}
	public void setImageURI(String imageURI) {
		this.imageURI = imageURI;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public BaseVO newInstance() {
		return new Customer();
	}
}
