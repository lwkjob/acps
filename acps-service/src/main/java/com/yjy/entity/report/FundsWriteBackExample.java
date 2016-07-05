package com.yjy.entity.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FundsWriteBackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FundsWriteBackExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeIsNull() {
            addCriterion("InvoiceType is null");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeIsNotNull() {
            addCriterion("InvoiceType is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeEqualTo(Byte value) {
            addCriterion("InvoiceType =", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotEqualTo(Byte value) {
            addCriterion("InvoiceType <>", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeGreaterThan(Byte value) {
            addCriterion("InvoiceType >", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("InvoiceType >=", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeLessThan(Byte value) {
            addCriterion("InvoiceType <", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeLessThanOrEqualTo(Byte value) {
            addCriterion("InvoiceType <=", value, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeIn(List<Byte> values) {
            addCriterion("InvoiceType in", values, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotIn(List<Byte> values) {
            addCriterion("InvoiceType not in", values, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeBetween(Byte value1, Byte value2) {
            addCriterion("InvoiceType between", value1, value2, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andInvoicetypeNotBetween(Byte value1, Byte value2) {
            addCriterion("InvoiceType not between", value1, value2, "invoicetype");
            return (Criteria) this;
        }

        public Criteria andParentidIsNull() {
            addCriterion("ParentId is null");
            return (Criteria) this;
        }

        public Criteria andParentidIsNotNull() {
            addCriterion("ParentId is not null");
            return (Criteria) this;
        }

        public Criteria andParentidEqualTo(Integer value) {
            addCriterion("ParentId =", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotEqualTo(Integer value) {
            addCriterion("ParentId <>", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThan(Integer value) {
            addCriterion("ParentId >", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThanOrEqualTo(Integer value) {
            addCriterion("ParentId >=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThan(Integer value) {
            addCriterion("ParentId <", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThanOrEqualTo(Integer value) {
            addCriterion("ParentId <=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidIn(List<Integer> values) {
            addCriterion("ParentId in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotIn(List<Integer> values) {
            addCriterion("ParentId not in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidBetween(Integer value1, Integer value2) {
            addCriterion("ParentId between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotBetween(Integer value1, Integer value2) {
            addCriterion("ParentId not between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberIsNull() {
            addCriterion("InvoiceNumber is null");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberIsNotNull() {
            addCriterion("InvoiceNumber is not null");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberEqualTo(String value) {
            addCriterion("InvoiceNumber =", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotEqualTo(String value) {
            addCriterion("InvoiceNumber <>", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberGreaterThan(String value) {
            addCriterion("InvoiceNumber >", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberGreaterThanOrEqualTo(String value) {
            addCriterion("InvoiceNumber >=", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberLessThan(String value) {
            addCriterion("InvoiceNumber <", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberLessThanOrEqualTo(String value) {
            addCriterion("InvoiceNumber <=", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberLike(String value) {
            addCriterion("InvoiceNumber like", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotLike(String value) {
            addCriterion("InvoiceNumber not like", value, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberIn(List<String> values) {
            addCriterion("InvoiceNumber in", values, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotIn(List<String> values) {
            addCriterion("InvoiceNumber not in", values, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberBetween(String value1, String value2) {
            addCriterion("InvoiceNumber between", value1, value2, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andInvoicenumberNotBetween(String value1, String value2) {
            addCriterion("InvoiceNumber not between", value1, value2, "invoicenumber");
            return (Criteria) this;
        }

        public Criteria andBuyeridIsNull() {
            addCriterion("BuyerId is null");
            return (Criteria) this;
        }

        public Criteria andBuyeridIsNotNull() {
            addCriterion("BuyerId is not null");
            return (Criteria) this;
        }

        public Criteria andBuyeridEqualTo(Integer value) {
            addCriterion("BuyerId =", value, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridNotEqualTo(Integer value) {
            addCriterion("BuyerId <>", value, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridGreaterThan(Integer value) {
            addCriterion("BuyerId >", value, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridGreaterThanOrEqualTo(Integer value) {
            addCriterion("BuyerId >=", value, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridLessThan(Integer value) {
            addCriterion("BuyerId <", value, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridLessThanOrEqualTo(Integer value) {
            addCriterion("BuyerId <=", value, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridIn(List<Integer> values) {
            addCriterion("BuyerId in", values, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridNotIn(List<Integer> values) {
            addCriterion("BuyerId not in", values, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridBetween(Integer value1, Integer value2) {
            addCriterion("BuyerId between", value1, value2, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyeridNotBetween(Integer value1, Integer value2) {
            addCriterion("BuyerId not between", value1, value2, "buyerid");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameIsNull() {
            addCriterion("Buyer_CompanyName is null");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameIsNotNull() {
            addCriterion("Buyer_CompanyName is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameEqualTo(String value) {
            addCriterion("Buyer_CompanyName =", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameNotEqualTo(String value) {
            addCriterion("Buyer_CompanyName <>", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameGreaterThan(String value) {
            addCriterion("Buyer_CompanyName >", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameGreaterThanOrEqualTo(String value) {
            addCriterion("Buyer_CompanyName >=", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameLessThan(String value) {
            addCriterion("Buyer_CompanyName <", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameLessThanOrEqualTo(String value) {
            addCriterion("Buyer_CompanyName <=", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameLike(String value) {
            addCriterion("Buyer_CompanyName like", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameNotLike(String value) {
            addCriterion("Buyer_CompanyName not like", value, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameIn(List<String> values) {
            addCriterion("Buyer_CompanyName in", values, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameNotIn(List<String> values) {
            addCriterion("Buyer_CompanyName not in", values, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameBetween(String value1, String value2) {
            addCriterion("Buyer_CompanyName between", value1, value2, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerCompanynameNotBetween(String value1, String value2) {
            addCriterion("Buyer_CompanyName not between", value1, value2, "buyerCompanyname");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanIsNull() {
            addCriterion("Buyer_LinkMan is null");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanIsNotNull() {
            addCriterion("Buyer_LinkMan is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanEqualTo(String value) {
            addCriterion("Buyer_LinkMan =", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanNotEqualTo(String value) {
            addCriterion("Buyer_LinkMan <>", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanGreaterThan(String value) {
            addCriterion("Buyer_LinkMan >", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("Buyer_LinkMan >=", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanLessThan(String value) {
            addCriterion("Buyer_LinkMan <", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanLessThanOrEqualTo(String value) {
            addCriterion("Buyer_LinkMan <=", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanLike(String value) {
            addCriterion("Buyer_LinkMan like", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanNotLike(String value) {
            addCriterion("Buyer_LinkMan not like", value, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanIn(List<String> values) {
            addCriterion("Buyer_LinkMan in", values, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanNotIn(List<String> values) {
            addCriterion("Buyer_LinkMan not in", values, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanBetween(String value1, String value2) {
            addCriterion("Buyer_LinkMan between", value1, value2, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andBuyerLinkmanNotBetween(String value1, String value2) {
            addCriterion("Buyer_LinkMan not between", value1, value2, "buyerLinkman");
            return (Criteria) this;
        }

        public Criteria andSaleridIsNull() {
            addCriterion("SalerId is null");
            return (Criteria) this;
        }

        public Criteria andSaleridIsNotNull() {
            addCriterion("SalerId is not null");
            return (Criteria) this;
        }

        public Criteria andSaleridEqualTo(Integer value) {
            addCriterion("SalerId =", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridNotEqualTo(Integer value) {
            addCriterion("SalerId <>", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridGreaterThan(Integer value) {
            addCriterion("SalerId >", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridGreaterThanOrEqualTo(Integer value) {
            addCriterion("SalerId >=", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridLessThan(Integer value) {
            addCriterion("SalerId <", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridLessThanOrEqualTo(Integer value) {
            addCriterion("SalerId <=", value, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridIn(List<Integer> values) {
            addCriterion("SalerId in", values, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridNotIn(List<Integer> values) {
            addCriterion("SalerId not in", values, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridBetween(Integer value1, Integer value2) {
            addCriterion("SalerId between", value1, value2, "salerid");
            return (Criteria) this;
        }

        public Criteria andSaleridNotBetween(Integer value1, Integer value2) {
            addCriterion("SalerId not between", value1, value2, "salerid");
            return (Criteria) this;
        }

        public Criteria andHappentimeIsNull() {
            addCriterion("HappenTime is null");
            return (Criteria) this;
        }

        public Criteria andHappentimeIsNotNull() {
            addCriterion("HappenTime is not null");
            return (Criteria) this;
        }

        public Criteria andHappentimeEqualTo(Long value) {
            addCriterion("HappenTime =", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeNotEqualTo(Long value) {
            addCriterion("HappenTime <>", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeGreaterThan(Long value) {
            addCriterion("HappenTime >", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeGreaterThanOrEqualTo(Long value) {
            addCriterion("HappenTime >=", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeLessThan(Long value) {
            addCriterion("HappenTime <", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeLessThanOrEqualTo(Long value) {
            addCriterion("HappenTime <=", value, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeIn(List<Long> values) {
            addCriterion("HappenTime in", values, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeNotIn(List<Long> values) {
            addCriterion("HappenTime not in", values, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeBetween(Long value1, Long value2) {
            addCriterion("HappenTime between", value1, value2, "happentime");
            return (Criteria) this;
        }

        public Criteria andHappentimeNotBetween(Long value1, Long value2) {
            addCriterion("HappenTime not between", value1, value2, "happentime");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceIsNull() {
            addCriterion("TotalGoodsPrice is null");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceIsNotNull() {
            addCriterion("TotalGoodsPrice is not null");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceEqualTo(BigDecimal value) {
            addCriterion("TotalGoodsPrice =", value, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceNotEqualTo(BigDecimal value) {
            addCriterion("TotalGoodsPrice <>", value, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceGreaterThan(BigDecimal value) {
            addCriterion("TotalGoodsPrice >", value, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TotalGoodsPrice >=", value, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceLessThan(BigDecimal value) {
            addCriterion("TotalGoodsPrice <", value, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TotalGoodsPrice <=", value, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceIn(List<BigDecimal> values) {
            addCriterion("TotalGoodsPrice in", values, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceNotIn(List<BigDecimal> values) {
            addCriterion("TotalGoodsPrice not in", values, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TotalGoodsPrice between", value1, value2, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalgoodspriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TotalGoodsPrice not between", value1, value2, "totalgoodsprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceIsNull() {
            addCriterion("TotalPostPrice is null");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceIsNotNull() {
            addCriterion("TotalPostPrice is not null");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceEqualTo(BigDecimal value) {
            addCriterion("TotalPostPrice =", value, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceNotEqualTo(BigDecimal value) {
            addCriterion("TotalPostPrice <>", value, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceGreaterThan(BigDecimal value) {
            addCriterion("TotalPostPrice >", value, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TotalPostPrice >=", value, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceLessThan(BigDecimal value) {
            addCriterion("TotalPostPrice <", value, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TotalPostPrice <=", value, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceIn(List<BigDecimal> values) {
            addCriterion("TotalPostPrice in", values, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceNotIn(List<BigDecimal> values) {
            addCriterion("TotalPostPrice not in", values, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TotalPostPrice between", value1, value2, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andTotalpostpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TotalPostPrice not between", value1, value2, "totalpostprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceIsNull() {
            addCriterion("AccGoodsPrice is null");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceIsNotNull() {
            addCriterion("AccGoodsPrice is not null");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceEqualTo(BigDecimal value) {
            addCriterion("AccGoodsPrice =", value, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceNotEqualTo(BigDecimal value) {
            addCriterion("AccGoodsPrice <>", value, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceGreaterThan(BigDecimal value) {
            addCriterion("AccGoodsPrice >", value, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AccGoodsPrice >=", value, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceLessThan(BigDecimal value) {
            addCriterion("AccGoodsPrice <", value, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AccGoodsPrice <=", value, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceIn(List<BigDecimal> values) {
            addCriterion("AccGoodsPrice in", values, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceNotIn(List<BigDecimal> values) {
            addCriterion("AccGoodsPrice not in", values, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AccGoodsPrice between", value1, value2, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccgoodspriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AccGoodsPrice not between", value1, value2, "accgoodsprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceIsNull() {
            addCriterion("AccPostPrice is null");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceIsNotNull() {
            addCriterion("AccPostPrice is not null");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceEqualTo(BigDecimal value) {
            addCriterion("AccPostPrice =", value, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceNotEqualTo(BigDecimal value) {
            addCriterion("AccPostPrice <>", value, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceGreaterThan(BigDecimal value) {
            addCriterion("AccPostPrice >", value, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AccPostPrice >=", value, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceLessThan(BigDecimal value) {
            addCriterion("AccPostPrice <", value, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AccPostPrice <=", value, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceIn(List<BigDecimal> values) {
            addCriterion("AccPostPrice in", values, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceNotIn(List<BigDecimal> values) {
            addCriterion("AccPostPrice not in", values, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AccPostPrice between", value1, value2, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andAccpostpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AccPostPrice not between", value1, value2, "accpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceIsNull() {
            addCriterion("RedPacketGoodsPrice is null");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceIsNotNull() {
            addCriterion("RedPacketGoodsPrice is not null");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceEqualTo(BigDecimal value) {
            addCriterion("RedPacketGoodsPrice =", value, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceNotEqualTo(BigDecimal value) {
            addCriterion("RedPacketGoodsPrice <>", value, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceGreaterThan(BigDecimal value) {
            addCriterion("RedPacketGoodsPrice >", value, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RedPacketGoodsPrice >=", value, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceLessThan(BigDecimal value) {
            addCriterion("RedPacketGoodsPrice <", value, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RedPacketGoodsPrice <=", value, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceIn(List<BigDecimal> values) {
            addCriterion("RedPacketGoodsPrice in", values, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceNotIn(List<BigDecimal> values) {
            addCriterion("RedPacketGoodsPrice not in", values, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RedPacketGoodsPrice between", value1, value2, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketgoodspriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RedPacketGoodsPrice not between", value1, value2, "redpacketgoodsprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceIsNull() {
            addCriterion("RedPacketPostPrice is null");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceIsNotNull() {
            addCriterion("RedPacketPostPrice is not null");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceEqualTo(BigDecimal value) {
            addCriterion("RedPacketPostPrice =", value, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceNotEqualTo(BigDecimal value) {
            addCriterion("RedPacketPostPrice <>", value, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceGreaterThan(BigDecimal value) {
            addCriterion("RedPacketPostPrice >", value, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RedPacketPostPrice >=", value, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceLessThan(BigDecimal value) {
            addCriterion("RedPacketPostPrice <", value, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RedPacketPostPrice <=", value, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceIn(List<BigDecimal> values) {
            addCriterion("RedPacketPostPrice in", values, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceNotIn(List<BigDecimal> values) {
            addCriterion("RedPacketPostPrice not in", values, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RedPacketPostPrice between", value1, value2, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andRedpacketpostpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RedPacketPostPrice not between", value1, value2, "redpacketpostprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceIsNull() {
            addCriterion("PlatformCouponPrice is null");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceIsNotNull() {
            addCriterion("PlatformCouponPrice is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceEqualTo(BigDecimal value) {
            addCriterion("PlatformCouponPrice =", value, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceNotEqualTo(BigDecimal value) {
            addCriterion("PlatformCouponPrice <>", value, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceGreaterThan(BigDecimal value) {
            addCriterion("PlatformCouponPrice >", value, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PlatformCouponPrice >=", value, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceLessThan(BigDecimal value) {
            addCriterion("PlatformCouponPrice <", value, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PlatformCouponPrice <=", value, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceIn(List<BigDecimal> values) {
            addCriterion("PlatformCouponPrice in", values, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceNotIn(List<BigDecimal> values) {
            addCriterion("PlatformCouponPrice not in", values, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PlatformCouponPrice between", value1, value2, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andPlatformcouponpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PlatformCouponPrice not between", value1, value2, "platformcouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceIsNull() {
            addCriterion("SalerCouponPrice is null");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceIsNotNull() {
            addCriterion("SalerCouponPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceEqualTo(BigDecimal value) {
            addCriterion("SalerCouponPrice =", value, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceNotEqualTo(BigDecimal value) {
            addCriterion("SalerCouponPrice <>", value, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceGreaterThan(BigDecimal value) {
            addCriterion("SalerCouponPrice >", value, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerCouponPrice >=", value, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceLessThan(BigDecimal value) {
            addCriterion("SalerCouponPrice <", value, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerCouponPrice <=", value, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceIn(List<BigDecimal> values) {
            addCriterion("SalerCouponPrice in", values, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceNotIn(List<BigDecimal> values) {
            addCriterion("SalerCouponPrice not in", values, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerCouponPrice between", value1, value2, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalercouponpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerCouponPrice not between", value1, value2, "salercouponprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceIsNull() {
            addCriterion("SalerSpecialPrice is null");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceIsNotNull() {
            addCriterion("SalerSpecialPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceEqualTo(BigDecimal value) {
            addCriterion("SalerSpecialPrice =", value, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceNotEqualTo(BigDecimal value) {
            addCriterion("SalerSpecialPrice <>", value, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceGreaterThan(BigDecimal value) {
            addCriterion("SalerSpecialPrice >", value, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerSpecialPrice >=", value, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceLessThan(BigDecimal value) {
            addCriterion("SalerSpecialPrice <", value, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerSpecialPrice <=", value, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceIn(List<BigDecimal> values) {
            addCriterion("SalerSpecialPrice in", values, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceNotIn(List<BigDecimal> values) {
            addCriterion("SalerSpecialPrice not in", values, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerSpecialPrice between", value1, value2, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSalerspecialpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerSpecialPrice not between", value1, value2, "salerspecialprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceIsNull() {
            addCriterion("SalerAgioPrice is null");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceIsNotNull() {
            addCriterion("SalerAgioPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceEqualTo(BigDecimal value) {
            addCriterion("SalerAgioPrice =", value, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceNotEqualTo(BigDecimal value) {
            addCriterion("SalerAgioPrice <>", value, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceGreaterThan(BigDecimal value) {
            addCriterion("SalerAgioPrice >", value, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerAgioPrice >=", value, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceLessThan(BigDecimal value) {
            addCriterion("SalerAgioPrice <", value, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerAgioPrice <=", value, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceIn(List<BigDecimal> values) {
            addCriterion("SalerAgioPrice in", values, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceNotIn(List<BigDecimal> values) {
            addCriterion("SalerAgioPrice not in", values, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerAgioPrice between", value1, value2, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSaleragiopriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerAgioPrice not between", value1, value2, "saleragioprice");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountIsNull() {
            addCriterion("SalerFullAmount is null");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountIsNotNull() {
            addCriterion("SalerFullAmount is not null");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountEqualTo(BigDecimal value) {
            addCriterion("SalerFullAmount =", value, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountNotEqualTo(BigDecimal value) {
            addCriterion("SalerFullAmount <>", value, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountGreaterThan(BigDecimal value) {
            addCriterion("SalerFullAmount >", value, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerFullAmount >=", value, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountLessThan(BigDecimal value) {
            addCriterion("SalerFullAmount <", value, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerFullAmount <=", value, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountIn(List<BigDecimal> values) {
            addCriterion("SalerFullAmount in", values, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountNotIn(List<BigDecimal> values) {
            addCriterion("SalerFullAmount not in", values, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerFullAmount between", value1, value2, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerfullamountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerFullAmount not between", value1, value2, "salerfullamount");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceIsNull() {
            addCriterion("SalerChangeGoodsPrice is null");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceIsNotNull() {
            addCriterion("SalerChangeGoodsPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceEqualTo(BigDecimal value) {
            addCriterion("SalerChangeGoodsPrice =", value, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceNotEqualTo(BigDecimal value) {
            addCriterion("SalerChangeGoodsPrice <>", value, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceGreaterThan(BigDecimal value) {
            addCriterion("SalerChangeGoodsPrice >", value, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerChangeGoodsPrice >=", value, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceLessThan(BigDecimal value) {
            addCriterion("SalerChangeGoodsPrice <", value, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerChangeGoodsPrice <=", value, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceIn(List<BigDecimal> values) {
            addCriterion("SalerChangeGoodsPrice in", values, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceNotIn(List<BigDecimal> values) {
            addCriterion("SalerChangeGoodsPrice not in", values, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerChangeGoodsPrice between", value1, value2, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangegoodspriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerChangeGoodsPrice not between", value1, value2, "salerchangegoodsprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceIsNull() {
            addCriterion("SalerChangePostPrice is null");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceIsNotNull() {
            addCriterion("SalerChangePostPrice is not null");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceEqualTo(BigDecimal value) {
            addCriterion("SalerChangePostPrice =", value, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceNotEqualTo(BigDecimal value) {
            addCriterion("SalerChangePostPrice <>", value, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceGreaterThan(BigDecimal value) {
            addCriterion("SalerChangePostPrice >", value, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerChangePostPrice >=", value, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceLessThan(BigDecimal value) {
            addCriterion("SalerChangePostPrice <", value, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SalerChangePostPrice <=", value, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceIn(List<BigDecimal> values) {
            addCriterion("SalerChangePostPrice in", values, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceNotIn(List<BigDecimal> values) {
            addCriterion("SalerChangePostPrice not in", values, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerChangePostPrice between", value1, value2, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andSalerchangepostpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SalerChangePostPrice not between", value1, value2, "salerchangepostprice");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("Balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("Balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("Balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("Balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("Balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("Balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("Balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("Balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Balance not between", value1, value2, "balance");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}